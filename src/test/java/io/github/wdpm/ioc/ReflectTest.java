package io.github.wdpm.ioc;

import io.github.wdpm.ioc.model.User;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author evan
 * @date 2020/4/24
 */
public class ReflectTest {
    public static void main(String[] args) {
        try {
            // AppClassLoader
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            Class<?>    clazz  = loader.loadClass("io.github.wdpm.ioc.model.User");

            // get non-args constructor
            Constructor<?> cons = clazz.getDeclaredConstructor((Class[]) null);
            User           user = (User) cons.newInstance();

            Method setName = clazz.getMethod("setName", String.class);
            setName.invoke(user, "abc");

            user.printName();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
