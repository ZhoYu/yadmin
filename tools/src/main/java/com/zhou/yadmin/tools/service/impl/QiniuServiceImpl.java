/**
 * <p>
 * 文件名称:    QiniuServiceImpl
 * </p>
 */
package com.zhou.yadmin.tools.service.impl;

import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Optional;

import com.google.common.collect.Lists;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.storage.model.FileInfo;
import com.qiniu.util.Auth;
import com.zhou.yadmin.common.constants.CommonConstant;
import com.zhou.yadmin.common.constants.FrontConstant;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.common.utils.FileUtils;
import com.zhou.yadmin.common.utils.JSONUtils;
import com.zhou.yadmin.common.utils.PageUtils;
import com.zhou.yadmin.common.utils.ValidationUtils;
import com.zhou.yadmin.tools.domain.QiniuConfig;
import com.zhou.yadmin.tools.domain.QiniuContent;
import com.zhou.yadmin.tools.repository.QiniuConfigRepository;
import com.zhou.yadmin.tools.repository.QiniuContentRepository;
import com.zhou.yadmin.tools.service.QiniuService;
import com.zhou.yadmin.tools.utils.QiniuUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 22:03
 */
@Service
@CacheConfig(cacheNames = "qiniu")
public class QiniuServiceImpl extends AbstractBaseComponent implements QiniuService
{
    @Autowired
    private QiniuConfigRepository qiniuConfigRepository;

    @Autowired
    private QiniuContentRepository qiniuContentRepository;

    @Value("${qiniu.max-size}")
    private Long maxSize;

    private static final String TYPE = "公开";
    /**
     * 1小时，可以自定义链接过期时间
     */
    private final long expireInSeconds = 3600L;

    /**
     * 分页
     *
     * @param qiniuContent
     * @param pageable
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = "keyGenerator")
    public Object queryAll(QiniuContent qiniuContent, Pageable pageable)
    {
        return PageUtils.toPage(qiniuContentRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = Lists.newArrayList();
            if (!ObjectUtils.isEmpty(qiniuContent.getKey()))
            {
                predicateList.add(criteriaBuilder.like(root.get("key").as(String.class), "%" + qiniuContent.getKey() + "%"));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        }, pageable));
    }

    /**
     * 查配置
     *
     * @return
     */
    @Override
    @Cacheable(key = "'1'")
    @Transactional(readOnly = true)
    public QiniuConfig find()
    {
        Optional<QiniuConfig> qiniuConfig = qiniuConfigRepository.findById(1L);
        return qiniuConfig.orElseGet(QiniuConfig::new);
    }

    /**
     * 修改配置
     *
     * @param qiniuConfig
     *
     * @return
     */
    @Override
    @CachePut(key = "'1'")
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public QiniuConfig update(QiniuConfig qiniuConfig)
    {
        if (!qiniuConfig.getHost().toLowerCase().startsWith(FrontConstant.URL_PROTOCOL_HTTP) &&
            !qiniuConfig.getHost().toLowerCase().startsWith(FrontConstant.URL_PROTOCOL_HTTPS))
        {
            throw BadRequestException.newExceptionByTools("外链域名必须以http://或者https://开头");
        }
        qiniuConfig.setId(1L);
        return qiniuConfigRepository.save(qiniuConfig);
    }

