/**
 * <p>
 * 文件名称:    EmailDto
 * </p>
 */
package com.zhou.yadmin.tools.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 发送邮件时，接收参数的类
 * </p>
 *
 * @author yuZhou
 * @since 2020/3/15 23:27
 */
public class EmailDto implements Serializable
{
    private static final long serialVersionUID = 3776305775973150676L;

    /**
     * 收件人，支持多个收件人，用逗号分隔
     */
    @NotEmpty
    private List<String> toList;

    @NotBlank
    private String subject;

    @NotBlank
    private String content;

    public EmailDto()
    {
    }

    public EmailDto(@NotEmpty List<String> toList, @NotBlank String subject, @NotBlank String content)
    {
        this.toList = toList;
        this.subject = subject;
        this.content = content;
    }

    /**
     * Gets the value of toList
     *
     * @return the value of toList
     */
    public List<String> getToList()
    {
        return toList;
    }

    /**
     * Sets the toList
     * <p>You can use getToList() to get the value of toList</p>
     *
     * @param toList toList
     */
    public void setToList(List<String> toList)
    {
        this.toList = toList;
    }

    /**
     * Gets the value of subject
     *
     * @return the value of subject
     */
    public String getSubject()
    {
        return subject;
    }

    /**
     * Sets the subject
     * <p>You can use getSubject() to get the value of subject</p>
     *
     * @param subject subject
     */
    public void setSubject(String subject)
    {
        this.subject = subject;
    }

    /**
     * Gets the value of content
     *
     * @return the value of content
     */
    public String getContent()
    {
        return content;
    }

    /**
     * Sets the content
     * <p>You can use getContent() to get the value of content</p>
     *
     * @param content content
     */
    public void setContent(String content)
    {
        this.content = content;
    }
}
