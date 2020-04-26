/**
 * <p>
 * 文件名称:    QiniuController
 * </p>
 */
package com.zhou.yadmin.tools.web.controller;

import java.util.Map;

import com.google.common.collect.Maps;
import com.zhou.yadmin.common.plugin.AbstractBaseComponent;
import com.zhou.yadmin.logging.aop.annotation.Log;
import com.zhou.yadmin.tools.domain.QiniuConfig;
import com.zhou.yadmin.tools.domain.QiniuContent;
import com.zhou.yadmin.tools.service.QiniuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/19 22:51
 */
@RestController
@RequestMapping("api/qiniu")
public class QiniuController extends AbstractBaseComponent
{
    @Autowired
    private QiniuService qiniuService;

    @GetMapping(value = "/config")
    public ResponseEntity get()
    {
        return new ResponseEntity(qiniuService.find(), HttpStatus.OK);
    }

    @Log("配置七牛云存储")
    @PutMapping(value = "/config")
    public ResponseEntity emailConfig(@Validated @RequestBody QiniuConfig qiniuConfig)
    {
        qiniuService.update(qiniuConfig);
        return new ResponseEntity(HttpStatus.OK);
    }

    @Log("查询文件")
    @GetMapping(value = "/search")
    public ResponseEntity search(QiniuContent resources, Pageable pageable)
    {
        return new ResponseEntity(qiniuService.queryAll(resources, pageable), HttpStatus.OK);
    }

    /**
     * 上传文件到七牛云
     *
     * @param file
     *
     * @return
     */
    @Log("上传文件")
    @PostMapping(value = "/upload")
    public ResponseEntity upload(@RequestParam MultipartFile file)
    {
        QiniuContent qiniuContent = qiniuService.upload(file, qiniuService.find());
        Map<String, Object> map = Maps.newHashMap();
        map.put("errno", 0);
        map.put("id", qiniuContent.getId());
        map.put("data", new String[]{qiniuContent.getUrl()});
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * 同步七牛云数据到数据库
     *
     * @return
     */
    @Log("同步七牛云数据")
    @PostMapping(value = "/synchronize")
    public ResponseEntity synchronize()
    {
        logger.warn("REST request to synchronize qiniu 数据");
        qiniuService.synchronize(qiniuService.find());
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * 下载七牛云文件
     *
     * @param id
     *
     * @return
     *
     * @throws Exception
     */
    @Log("下载文件")
    @GetMapping(value = "/download/{id}")
    public ResponseEntity download(@PathVariable Long id)
    {
        return new ResponseEntity(qiniuService.download(qiniuService.findByContentId(id), qiniuService.find()), HttpStatus.OK);
    }

    /**
     * 删除七牛云文件
     *
     * @param id
     *
     * @return
     *
     * @throws Exception
     */
    @Log("删除文件")
    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity delete(@PathVariable Long id)
    {
        qiniuService.delete(qiniuService.findByContentId(id), qiniuService.find());
        return new ResponseEntity(HttpStatus.OK);
    }
}