    /**
     * 上传文件
     *
     * @param file
     * @param qiniuConfig
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public QiniuContent upload(MultipartFile file, QiniuConfig qiniuConfig)
    {
        long size = maxSize * 1024 * 1024;
        if (file.getSize() > size)
        {
            throw BadRequestException.newExceptionByTools("文件超出规定大小");
        }
        if (qiniuConfig.getId() == null)
        {
            throw BadRequestException.newExceptionByTools("请先添加相应配置，再操作");
        }
        /**
         * 构造一个带指定Zone对象的配置类
         */
        Configuration cfg = QiniuUtils.getConfiguration(qiniuConfig.getZone());
        UploadManager uploadManager = new UploadManager(cfg);
        Auth auth = Auth.create(qiniuConfig.getAccessKey(), qiniuConfig.getSecretKey());
        String upToken = auth.uploadToken(qiniuConfig.getBucket());
        try
        {
            Response response = uploadManager.put(file.getBytes(), QiniuUtils.getKey(file.getOriginalFilename()), upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSONUtils.json2Object(response.bodyString(), DefaultPutRet.class);
            //存入数据库
            QiniuContent qiniuContent = new QiniuContent();
            qiniuContent.setBucket(qiniuConfig.getBucket());
            qiniuContent.setType(qiniuConfig.getType());
            qiniuContent.setKey(putRet.key);
            qiniuContent.setUrl(qiniuConfig.getHost() + CommonConstant.SLASH_DELIMITER + putRet.key);
            qiniuContent.setSize(FileUtils.getSize(file.getSize()));
            return qiniuContentRepository.save(qiniuContent);
        }
        catch (Exception e)
        {
            logger.error("上传处理文件失败", e);
            throw BadRequestException.newExceptionByTools(e.getMessage());
        }
    }

    /**
     * 查询文件
     *
     * @param id
     *
     * @return
     */
    @Override
    @Cacheable(key = "'content:'+#p0")
    @Transactional(readOnly = true)
    public QiniuContent findByContentId(Long id)
    {
        Optional<QiniuContent> qiniuContent = qiniuContentRepository.findById(id);
        ValidationUtils.isNull(qiniuContent, "QiniuContent", "id", id);
        return qiniuContent.get();
    }

    /**
     * 下载文件
     *
     * @param content
     * @param config
     *
     * @return
     */
    @Override
    public String download(QiniuContent content, QiniuConfig config)
    {
        String finalUrl;
        if (TYPE.equals(content.getType()))
        {
            finalUrl = content.getUrl();
        }
        else
        {
            Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
            finalUrl = auth.privateDownloadUrl(content.getUrl(), expireInSeconds);
        }
        return finalUrl;
    }

    /**
     * 删除文件
     *
     * @param content
     * @param config
     *
     * @return
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void delete(QiniuContent content, QiniuConfig config)
    {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = QiniuUtils.getConfiguration(config.getZone());
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        try
        {
            bucketManager.delete(content.getBucket(), content.getKey());
            qiniuContentRepository.delete(content);
        }
        catch (QiniuException e)
        {
            logger.error("文件删除失败", e);
        }
    }

    /**
     * 同步数据
     *
     * @param config
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void synchronize(QiniuConfig config)
    {
        if (config.getId() == null)
        {
            throw BadRequestException.newExceptionByTools("请先添加相应配置，再操作");
        }
        //构造一个带指定Zone对象的配置类
        Configuration cfg = QiniuUtils.getConfiguration(config.getZone());
        Auth auth = Auth.create(config.getAccessKey(), config.getSecretKey());
        BucketManager bucketManager = new BucketManager(auth, cfg);
        //文件名前缀
        String prefix = "";
        //每次迭代的长度限制，最大1000，推荐值 1000
        int limit = 1000;
        //指定目录分隔符，列出所有公共前缀（模拟列出目录效果）。缺省值为空字符串
        String delimiter = "";
        //列举空间文件列表
        BucketManager.FileListIterator fileListIterator = bucketManager.createFileListIterator(config.getBucket(), prefix, limit, delimiter);
        while (fileListIterator.hasNext())
        {
            //处理获取的file list结果
            QiniuContent qiniuContent = null;
            FileInfo[] items = fileListIterator.next();
            for (FileInfo item : items)
            {
                if (qiniuContentRepository.findByKey(item.key) == null)
                {
                    qiniuContent = new QiniuContent();
                    qiniuContent.setSize(FileUtils.getSize((int) item.fsize));
                    qiniuContent.setKey(item.key);
                    qiniuContent.setType(config.getType());
                    qiniuContent.setBucket(config.getBucket());
                    qiniuContent.setUrl(config.getHost() + "/" + item.key);
                    qiniuContentRepository.save(qiniuContent);
                }
            }
        }
    }
}
