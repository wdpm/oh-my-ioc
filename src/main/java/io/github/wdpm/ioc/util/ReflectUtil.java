package io.github.wdpm.ioc.util;

/**
 * @author evan
 * @date 2020/4/23
 */
public class ReflectUtil {
    private ReflectUtil() {
    }

    public static Object newInstance(String className) {
        Object obj = null;
        try {
            obj = Class.forName(className).newInstance();
        } catch (Exception e) {

        }
        return obj;
    }
}
