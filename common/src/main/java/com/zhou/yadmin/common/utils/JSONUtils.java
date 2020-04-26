/**
 * 文件名称:    com.zhou.yadmin.common.utils.JSONUtils<br />
 * 项目名称:    springexample<br />
 * 创建时间:    下午2:36<br />
 *
 * @author zhou<br />
 * @since 13-10-8 下午2:36<br />
 */
package com.zhou.yadmin.common.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.zhou.yadmin.common.constants.DateConstant;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Jackson工具类<br />
 * <p>
 * 默认给{@link ObjectMapper}设置一些默认的规则.<br />
 * 如: <br />
 * <ul>
 * <li>允许未加引号的变量名</li>
 * <li>忽略在JSON字符串中存在但Java对象实际没有的属性</li>
 * <li>设置默认时间格式为: yyyy-MM-dd HH:mm:ss</li>
 * </ul>
 * </p>
 * 项目名称: springexample<br />
 * 版本: <br />
 * 作者: zhou<br />
 * 日期: 13-10-8 下午2:36<br />
 *
 * @author zhou<br />
 */
public final class JSONUtils
{
    /**
     * 日志
     */
    private static Logger logger = LoggerFactory.getLogger(JSONUtils.class);
    /**
     * 公用的Jackson工具对象
     */
    private final static ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    /**
     * 默认时间格式
     */
    private final static String DATE_FORMAT_PATTERN = DateConstant.DATE_TIME_PATTERN;
    
