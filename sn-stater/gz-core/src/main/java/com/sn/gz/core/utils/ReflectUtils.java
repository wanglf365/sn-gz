/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: ReflectUtil
 * Author:   luffy
 * Date:   2018/4/24 14:40
 * Since: 1.0.0
 */
package com.sn.gz.core.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 反射工具类
 *
 * @author luffy
 * @since 1.0.0
 * 2018/4/24
 */
@Slf4j
@SuppressWarnings("unused")
public class ReflectUtils {
    private static String GET_METHOD_HEAD = "get";
    private static String SET_METHOD_HEAD = "set";

    /**
     * 从包package中获取所有的Class
     *
     * @param pack 包路径
     * @author luffy
     * 2018/4/24 18:31
     * @since 1.0.0
     */
    public static Set<Class<?>> getClasses(String pack) {

        // 第一个class类的集合
        Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
        // 是否循环迭代
        boolean recursive = true;
        // 获取包的名字 并进行替换
        String packageName = pack;
        String packageDirName = packageName.replace('.', '/');
        // 定义一个枚举的集合 并进行循环来处理这个目录下的things
        Enumeration<URL> dirs;
        try {
            dirs = Thread.currentThread().getContextClassLoader()
                    .getResources(packageDirName);
            // 循环迭代下去
            while (dirs.hasMoreElements()) {
                // 获取下一个元素
                URL url = dirs.nextElement();
                // 得到协议的名称
                String protocol = url.getProtocol();
                // 如果是以文件的形式保存在服务器上
                if ("file".equals(protocol)) {
                    log.debug("ReflectUtil.getClasses file类型的扫描");
                    // 获取包的物理路径
                    String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
                    // 以文件的方式扫描整个包下的文件 并添加到集合中
                    findAndAddClassesInPackageByFile(packageName, filePath,
                            recursive, classes);
                } else if ("jar".equals(protocol)) {
                    // 如果是jar包文件
                    // 定义一个JarFile
                    log.debug("ReflectUtil.getClasses jar类型的扫描");
                    JarFile jar;
                    try {
                        // 获取jar
                        jar = ((JarURLConnection) url.openConnection())
                                .getJarFile();
                        // 从此jar包 得到一个枚举类
                        Enumeration<JarEntry> entries = jar.entries();
                        // 同样的进行循环迭代
                        while (entries.hasMoreElements()) {
                            // 获取jar里的一个实体 可以是目录 和一些jar包里的其他文件 如META-INF等文件
                            JarEntry entry = entries.nextElement();
                            String name = entry.getName();
                            // 如果是以/开头的
                            if (name.charAt(0) == '/') {
                                // 获取后面的字符串
                                name = name.substring(1);
                            }
                            // 如果前半部分和定义的包名相同
                            if (name.startsWith(packageDirName)) {
                                int idx = name.lastIndexOf('/');
                                // 如果以"/"结尾 是一个包
                                if (idx != -1) {
                                    // 获取包名 把"/"替换成"."
                                    packageName = name.substring(0, idx)
                                            .replace('/', '.');
                                }
                                // 如果可以迭代下去 并且是一个包
                                if ((idx != -1) || recursive) {
                                    // 如果是一个.class文件 而且不是目录
                                    if (name.endsWith(".class")
                                            && !entry.isDirectory()) {
                                        // 去掉后面的".class" 获取真正的类名
                                        String className = name.substring(
                                                packageName.length() + 1,
                                                name.length() - 6);
                                        try {
                                            // 添加到classes
                                            classes.add(Class
                                                    .forName(packageName + '.'
                                                            + className));
                                        } catch (ClassNotFoundException e) {
                                            //("添加用户自定义视图类错误 找不到此类的.class文件");
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return classes;
    }


    /**
     * 以文件的形式来获取包下的所有Class
     *
     * @param packageName 包名
     * @param packagePath 包路径
     * @param recursive   是否递归
     * @param classes     类set
     * @author luffy
     * 2018/4/24 18:31
     * @since 1.0.0
     */
    private static void findAndAddClassesInPackageByFile(String packageName,
                                                         String packagePath, final boolean recursive, Set<Class<?>> classes) {
        // 获取此包的目录 建立一个File
        File dir = new File(packagePath);
        // 如果不存在或者 也不是目录就直接返回
        if (!dir.exists() || !dir.isDirectory()) {
            log.warn("用户定义包名 " + packageName + " 下没有任何文件");
            return;
        }
        // 如果存在 就获取包下的所有文件 包括目录
        File[] dirFiles = dir.listFiles(new FileFilter() {
            // 自定义过滤规则 如果可以循环(包含子目录) 或则是以.class结尾的文件(编译好的java类文件)
            @Override
            public boolean accept(File file) {
                return (recursive && file.isDirectory())
                        || (file.getName().endsWith(".class"));
            }
        });
        if (dirFiles == null){
            return;
        }
        // 循环所有文件
        for (File file : dirFiles) {
            // 如果是目录 则继续扫描
            if (file.isDirectory()) {
                findAndAddClassesInPackageByFile(
                        packageName + "." + file.getName(),
                        file.getAbsolutePath(), recursive, classes);
            } else {
                // 如果是java类文件 去掉后面的.class 只留下类名
                String className = file.getName().substring(0,
                        file.getName().length() - 6);
                try {
                    //这里用forName有一些不好，会触发static方法，没有使用classLoader的load干净
                    classes.add(Thread.currentThread().getContextClassLoader()
                            .loadClass(packageName + '.' + className));
                } catch (ClassNotFoundException e) {
                    log.error("添加用户自定义视图类错误 找不到此类的.class文件");
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 获取成员变量(包括private,protected)的修饰符
     * @param clazz 类
     * @param fieldName 字段名
     * @return int
     */
    public static <T> int getFieldModifier(Class<T> clazz, String fieldName)  {
        //getDeclaredFields可以获取所有修饰符的成员变量，包括private,protected等getFields则不可以
        Field[] fields = clazz.getDeclaredFields();

        for (Field field:fields) {
            if (field.getName().equals(fieldName)) {
                return field.getModifiers();
            }
        }
        return 0;
    }
    /**
     * 获取成员方法的修饰符
     * @param clazz 类
     * @param methodName 方法名
     * @return int
     */
    public static <T> int getMethodModifier(Class<T> clazz, String methodName)  {

        //getDeclaredMethods可以获取所有修饰符的成员方法，包括private,protected等getMethods则不可以
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method:methods) {
            if (method.getName().equals(methodName)) {
                return method.getModifiers();
            }
        }
        return 0;
    }

    /**
     * [对象]根据成员变量名称设置取其值
     * @param entity 类实例
     * @param fieldName 字段名
     * @param value 字段值
     */
    public static void setFieldValue(Object entity, String fieldName,Object value) throws   IllegalAccessException,NoSuchFieldException{

        Field field = entity.getClass().getDeclaredField(fieldName);
        //对于私有变量的访问权限，在这里设置，这样即可访问Private修饰的变量
        field.setAccessible(true);
        field.set(entity,value);
    }
    /**
     * [对象]根据成员变量名称获取其值
     * @param entity 类实例
     * @param fieldName 字段名
     * @return Object
     */
    public static Object getFieldValue(Object entity, String fieldName) throws   IllegalAccessException,NoSuchFieldException{

        Field field = entity.getClass().getDeclaredField(fieldName);
        //对于私有变量的访问权限，在这里设置，这样即可访问Private修饰的变量
        field.setAccessible(true);
        return field.get(entity);
    }
    /**
     * [类]根据成员变量名称获取其字段缺省默认值
     * @param clazz 类
     * @param fieldName 字段名
     * @return Object
     */
    public static <T> Object getClassFieldValue(Class<T> clazz, String fieldName) throws   IllegalAccessException, InstantiationException,NoSuchFieldException {
        T clazzInstance = clazz.newInstance();
        return getFieldValue(clazzInstance,fieldName);
    }
    /**
     * 获取该类（不包含父类）下声明所有的成员变量名称（包含private，public，protected）
     * @param clazz 类
     * @return java.lang.String[]
     */
    public static  String[] getFieldNames(Class clazz) {
        Field[] fields = clazz.getDeclaredFields();
        String[] fieldsArray = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            fieldsArray[i] = fields[i].getName();
        }
        return fieldsArray;
    }
    /**
     * 获取这个类所有包括（父类，祖先，到object类为止）所有申明的字段（public，protected，private）
     * @param clazz 对象类
     * @return java.lang.reflect.Field[]
     */
    public static  Field[] getClassAllDeclaredFields(Class clazz) {
        List<Field> allFieldList = new ArrayList<>();

        for (Class<?> curClass = clazz; curClass!=Object.class; curClass= curClass.getSuperclass()){
            Field[] fields = curClass.getDeclaredFields();
            allFieldList.addAll(Arrays.asList(fields));
        }

        return allFieldList.toArray(new Field[allFieldList.size()]);
    }

    /**
     * 指定类，调用缺省的无参方法
     * @param clazz  类
     * @param methodName  方法名
     * @return java.lang.Object
     */
    public static <T> Object invokeClass(Class<T> clazz, String methodName)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Object instance = clazz.newInstance();
        Method m = clazz.getMethod(methodName);
        return m.invoke(instance);
    }


    /**
     * 指定类，调用指定的方法
     * @param clazz  类
     * @param method  方法名
     * @param paramClasses 方法对应的参数类数组
     * @param params  方法对应的参数数组
     * @return java.lang.Object
     */
    public static <T> Object invokeClass(Class<T> clazz, String method, Class<T>[] paramClasses, Object[] params)
            throws InstantiationException, IllegalAccessException, NoSuchMethodException,  InvocationTargetException {
        Object instance = clazz.newInstance();
        Method m = clazz.getMethod(method, paramClasses);
        return m.invoke(instance, params);
    }

    /**
     * 通过类的实例，调用指定的方法
     * @param clazzInstance  类的实例
     * @param methodName  方法名
     * @param paramClasses 方法对应的参数类数组
     * @param params  方法对应的参数数组
     * @return java.lang.Object
     */
    public static <T> Object invoke(Object clazzInstance, String methodName, Class<T>[] paramClasses, Object[] params)
            throws  IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        Method method = clazzInstance.getClass().getMethod(methodName, paramClasses);
        return method.invoke(clazzInstance, params);
    }

    /**
     * 通过对象，访问其方法,无参数
     * @param clazzInstance 类实例对象
     * @param methodName 方法名
     */
    public static  Object invoke(Object clazzInstance, String methodName)
            throws NoSuchMethodException,  IllegalAccessException,  InvocationTargetException {
        Method m = clazzInstance.getClass().getMethod(methodName);
        return m.invoke(clazzInstance);
    }

    /**
     * 判断该类是不是包装类
     * @param clz 类
     * @return boolean 是否是包装类
     */
    @SuppressWarnings("all")
    public static boolean isWrapClass(Class clz) {
        try {
            return ((Class) clz.getField("TYPE").get(null)).isPrimitive();
        } catch (Exception e) {
            return false;
        }
    }
    /**
     * 判断该类是否是包装类或基本类,String,Date,BigDecimal
     * @param entityClass 类
     * @return boolean 是否是包装类或基本类
     */
    public static boolean isBaseClass(Class entityClass) {
        return (entityClass.isPrimitive()|| isWrapClass(entityClass)
                || entityClass.equals(BigDecimal.class) || entityClass.equals(String.class)
                || entityClass.equals(Date.class));
    }

    /**
     *  根据字段名寻找对应字段的get方法
     * @param clazz 类
     * @param fieldName 字段名
     * @return Method 类方法
     * @throws NoSuchMethodException 找不到方法异常
     */
    public static Method getGetMethod(Class<?> clazz,String fieldName) throws NoSuchMethodException{
        return clazz.getDeclaredMethod(GET_METHOD_HEAD+getMethodName(fieldName));
    }
    /**
     *  根据字段名寻找对应字段的set方法
     * @param clazz 类
     * @param fieldName 字段名
     * @param type 字段类型
     * @return Method 类方法
     * @throws NoSuchMethodException 找不到方法异常
     */
    public static Method getSetMethod(Class<?> clazz, String fieldName, Class<?> type) throws NoSuchMethodException{
        // 获取当前类声明的方法，包括private，protected，public的
        return clazz.getDeclaredMethod(SET_METHOD_HEAD+getMethodName(fieldName), type);
    }

    /**
     * 通过get方法获取字段值
     * @param object 对象
     * @param fieldName 字段名
     * @return Object 字段值
     * @throws NoSuchMethodException 没有找到方法
     * @throws IllegalAccessException 非法访问
     * @throws InvocationTargetException 异常
     */
    public static Object getFieldValueWithMethod(Object object, String fieldName)throws NoSuchMethodException,IllegalAccessException,InvocationTargetException{
        Method getter = getGetMethod(object.getClass(),fieldName);
        return getter.invoke(object);
    }
    /**
     * 通过set方法设置字段值
     * @param object 对象
     * @param fieldName 字段名
     * @param type 字段类型
     * @return Object 字段值
     * @throws NoSuchMethodException 没有找到方法
     * @throws IllegalAccessException 非法访问
     * @throws InvocationTargetException 异常
     */
    public static Object setFieldValueWithMethod(Object object, String fieldName, Object value, Class<?> type)throws NoSuchMethodException,IllegalAccessException,InvocationTargetException{
        Method setter = getSetMethod(object.getClass(),fieldName, type);
        return setter.invoke(object,value);
    }
    /**
     * 把属性名的第一个字母大写
     *
     * @param fieldName 属性名
     * @return java.lang.String
     * @author Enma.ai
     * 2018/6/22
     */
    private static String getMethodName(String fieldName) {
        byte[] items = fieldName.getBytes();
        items[0] = (byte) ((char) items[0] - 'a' + 'A');
        return new String(items);
    }

}
