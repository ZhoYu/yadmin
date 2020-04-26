/**
 * <p>
 * 文件名称:    GeneratorConfig
 * </p>
 */
package com.zhou.yadmin.generator.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.zhou.yadmin.common.domain.BaseEntity;

/**
 * <p>
 * 代码生成配置
 * </p>
 *
 * @author yuZhou
 * @since 2020/4/17 19:54
 */
@Entity
@Table(name = "t_generator_config")
public class GeneratorConfig extends BaseEntity<Long>
{
    private static final long serialVersionUID = 6472516976672332546L;
    /**
     * 包路径
     **/
    private String pack;

    /**
     * 模块名
     **/
    @Column(name = "module_name")
    private String moduleName;

    /**
     * 前端文件路径
     **/
    private String path;

    /**
     * 前端文件路径
     **/
    @Column(name = "api_path")
    private String apiPath;

    /**
     * 作者
     **/
    private String author;

    /**
     * 是否覆盖
     **/
    private Boolean cover;

    /**
     * Gets the value of pack
     *
     * @return the value of pack
     */
    public String getPack()
    {
        return pack;
    }

    /**
     * Sets the pack
     * <p>You can use getPack() to get the value of pack</p>
     *
     * @param pack pack
     */
    public void setPack(String pack)
    {
        this.pack = pack;
    }

    /**
     * Gets the value of moduleName
     *
     * @return the value of moduleName
     */
    public String getModuleName()
    {
        return moduleName;
    }

    /**
     * Sets the moduleName
     * <p>You can use getModuleName() to get the value of moduleName</p>
     *
     * @param moduleName moduleName
     */
    public void setModuleName(String moduleName)
    {
        this.moduleName = moduleName;
    }

    /**
     * Gets the value of path
     *
     * @return the value of path
     */
    public String getPath()
    {
        return path;
    }

    /**
     * Sets the path
     * <p>You can use getPath() to get the value of path</p>
     *
     * @param path path
     */
    public void setPath(String path)
    {
        this.path = path;
    }

    /**
     * Gets the value of apiPath
     *
     * @return the value of apiPath
     */
    public String getApiPath()
    {
        return apiPath;
    }

    /**
     * Sets the apiPath
     * <p>You can use getApiPath() to get the value of apiPath</p>
     *
     * @param apiPath apiPath
     */
    public void setApiPath(String apiPath)
    {
        this.apiPath = apiPath;
    }

    /**
     * Gets the value of author
     *
     * @return the value of author
     */
    public String getAuthor()
    {
        return author;
    }

    /**
     * Sets the author
     * <p>You can use getAuthor() to get the value of author</p>
     *
     * @param author author
     */
    public void setAuthor(String author)
    {
        this.author = author;
    }

    /**
     * Gets the value of cover
     *
     * @return the value of cover
     */
    public Boolean getCover()
    {
        return cover;
    }

    /**
     * Sets the cover
     * <p>You can use getCover() to get the value of cover</p>
     *
     * @param cover cover
     */
    public void setCover(Boolean cover)
    {
        this.cover = cover;
    }
}
