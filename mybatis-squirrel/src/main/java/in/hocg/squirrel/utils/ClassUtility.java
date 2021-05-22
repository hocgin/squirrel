package in.hocg.squirrel.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import in.hocg.squirrel.exception.SquirrelException;
import lombok.RequiredArgsConstructor;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by hocgin on 2019/7/14.
 * email: hocgin@gmail.com
 *
 * @author hocgin
 */
@RequiredArgsConstructor
public class ClassUtility {
    /**
     * 缓存已分析的类
     */
    private static Map<Class, ClassUtility> CACHED = Maps.newHashMap();
    private final Class<?> clazz;

    public static ClassUtility from(Class<?> clazz) {
        return CACHED.computeIfAbsent(clazz, ClassUtility::new);
    }

    /**
     * 获取所有函数
     *
     * @return r
     */
    public ArrayList<Method> getAllMethod() {
        ArrayList<Method> result = Lists.newArrayList();
        result.addAll(Arrays.asList(clazz.getDeclaredMethods()));
        Class<?> superclass = clazz.getSuperclass();
        if (Object.class.equals(superclass)) {
            return result;
        }
        result.addAll(ClassUtility.from(superclass).getAllMethod());

        return result;
    }

    /**
     * 获取所有字段
     *
     * @return r
     */
    public List<Field> getAllField() {
        ArrayList<Field> result = Lists.newArrayList();
        result.addAll(Arrays.asList(clazz.getDeclaredFields()));

        Class<?> superclass = clazz.getSuperclass();
        if (Object.class.equals(superclass)) {
            return result;
        }
        result.addAll(ClassUtility.from(superclass).getAllField());
        return result;
    }

    /**
     * 获取所有字段，排除 @Transient
     *
     * @return r
     */
    public List<Field> getAllFieldExcludeTransient() {
        return getAllField().stream()
                .filter(field -> !Modifier.isTransient(field.getModifiers()))
                .collect(Collectors.toList());
    }

    /**
     * 查找字段
     *
     * @param fieldName
     * @return r
     */
    public Field getField(String fieldName) {
        Field field = null;
        try {
            field = clazz.getDeclaredField(fieldName);
        } catch (NoSuchFieldException e) {
            Class<?> superclass = clazz.getSuperclass();
            if (!Object.class.equals(superclass)) {
                return ClassUtility.from(superclass).getField(fieldName);
            }
        }
        if (Objects.isNull(field)) {
            throw new IllegalArgumentException(String.format("在 %s 中未找到 %s 字段", clazz.getSimpleName(), fieldName));
        }
        return field;
    }

    /**
     * 通过函数名称获取 Class 对应的函数
     *
     * @param methodName
     * @return r
     */
    public Method getMethod(String methodName) {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }

        throw SquirrelException.wrap("{class} 未找到函数名为 {methodName} 的函数", clazz, methodName);
    }

    /**
     * 检查是否是基础类型
     * Integer => true
     * Long => true
     *
     * @param clazz
     * @return r
     */
    public static boolean isPrimitive(Class<?> clazz) {
        try {
            return ((Class<?>) clazz.getField("TYPE").get(null)).isPrimitive();
        } catch (IllegalAccessException | NoSuchFieldException e) {
            return false;
        }
    }

}
