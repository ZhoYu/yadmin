/**
 * <p>
 * 文件名称:    PictureServiceImpl
 * </p>
 */
package com.zhou.yadmin.tools.service.impl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.common.collect.Lists;
import com.zhou.yadmin.common.exception.BadRequestException;
import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.common.utils.FileUtils;
import com.zhou.yadmin.common.utils.JSONUtils;
import com.zhou.yadmin.common.utils.PageUtils;
import com.zhou.yadmin.common.utils.ValidationUtils;
import com.zhou.yadmin.tools.domain.Picture;
import com.zhou.yadmin.tools.repository.PictureRepository;
import com.zhou.yadmin.tools.service.PictureService;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/16 0:00
 */
@Service
@CacheConfig(cacheNames = "picture")
public class PictureServiceImpl extends AbstractBaseComponent implements PictureService
{
    public static final String SUCCESS = "success";

    public static final String CODE = "code";

    public static final String MSG = "msg";

    @Autowired
    private PictureRepository pictureRepository;

    /**
     * 上传图片
     *
     * @param multipartFile
     * @param username
     *
     * @return
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Throwable.class)
    public Picture upload(MultipartFile multipartFile, String username)
    {
        File file = FileUtils.toFile(multipartFile);
        //将参数合成一个请求
        RestTemplate rest = new RestTemplate();
        FileSystemResource resource = new FileSystemResource(file);
        MultiValueMap<String, Object> param = new LinkedMultiValueMap<>();
        param.add("smfile", resource);

        //设置头部，必须
        HttpHeaders headers = new HttpHeaders();
        headers.setAccept(Lists.newArrayList(MediaType.APPLICATION_JSON));
        headers
          .add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");

        HttpEntity<MultiValueMap<String, Object>> httpEntity = new HttpEntity<>(param, headers);
        ResponseEntity<String> responseEntity = rest.exchange("https://sm.ms/api/upload", HttpMethod.POST, httpEntity, String.class);

        JSONObject jsonObject = JSONUtil.parseObj(responseEntity.getBody());
        // JSONUtils.json2Object(responseEntity.getBody(), )
        Picture picture = null;
        if (!jsonObject.get(CODE).toString().equals(SUCCESS))
        {
            throw BadRequestException.newExceptionByTools(jsonObject.get(MSG).toString());
        }
        //转成实体类
        picture = JSONUtils.json2Object(jsonObject.get("data").toString(), Picture.class);
        picture.setSize(FileUtils.getSize(Long.parseLong(picture.getSize())));
        picture.setUsername(username);
        picture.setFilename(FilenameUtils.getBaseName(multipartFile.getOriginalFilename()) + FilenameUtils.EXTENSION_SEPARATOR +
                            FilenameUtils.getExtension(multipartFile.getOriginalFilename()));
        pictureRepository.save(picture);
        //删除临时文件
        FileUtils.deleteFile(file);
        return picture;
    }

    /**
     * 根据ID查询
     *
     * @param id
     *
     * @return
     */
    @Override
    @Cacheable(key = "#p0")
    @Transactional(readOnly = true)
    public Picture findById(Long id)
    {
        Optional<Picture> pictureOptional = pictureRepository.findById(id);
        ValidationUtils.isNull(pictureOptional, Picture.class.getSimpleName(), "id", id);
        return pictureOptional.get();
    }

    /**
     * 删除图片
     *
     * @param picture
     */
    @Override
    @CacheEvict(allEntries = true)
    @Transactional(propagation = Propagation.SUPPORTS, rollbackFor = Exception.class)
    public void delete(Picture picture)
    {
        RestTemplate template = new RestTemplate();
        try
        {
            ResponseEntity<String> response = template.getForEntity(picture.getDelete(), String.class);
            if (response.getStatusCode().is2xxSuccessful())
            {
                pictureRepository.delete(picture);
            }
        }
        catch (Exception e)
        {
            // 如果删除的地址出错，直接删除数据库数据
            logger.error("删除错误", e);
            pictureRepository.delete(picture);
        }
    }

    /**
     * 分页
     *
     * @param picture
     * @param pageable
     *
     * @return
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(keyGenerator = "keyGenerator")
    public Map<String, Object> queryAll(Picture picture, Pageable pageable)
    {
        return PageUtils.toPage(pictureRepository.findAll(new CustomPictureSpecification(picture),pageable));
    }

    static class CustomPictureSpecification implements Specification<Picture>
    {
        private static final long serialVersionUID = -4714868337667068290L;

        private Picture picture;

        public CustomPictureSpecification(Picture picture)
        {
            this.picture = picture;
        }

        @Override
        public Predicate toPredicate(Root<Picture> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder)
        {
            List<Predicate> predicateList = Lists.newArrayList();
            if (!ObjectUtils.isEmpty(picture.getFilename()))
            {
                predicateList.add(criteriaBuilder.like(root.get("filename").as(String.class), "%" + picture.getFilename() + "%")); // 模糊
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        }
    }
}
