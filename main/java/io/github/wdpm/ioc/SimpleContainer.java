package io.github.wdpm.ioc;

import io.github.wdpm.ioc.annotation.Autowired;
import io.github.wdpm.ioc.util.ReflectUtil;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * scope为singleton的IOC容器
 *
 * @author evan
 * @date 2020/4/23
 */
public class SimpleContainer implements Container {
    // com.xxx.Person : @52x2xa
    private Map<String, Object> beans;

    // name or className  : com.xxx.Person
    private Map<String, String> beanKeys;

    public SimpleContainer() {
        // CHM保证线程安全
        beans = new ConcurrentHashMap<>();
        beanKeys = new ConcurrentHashMap<>();
    }

    @Override
    public <T> T getBean(Class<T> clazz) {
        String clazzName = clazz.getName();
        if (clazzName == null || "".equals(clazzName)) {
            return null;
        }
        return (T) beans.get(clazzName);
    }

    @Override
    public <T> T getBeanByName(String name) {
        String clazzName = beanKeys.get(name);
        if (clazzName == null || "".equals(clazzName)) {
            return null;
        }
        return (T) beans.get(clazzName);
    }

    /**
     * 注册bean。
     *
     * @param clazz
     * @return
     */
    @Override
    public Object registerBean(Class<?> clazz) {
        String clazzName = clazz.getName();
        Object bean;
        //ensure singleton
        if (!beanKeys.containsKey(clazzName)) {
            beanKeys.put(clazzName, clazzName);
            bean = ReflectUtil.newInstance(clazzName);
            beans.put(clazzName, bean);
        } else {
            bean = beans.get(clazzName);
        }
        return bean;
    }

    /**
     * 注册bean。
     *
     * @param name
     * @param clazz
     * @return
     */
    @Override
    public Object registerBean(String name, Class<?> clazz) {
        String clazzName = clazz.getName();
        Object bean;
        //ensure singleton
        if (!beanKeys.containsKey(name)) {
            beanKeys.put(name, clazzName);
            bean = ReflectUtil.newInstance(clazzName);
            beans.put(clazzName, bean);
        } else {
            String className = beanKeys.get(name);
            bean = beans.get(className);
        }
        return bean;
    }

    @Override
    public void remove(Class<?> clazz) {
        String className = clazz.getName();
        if (null != className && !className.equals("")) {
            beanKeys.remove(className);
            beans.remove(className);
        }
    }

    @Override
    public void removeByName(String name) {
        String className = beanKeys.get(name);
        if (null != className && !className.equals("")) {
            beanKeys.remove(name);
            beans.remove(className);
        }
    }

    @Override
    public Set<String> getBeanNames() {
        return beanKeys.keySet();
    }

    @Override
    public int getBeanSize() {
        return beanKeys.size();
    }

    /**
     * 将beans中的所有bean装配
     */
    @Override
    public void initWired() {
        for (Map.Entry<String, Object> entry : beans.entrySet()) {
            Object object = entry.getValue();
            inject(object);
        }
    }

    /**
     * fields inject
     *
     * @param object
     */
    public void inject(Object object) {
        try {
            // 获取一个类中定义的所有字段
            Field[] fields = object.getClass().getDeclaredFields();
            for (Field field : fields) {
                // fields with @Autowired
                Autowired autoWired = field.getAnnotation(Autowired.class);

                if (null != autoWired) {
                    Object autoWiredField = null;
                    String name           = autoWired.name();
                    if (!name.equals("")) {
                        String className = beanKeys.get(name);
                        if (null != className && !className.equals("")) {
                            autoWiredField = beans.get(className);
                        }
                        //因为以非空name形式注册的bean，不可能获取不到
                        if (null == autoWiredField) {
                            throw new RuntimeException("Unable to load bean with name: " + name);
                        }
                    } else {
                        if (autoWired.value() == Class.class) {
                            autoWiredField = recursiveAssembly(field.getType());
                        } else {
                            // autoWired.value()!=Class.class, means has set value
                            autoWiredField = this.getBean(autoWired.value());
                            if (null == autoWiredField) {
                                autoWiredField = recursiveAssembly(autoWired.value());
                            }
                        }
                    }

                    if (null == autoWiredField) {
                        throw new RuntimeException("Unable to load " + field.getType().getCanonicalName());
                    }

                    boolean accessible = field.isAccessible();
                    field.setAccessible(true);
                    // 将object中的这个field字段，设置值为autoWiredField的值
                    field.set(object, autoWiredField);
                    field.setAccessible(accessible);
                }
            }
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private Object recursiveAssembly(Class<?> clazz) {
        if (null != clazz) {
            return this.registerBean(clazz);
        }
        return null;
    }
}
