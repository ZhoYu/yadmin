/**
 * <p>
 * 文件名称:    MenuMetaDto
 * </p>
 */
package com.zhou.yadmin.system.authorization.dto;

import java.io.Serializable;

/**
 * <p>
 * 类注释
 * </p>
 *
 * @author yuZhou
 * @since 2020/2/28 21:27
 */
public class MenuMetaDto implements Serializable
{
    private static final long serialVersionUID = -507394961642881996L;

    private String title;

    private String icon;

    public MenuMetaDto(String title, String icon)
    {
        this.title = title;
        this.icon = icon;
    }

    /**
     * Gets the value of title
     *
     * @return the value of title
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * Sets the title
     * <p>You can use getTitle() to get the value of title</p>
     *
     * @param title title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * Gets the value of icon
     *
     * @return the value of icon
     */
    public String getIcon()
    {
        return icon;
    }

    /**
     * Sets the icon
     * <p>You can use getIcon() to get the value of icon</p>
     *
     * @param icon icon
     */
    public void setIcon(String icon)
    {
        this.icon = icon;
    }
}
