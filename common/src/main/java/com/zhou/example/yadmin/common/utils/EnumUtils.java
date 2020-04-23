/**
 * <p>
 * 文件名称:    com.zhou.simple.pay.common.utils.EnumUtils
 * </p>
 * <p>
 * 创建时间:    11:33
 * </p>
 *
 * @author yuZhou
 * @since 2017-02-10 11:33
 */
package com.zhou.example.yadmin.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.common.collect.Maps;
import com.zhou.example.yadmin.common.enums.ValueDescBaseEnum;

/**
 * <p>
 * 枚举工具类.扩展枚举
 * </p>
 * <p>日期: 2017-02-10 11:33   </p>
 *
 * @author yuZhou
 */
public class EnumUtils
{
    public static DataHandle valueDescDataHandle = new DataHandle()
    {
        @Override
        public <T extends Enum<T> & ValueDescBaseEnum> Map<String, String> execute(T value)
        {
            Map<String, String> valueMap = Maps.newHashMapWithExpectedSize(2);
            valueMap.put("value", String.valueOf(value.getValue()));
            valueMap.put("desc", value.getDesc());
            return valueMap;
        }
    };

    public static DataHandle nameDescDataHandle = new DataHandle()
    {
        @Override
        public <T extends Enum<T> & ValueDescBaseEnum> Map<String, String> execute(T value)
        {
            Map<String, String> valueMap = Maps.newHashMapWithExpectedSize(2);
            valueMap.put("value", value.name());
            valueMap.put("desc", value.getDesc());
            return valueMap;
        }
    };

    public static DataHandle nameValueDescDataHandle = new DataHandle()
    {
        @Override
        public <T extends Enum<T> & ValueDescBaseEnum> Map<String, String> execute(T value)
        {
            Map<String, String> valueMap = Maps.newHashMapWithExpectedSize(3);
            valueMap.put("value", String.valueOf(value.getValue()));
            valueMap.put("desc", value.getDesc());
            valueMap.put("name", value.toString());
            return valueMap;
        }
    };

    public static <T extends Enum<T> & ValueDescBaseEnum> T getEnum(int value, Class<T> enumClass)
    {
        T result = null;
        for (T e : enumClass.getEnumConstants())
        {
            if (e.getValue() == value)
            {
                result = e;
                break;
            }
        }
        return result;
    }

    public static <T extends Enum<T> & ValueDescBaseEnum> T getEnum(String desc, Class<T> enumClass)
    {
        T result = null;
        for (T e : enumClass.getEnumConstants())
        {
            if (e.getDesc().equals(desc))
            {
                result = e;
                break;
            }
        }
        return result;
    }

    public static <T extends Enum<T> & ValueDescBaseEnum> List<Map<String, String>> toList(Class<T> enumClass)
    {
        return toListByPredicate(enumClass, t -> true);
    }

    public static <T extends Enum<T> & ValueDescBaseEnum> List<Map<String, String>> toListByValues(Class<T> enumClass, int[] values)
    {
        if (values == null)
        {
            values = new int[0];
        }
        int[] finalValues = values;
        Predicate<T> predicate = e -> Arrays.stream(finalValues).anyMatch(value -> value == e.getValue());
        return toListByPredicate(enumClass, predicate);
    }

    public static <T extends Enum<T> & ValueDescBaseEnum> List<Map<String, String>> toListByPredicate(Class<T> enumClass, Predicate<T> predicate)
    {
        return toListByPredicate(enumClass, predicate, valueDescDataHandle);
    }

    public static <T extends Enum<T> & ValueDescBaseEnum> List<Map<String, String>> toListByPredicate(Class<T> enumClass, Predicate<T> predicate,
      DataHandle handle)
    {
        return Arrays.stream(enumClass.getEnumConstants()).filter(predicate).map(handle::execute).collect(Collectors.toList());
    }

    public static <T extends Enum<T> & ValueDescBaseEnum> Map<String, Map<String, String>> toMap(Class<T> enumClass)
    {
        return toMap(enumClass, valueDescDataHandle);
    }

    public static <T extends Enum<T> & ValueDescBaseEnum> Map<String, Map<String, String>> toMap(Class<T> enumClass, DataHandle handle)
    {
        Map<String, Map<String, String>> map = Maps.newHashMap();
        for (T value : enumClass.getEnumConstants())
        {
            String key = String.valueOf(value);
            Map<String, String> valueMap = handle.execute(value);
            map.put(key, valueMap);
        }
        return map;
    }

    public static <T extends Enum<T> & ValueDescBaseEnum> Map<Integer, T> toValueMap(Class<T> enumClass)
    {
        Map<Integer, T> map = Maps.newHashMap();
        for (T value : enumClass.getEnumConstants())
        {
            map.put(value.getValue(), value);
        }
        return map;
    }

    public static <T extends Enum<T> & ValueDescBaseEnum> String getJsonStr(Class<T> enumClass)
    {
        StringBuilder builder = new StringBuilder("[");
        char objectSeparatorChar = ',';
        for (T valueEnum : enumClass.getEnumConstants())
        {
            builder.append("{\"id\": \"").append(valueEnum).append("\", desc: \"").append(valueEnum.getDesc()).append("\", value: ")
              .append(valueEnum.getValue()).append("}").append(objectSeparatorChar);
        }
        int endCharIndex = builder.length() - 1;
        if (builder.charAt(endCharIndex) == objectSeparatorChar)
        {
            builder.deleteCharAt(endCharIndex);
        }
        builder.append(']');
        return builder.toString();
    }

    public static <T extends Enum<T> & ValueDescBaseEnum> Map<String, String> getEnumDataMap(T value)
    {
        return valueDescDataHandle.execute(value);
    }

    public interface DataHandle
    {
        <T extends Enum<T> & ValueDescBaseEnum> Map<String, String> execute(T value);
    }
}
