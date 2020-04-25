/**
 * <p>
 * 文件名称:    com.zhou.example.yadmin.tools.web.controller.PictureController
 * </p>
 */
package com.zhou.example.yadmin.tools.web.controller;

import java.util.Map;

import com.google.common.collect.Maps;
import com.zhou.example.yadmin.common.utils.WebUtils;
import com.zhou.example.yadmin.logging.aop.annotation.Log;
import com.zhou.example.yadmin.tools.domain.Picture;
import com.zhou.example.yadmin.tools.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/16 2:14
 */
@RestController
@RequestMapping("api/pictures")
public class PictureController
{
    @Autowired
    private PictureService pictureService;

    @Log("查询图片")
    @GetMapping(value = "")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_SELECT')")
    public ResponseEntity getRoles(Picture resources, Pageable pageable)
    {
        return new ResponseEntity(pictureService.queryAll(resources, pageable), HttpStatus.OK);
    }

    /**
     * 上传图片
     *
     * @param file
     *
     * @return
     *
     * @throws Exception
     */
    @Log("上传图片")
    @PostMapping(value = "")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_UPLOAD')")
    public ResponseEntity upload(@RequestParam MultipartFile file)
    {
        UserDetails userDetails = WebUtils.getUserDetails();
        String userName = userDetails.getUsername();
        Picture picture = pictureService.upload(file, userName);
        Map<String, Object> map = Maps.newHashMapWithExpectedSize(3);
        map.put("errno", 0);
        map.put("id", picture.getId());
        map.put("data", new String[]{picture.getUrl()});
        return new ResponseEntity(map, HttpStatus.OK);
    }

    /**
     * 删除图片
     *
     * @param id
     *
     * @return
     */
    @Log("删除图片")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','PICTURE_ALL','PICTURE_DELETE')")
    public ResponseEntity delete(@PathVariable Long id)
    {
        pictureService.delete(pictureService.findById(id));
        return new ResponseEntity(HttpStatus.OK);
    }
}
