/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: ListUtils
 * Author:   luffy
 * Date:   2018/4/24 16:37
 * Since: 1.0.0
 */
package com.sn.gz.core.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 工具类
 *
 * @author luffy
 * @since 1.0.0
 * 2018/4/24
 */
@SuppressWarnings("unused")
public class ListUtils {

    private ListUtils() {
    }

    /**
     * 判空
     *
     * @param list List
     * @return boolean
     * @author luffy
     * 2018/4/24 16:38
     * @since 1.0.0
     */
    public static boolean isNotNull(List<?> list) {
        return (null != list) && (!list.isEmpty());
    }

    /**
     * 判空
     *
     * @param list List
     * @return boolean
     * @author luffy
     * 2018/4/24 16:39
     * @since 1.0.0
     */
    public static boolean isNull(List<?> list) {

        return (null == list || list.isEmpty());

    }

    /**
     * 分割List
     *
     * @param list     待分割的List
     * @param pageSize 子list元素最大值
     * @return java.util.List
     * @author xiaoxueliang
     * 2018/5/4 14:59
     * @since 1.0.0
     */
    public static <T> List<List<T>> split(List<T> list, int pageSize) {
        //list的大小
        int listSize = list.size();
        //页数
        int page = (listSize + (pageSize - 1)) / pageSize;
        //创建list数组 ,用来保存分割后的list
        List<List<T>> listArray = new ArrayList<>();
        //按照数组大小遍历
        for (int i = 0; i < page; i++) {
            //数组每一位放入一个分割后的list
            List<T> subList = new ArrayList<>();
            //遍历待分割的list
            for (int j = 0; j < listSize; j++) {
                //当前记录的页码(第几页)
                int pageIndex = ((j + 1) + (pageSize - 1)) / pageSize;
                //当前记录的页码等于要放入的页码时
                if (pageIndex == (i + 1)) {
                    //放入list中的元素到分割后的list(subList)
                    subList.add(list.get(j));
                }
                //当放满一页时退出当前循环
                if ((j + 1) == ((j + 1) * pageSize)) {
                    break;
                }
            }
            //将分割后的list放入对应的数组的位中
            listArray.add(subList);
        }
        return listArray;
    }

    /**
     * 连接字符
     *
     * @param collection 待连接字符集合
     * @param separate   连接分隔符
     * @return java.lang.String 连接后的字符串
     * @author xiaoxueliang
     * 2018/5/5 18:17
     * @since 1.0.0
     */
    public static String join(Collection<String> collection, String separate) {
        if (CollectionUtils.isEmpty(collection)) {
            return "";
        }
        String sep = separate;
        if (sep == null) {
            sep = "";
        }
        StringBuilder sb = new StringBuilder();
        for (String str : collection) {
            sb.append(str);
            sb.append(sep);
        }
        if ("".equals(sep)) {
            return sb.toString();
        } else {
            return sb.substring(0, sb.lastIndexOf(sep));
        }
    }

    /**
     * 将集合中指定属性连接为字符串
     *
     * @param sourceCollection 待连接的集合
     * @param keyName          指定属性名
     * @param separator        分隔符
     * @param lastSeparator    是否需要最后一个分隔符（默认false）
     * @return java.lang.String
     * @author Enma.ai
     * 2018/8/30
     */
    public static <V> String spliceStr(Collection<V> sourceCollection, String keyName, String separator, Boolean lastSeparator) {
        if (null == sourceCollection || sourceCollection.isEmpty() || StringUtils.isBlank(keyName)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        String sep = separator;
        if (null == sep) {
            sep = "";
        }
        try {
            for (V v : sourceCollection) {
                // 拿到该类
                Class<?> clz = v.getClass();
                // 获取实体类中声明的所有属性，返回Field数组
                Field[] fields = clz.getDeclaredFields();
                for (Field field : fields) {
                    String fieldName = field.getName();
                    if (fieldName.equals(keyName)) {
                        Object val = ReflectUtils.getFieldValueWithMethod(v, field.getName());
                        if (null != val) {
                            sb.append(val.toString());
                            sb.append(sep);
                        }
                        break;
                    }
                }
            }
            if (!lastSeparator) {
                sb.deleteCharAt(sb.lastIndexOf(sep));
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return sb.toString();
    }

    /**
     * 基本类型list去重
     *
     * @param list 集合
     * @return java.util.List<T>
     * @author Enma.ai
     * 2018/5/9
     * @since 1.0.0
     */
    public static <T> List<T> commonDistinct(List<T> list) {
        Set<T> set = new HashSet<>(list);
        return new LinkedList<>(set);
    }

    /**
     * String转Long
     *
     * @param list LongList
     * @return java.util.List
     * @author Enma.ai
     * 2018/8/18
     */
    public static List<String> longList2Str(List<Long> list) {
        List<String> stringList = new ArrayList<>();
        if (null != list) {
            for (Long aLong : list) {
                stringList.add(String.valueOf(aLong));
            }
        }
        return stringList;
    }

    /**
     * Long转String
     *
     * @param list stringList
     * @return java.util.List
     * @author Enma.ai
     * 2018/8/18
     */
    public static List<Long> strList2Long(List<String> list) {
        List<Long> longList = new ArrayList<>();
        if (null != list) {
            for (String s : list) {
                longList.add(Long.valueOf(s));
            }
        }
        return longList;
    }

    /**
     * list 转map
     *
     * @param list          list
     * @param keyMethodName getXXX
     * @param c             class
     * @return java.util.Map<K                                                                                                                               ,                                                                                                                               V>
     * @author lufeiwang
     * 2019/4/26
     */
    public static <K, V> Map<K, V> list2Map(List<V> list, String keyMethodName, Class<V> c) {
        Map<K, V> map = new HashMap<K, V>();
        if (list != null) {
            try {
                Method methodGetKey = c.getMethod(keyMethodName);
                for (int i = 0; i < list.size(); i++) {
                    V value = list.get(i);
                    @SuppressWarnings("unchecked")
                    K key = (K) methodGetKey.invoke(list.get(i));
                    map.put(key, value);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("field can't match the key!");
            }
        }
        return map;
    }

    /**
     * list 转list
     *
     * @param list          list
     * @param keyMethodName getXXX
     * @param c             class
     * @return java.util.Map<K                                                                                                                               ,                                                                                                                               V>
     * @author lufeiwang
     * 2019/4/26
     */
    public static <S, V> List<S> list2List(List<V> list, String keyMethodName, Class<V> c) {
        List<S> newList = new ArrayList<>();
        if (list != null) {
            try {
                Method methodGetKey = c.getMethod(keyMethodName);
                for (int i = 0; i < list.size(); i++) {
                    V value = list.get(i);
                    @SuppressWarnings("unchecked")
                    S item = (S) methodGetKey.invoke(list.get(i));
                    newList.add(item);
                }
            } catch (Exception e) {
                throw new IllegalArgumentException("field can't match the key!");
            }
        }
        return newList;
    }

    /**
     * map 转 list
     *
     * @param map map
     * @author lufeiwang
     * 2019/4/26
     */
    public static <K, V> List<V> map2List(Map<K, V> map) {
        if (null == map || map.isEmpty()) {
            return null;
        }
        List<V> list = new ArrayList<>();
        for (Map.Entry<K, V> entry : map.entrySet()) {
            list.add(entry.getValue());
        }
        return list;
    }

}