    static
    {
        /**
         * 允许未加引号的变量名
         */
        OBJECT_MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, false);
        /**
         * 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
         */
        OBJECT_MAPPER.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        // 注册 jdk8 的支持
        OBJECT_MAPPER.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module()).registerModule(getJavaTimeModule());
        // 支持 jaxb 注解
        enableJaxbAnnotation();
        /**
         * 设置默认的时间格式
         */
        setDateFormat(DATE_FORMAT_PATTERN);
    }
    
    /**
     * 私有化构造方法, 工具类不允许实例化,只能使用静态方法
     */
    private JSONUtils()
    {
    }
    
    /**
     * 取出Jackson的ObjectMapper做进一步的设置或使用其他序列化API.
     *
     * @return {@link ObjectMapper}
     */
    public static ObjectMapper getObjectMapper()
    {
        return OBJECT_MAPPER;
    }
    
    /**
     * 构造方法，根据Jackson的{@link Include}类创建ObjectMapper
     *
     * @param include {@link Include}
     */
    private static void setObjectMapperProperties(Include include)
    {
        if (include != null)
        {
            OBJECT_MAPPER.setSerializationInclusion(include);
        }
    }
    
    /**
     * 设置ObjectMapper序列化属性时 所有对象默认为是空值
     * <p>
     * 默认的空值判断有以下类型:
     * <ul>
     * <li>
     * 如果是 {@link java.util.Collections} 或 {@link java.util.Map} 。
     * 根据 <code>isEmpty()</code>方法返回值判断是否序列化
     * </li>
     * <li>
     * 如果是Java数组，长度等于0都为空值的情况
     * </li>
     * <li>
     * 如是String类型根据 <code>length()</code>方法返回值判断是否序列化,如果为0表示该值为空
     * </li>
     * </ul>
     * </p>
     * 其他类型的默认处理为：空值将一样加入。
     * <p>
     * 提示：其他类型的默认处理可以自定义{@link com.fasterxml.jackson.databind.JsonSerializer}
     * 的{@link com.fasterxml.jackson.databind.JsonSerializer#isEmpty(Object)}如果被重写，
     * 序列化json会调用该方法，根据返回值判断是否序列化
     * </p>
     */
    public static void nonEmptyObjectMapper()
    {
        setObjectMapperProperties(Include.NON_EMPTY);
    }
    
    /**
     * 设置ObjectMapper序列化时不管任何值都序列化成json
     */
    public static void alwaysObjectMapper()
    {
        setObjectMapperProperties(Include.ALWAYS);
    }
    
    /**
     * 设置ObjectMapper序列化属性为非空(null)值
     */
    public static void nonNullObjectMapper()
    {
        setObjectMapperProperties(Include.NON_NULL);
    }
    
    /**
     * 设置ObjectMapper只输出初始值被改变的属性到Json字符串的ObjectMapper, 最节约的存储方式。
     */
    public static void nonDefaultObjectMapper()
    {
        setObjectMapperProperties(Include.NON_DEFAULT);
    }
    
    /**
     * 设置是否使用Enum的toString函数来读取Enum,为false时使用Enum的name()函数类读取Enum, 默认为false.
     */
    public static void enableEnumUseToString()
    {
        OBJECT_MAPPER.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
        OBJECT_MAPPER.enable(DeserializationFeature.READ_ENUMS_USING_TO_STRING);
    }
    
    /**
     * 支持使用Jaxb的Annotation,使得POJO上的annotation不用与Jackson藕合.
     * 默认会先查找jaxb的annotation,如果找不到再找jackson的.
     */
    public static void enableJaxbAnnotation()
    {
        OBJECT_MAPPER.registerModule(new JaxbAnnotationModule());
    }
    
    /**
     * 设置转换日期类型的时间格式,如果不设置默认打印Timestamp毫秒数.
     *
     * @param pattern 时间格式化字符串
     */
    public static void setDateFormat(String pattern)
    {
        if (StringUtils.isNotBlank(pattern))
        {
            setDateFormat(new SimpleDateFormat(pattern));
        }
    }
    
    /**
     * 设置转换日期类型的时间格式,如果不设置默认打印Timestamp毫秒数.
     *
     * @param dateFormat 时间格式化对象
     */
    public static void setDateFormat(DateFormat dateFormat)
    {
        if (null != dateFormat)
        {
            OBJECT_MAPPER.setDateFormat(dateFormat);
        }
    }
    
    /**
     * 将json字符串转换为对象.
     * <pre>
     * 如果json字符串为null或"null"字符串,返回null. 如果json字符串为"[]",返回空集合.
     * 如需读取集合如List/Map,且不是List&lt;String&gt;这种简单类型时使用如下语句:
     * List<&lt;>MyBean&gt; beanList = {@linkplain #json2Object(String, TypeReference) json2Object(listString, new TypeReference&lt;List&lt;MyBean&gt;&gt;（）{ }）}
     * </pre>
     *
     * @param jsonString  json字符串
     * @param targetClass 转换对象的Class
     * @return <T> 转换后的对象.
     */
    public static <T> T json2Object(String jsonString, Class<T> targetClass)
    {
        if (StringUtils.isNotEmpty(jsonString))
        {
            try
            {
                return OBJECT_MAPPER.readValue(jsonString, targetClass);
            }
            catch (IOException e)
            {
                logger.warn("parse jsonString string error: {}", jsonString, e);
            }
        }
        return null;
    }
    
    /**
     * 反序列化复杂Collection如List<Bean>, 先使用函数createCollectionType构造类型,然后调用本函数.
     *
     * @param jsonString json字符串
     * @param javaType   java类型
     * @return 转换后的对象.
     * @see #createCollectionType(Class, Class...)
     */
    public static <T> T json2Object(String jsonString, JavaType javaType)
    {
        if (StringUtils.isNotEmpty(jsonString))
        {
            try
            {
                return OBJECT_MAPPER.readValue(jsonString, javaType);
            }
            catch (IOException e)
            {
                logger.warn("parse jsonString string error: {}", jsonString, e);
            }
        }
        return null;
    }
    
    /**
     * JSON串转换为Java泛型对象，可以是各种类型，此方法最为强大。
     *
     * @param jsonString JSON字符串
     * @param tr         TypeReference,例如: new TypeReference<List<FamousUser>>(){}
     * @return List对象列表
     */
    public static <T> T json2Object(String jsonString, TypeReference<T> tr)
    {
        if (StringUtils.isNotEmpty(jsonString))
        {
            try
            {
                return OBJECT_MAPPER.readValue(jsonString, tr);
            }
            catch (Exception e)
            {
                logger.warn("parse jsonString string error: {}", jsonString, e);
            }
        }
        return null;
    }
    
    /**
     * 当JSON里只含有Bean的部分属性时，更新一个已存在Bean，只覆盖部分属性.
     *
     * @param jsonString json字符串
     * @param object     目标对象
     * @param <T>        目标类型
     * @return 更新后的对象
     */
    public static <T> T update(String jsonString, T object)
    {
        try
        {
            return OBJECT_MAPPER.readerForUpdating(object).readValue(jsonString);
        }
        catch (IOException e)
        {
            logger.warn("update json string: {} to object: {} error.", jsonString, object, e);
        }
        return null;
    }
    
    /**
     * 将对象转换成json字符串,如果对象为Null,返回"null". 如果集合为空集合,返回"[]".
     *
     * @param target 转换为json的对象
     * @return json格式的字符串
     */
    public static String object2Json(Object target)
    {
        try
        {
            return OBJECT_MAPPER.writeValueAsString(target);
        }
        catch (IOException e)
        {
            logger.warn("write to json string error: {}", target, e);
        }
        return null;
    }
    
    /**
     * 将对象转换成JsonP格式字符串
     *
     * @param function JsonP回调方法名
     * @param target   转换为JsonP的对象
     * @return jsonP格式字符串
     */
    public static String object2JsonP(String function, Object target)
    {
        return object2Json(new JSONPObject(function, target));
    }
    
    /**
     * 构造泛型的Collection Type如:
     * <p>
     * ArrayList<MyBean>, 则调用constructCollectionType(ArrayList.class,MyBean.class)
     * HashMap<String,MyBean>, 则调用(HashMap.class,String.class, MyBean.class)
     * </p>
     */
    public static JavaType createCollectionType(Class<?> collectionClass, Class<?>... elementClasses)
    {
        return OBJECT_MAPPER.getTypeFactory().constructParametricType(collectionClass, elementClasses);
    }

    public static JavaTimeModule getJavaTimeModule()
    {
        // java8日期日期处理
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern(DateConstant.DATE_TIME_PATTERN)));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern(DateConstant.DATE_PATTERN)));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern(DateConstant.TIME_PATTERN)));
        javaTimeModule
          .addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern(DateConstant.DATE_TIME_PATTERN)));
        javaTimeModule.addDeserializer(LocalDate.class, new LocalDateDeserializer(DateTimeFormatter.ofPattern(DateConstant.DATE_PATTERN)));
        javaTimeModule.addDeserializer(LocalTime.class, new LocalTimeDeserializer(DateTimeFormatter.ofPattern(DateConstant.TIME_PATTERN)));
        return javaTimeModule;
    }
}