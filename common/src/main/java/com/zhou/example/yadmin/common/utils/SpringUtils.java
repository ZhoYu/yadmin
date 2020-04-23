/**
 * 文件名称:    SpringUtils<br />
 * 项目名称:    springexample<br />
 * 创建时间:    14:19<br />
 *
 * @author zhou
 * @since 2014/9/18 14:19
 */
package com.zhou.example.yadmin.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * spring工具类,封装一些可能用到的工具方法<br />
 * 将接口 {@link org.springframework.context.ApplicationContextAware ApplicationContextAware} 换成 {@link BeanFactoryPostProcessor}
 * {@link org.springframework.context.ApplicationContextAware ApplicationContextAware} 执行顺序是在实例化bean之后,
 * 而 {@link BeanFactoryPostProcessor} 执行顺序是在实例化所有bean之前执行
 * <p/>
 * 项目名称: springexample<br />
 * 版本: <br />
 * 作者: zhou<br />
 * 日期: 2014/9/18 14:19<br />
 *
 * @author zhou
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor
{
    /**
     * spring应用 上下文
     */
    private static ConfigurableListableBeanFactory beanFactory;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException
    {
        SpringUtils.beanFactory = beanFactory;
    }

    //    /**
    //     * 实现ApplicationContextAware接口的回调方法，设置上下文环境
    //     * @param applicationContext spring应用 上下文
    //     * @throws BeansException
    //     */
    //    @Override
    //    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    //    {
    //        beanFactory = applicationContext;
    //    }

    /**
     * 获取对象
     *
     * @param name bean的名称
     * @param <T> 返回bean对象的类型
     *
     * @return 一个以所给名字注册的bean的实例
     *
     * @throws BeansException
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws BeansException
    {
        return (T) beanFactory.getBean(name);
    }

    /**
     * 获取类型为 clazz 的对象
     *
     * @param clazz 对象类型
     * @param <T> 返回对象类型
     *
     * @return 类型为 clazz 的对象
     *
     * @throws BeansException
     */
    public static <T> T getBean(Class<T> clazz) throws BeansException
    {
        return beanFactory.getBean(clazz);
    }

    /**
     * 如果BeanFactory包含一个与所给名称匹配的bean定义，则返回true
     *
     * @param name bean的名称
     *
     * @return spring应用上下文中是否存在这个bean名称对应的对象
     */
    public static boolean containsBean(String name)
    {
        return beanFactory.containsBean(name);
    }

    /**
     * 判断以给定名字注册的bean定义是一个singleton还是一个prototype。
     * 如果与给定名字相应的bean定义没有被找到，将会抛出一个异常（NoSuchBeanDefinitionException）
     *
     * @param name bean的名称
     *
     * @return bean对象是否是单例
     *
     * @throws NoSuchBeanDefinitionException
     */
    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException
    {
        return beanFactory.isSingleton(name);
    }

    /**
     * 获取bean对象的类型
     *
     * @param name bean的名称
     *
     * @return Class 注册对象的类型
     *
     * @throws NoSuchBeanDefinitionException
     */
    public static Class<?> getType(String name) throws NoSuchBeanDefinitionException
    {
        return beanFactory.getType(name);
    }

    /**
     * 如果给定的bean名字在bean定义中有别名，则返回这些别名
     *
     * @param name bean的名称
     *
     * @return 返回bean对象的别名
     *
     * @throws NoSuchBeanDefinitionException
     */
    public static String[] getAliases(String name) throws NoSuchBeanDefinitionException
    {
        return beanFactory.getAliases(name);
    }
}
