package com.ding.common.utils;

import com.ding.common.BusinessException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 不同对象之间相同属性的复制.
 *
 * @author dingzhencong
 */
public final class BeanUtils {

    private static final ConcurrentHashMap<Class<?>, List<Method>> CACHE_CLASS_GET_METHOD = new ConcurrentHashMap<>();
    private static final ConcurrentHashMap<Class<?>, List<Method>> CACHE_CLASS_SET_METHOD = new ConcurrentHashMap<>();

    private static final String PROXY_GET = "get";
    private static final String PROXY_SET = "set";
    private static final String PROXY_IS = "is";

    private static Logger logger = LoggerFactory.getLogger(BeanUtils.class);

    private BeanUtils() {
    }

    /**
     * 将src java bean的属性拷贝到 tar java bean的属性中，默认不拷贝src对象中的空对象的值.
     *
     * @param src 源对象
     * @param tar 目标对象
     */
    public static void copyProperties(Object src, Object tar) {
        copyProperties(src, tar, true);
    }

    /**
     * @param src      源对象
     * @param tarClass 目标类的Class对象
     * @param <T>      返回对象泛型
     * @return 返回对象
     */
    public static <T> T copyProperties(Object src, Class<T> tarClass) {
        T object = null;
        try {
            object = tarClass.newInstance();
            copyProperties(src, object);
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return object;
    }

    /**
     * 将一个类型List转换为另一个类型List
     *
     * @param srcList  源对象List
     * @param tarClass 目标类Class对象
     * @param <T>      泛型
     * @return 目标类List
     */
    public static <T> List<T> copyListProperties(List srcList, Class<T> tarClass) {
        if (CollectionUtils.isEmpty(srcList)) {
            return Collections.emptyList();
        }
        List<T> list = new ArrayList<>(srcList.size());
        List<Method> getterMethods = getGetMethod(srcList.get(0).getClass());
        List<Method> setterMethods = getSetMethod(tarClass);
        srcList.forEach(src -> list.add(copyProperties(getterMethods, setterMethods, src, tarClass, true)));
        return list;
    }

    /**
     * 将src java bean的属性拷贝到 tar java bean的属性中
     *
     * @param src       源对象
     * @param tarClass  目标class对象
     * @param skipEmpty 如果src中属性的值时null,是否跳过这个拷贝， true:跳过 false:不跳过
     */
    private static <T> T copyProperties(List<Method> getterMethods, List<Method> setterMethods, Object src, Class tarClass, boolean skipEmpty) {
        T object = null;
        try {
            object = (T) tarClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            logger.error(e.getMessage());
        }
        return xcopyProperties(getterMethods, setterMethods, src, object, skipEmpty);
    }

    /**
     * 将src java bean的属性拷贝到 tar java bean的属性中
     *
     * @param src       源对象
     * @param tar       目标对象
     * @param skipEmpty 如果src中属性的值时null,是否跳过这个拷贝， true:跳过 false:不跳过
     */
    private static <T> T copyProperties(Object src, T tar, boolean skipEmpty) {
        List<Method> getterMethods = getGetMethod(src.getClass());
        List<Method> setterMethods = getSetMethod(tar.getClass());
        return xcopyProperties(getterMethods, setterMethods, src, tar, skipEmpty);
    }

    private static <T> T xcopyProperties(List<Method> getterMethods, List<Method> setterMethods, Object src, T tar, boolean skipEmpty) {
        for (Method getMethod : getterMethods) {
            try {
                Object value = getMethod.invoke(src);
                if (skipEmpty && value == null) {
                    continue;
                }
                Method method = setterMethods.stream().filter(m -> Objects.equals(m.getName(), getInvokedSetterMethodName(getMethod.getName()))).findFirst().orElse(null);
                if (null != method && method.getParameterTypes()[0] == getMethod.getReturnType()) {
                    method.invoke(tar, value);

                }
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new BusinessException(e.getMessage() + "，" + getMethod.getName());
            }
        }


        return tar;
    }


    /**
     * 根据getter的方法名拿到setter的方法名
     *
     * @param getterMethodName getter的方法名
     * @return setter的方法名
     */
    private static String getInvokedSetterMethodName(String getterMethodName) {
        if (getterMethodName.startsWith(PROXY_GET)) {
            return PROXY_SET + getterMethodName.substring(3);
        } else {
            return PROXY_SET + getterMethodName.substring(2);
        }
    }

    /**
     * 获取clazz中的所有public getter 方法
     *
     * @param clazz 需要获取getter方法的类
     * @return public getter方法
     */
    private static List<Method> getGetMethod(Class<?> clazz) {
        Class<?> lockClazz = clazz;
        if (!CACHE_CLASS_GET_METHOD.containsKey(clazz)) {
            synchronized (lockClazz) {
                if (!CACHE_CLASS_GET_METHOD.containsKey(clazz)) {
                    CACHE_CLASS_GET_METHOD.put(clazz, Arrays.stream(getMethods(clazz)).filter(BeanUtils::isGetterMethod).filter(m -> !Objects.equals(m.getName(), "getClass")).collect(Collectors.toList()));
                }
            }
        }
        return CACHE_CLASS_GET_METHOD.get(clazz);
    }

    /**
     * 获取clazz中的所有public setter 方法
     *
     * @param clazz 需要获取setter方法的类
     * @return public setter方法
     */
    private static List<Method> getSetMethod(Class<?> clazz) {
        Class<?> lockClazz = clazz;
        if (!CACHE_CLASS_SET_METHOD.containsKey(clazz)) {
            synchronized (lockClazz) {
                if (!CACHE_CLASS_SET_METHOD.containsKey(clazz)) {
                    CACHE_CLASS_SET_METHOD.put(clazz, Arrays.stream(getMethods(clazz)).filter(BeanUtils::isSetterMethod).collect(Collectors.toList()));
                }
            }
        }
        return CACHE_CLASS_SET_METHOD.get(clazz);
    }

    /**
     * 获取一个类中中的所有的方法
     *
     * @param clazz
     * @return
     */
    private static Method[] getMethods(Class<?> clazz) {
        return clazz.getMethods();
    }

    /**
     * 判断一个方式是否是getter方法
     *
     * @param method 需要判断的方法
     * @return true:是getter方法 false：不是getter方法
     */
    private static boolean isGetterMethod(Method method) {
        if (method.getName().startsWith(PROXY_GET) && method.getParameterCount() == 0 && method.getReturnType() != Void.class) {
            return true;
        } else if (method.getName().startsWith(PROXY_IS) && method.getParameterCount() == 0 && (method.getReturnType().equals(boolean.class) || method.getReturnType().equals(Boolean.class))) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否是setter方法
     *
     * @param method 需要判断的方法
     * @return true是setter方法 false不是setter方法
     */
    private static boolean isSetterMethod(Method method) {
        boolean isSetter = false;
        if (method.getName().startsWith(PROXY_SET) && method.getParameterCount() == 1 && method.getReturnType() != Void.class) {
            isSetter = true;
        }
        return isSetter;
    }
}
