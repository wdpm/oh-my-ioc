package io.github.wdpm.ioc;

import java.util.Set;

/**
 * @author evan
 * @date 2020/4/23
 */
interface Container {

    /**
     * get bean by clazz
     *
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getBean(Class<T> clazz);

    /**
     * get bean by name
     *
     * @param name
     * @param <T>
     * @return
     */
    <T> T getBeanByName(String name);

    /**
     * register clazz as a bean
     *
     * @param clazz
     * @return
     */
    Object registerBean(Class<?> clazz);

    /**
     * register a named bean
     *
     * @param name
     * @param clazz
     * @return
     */
    Object registerBean(String name, Class<?> clazz);

    /**
     * remove bean by clazz
     *
     * @param clazz
     */
    void remove(Class<?> clazz);

    /**
     * remove a bean by name
     *
     * @param name
     */
    void removeByName(String name);

    /**
     * get all beans
     *
     * @return
     */
    Set<String> getBeanNames();

    /**
     * init
     */
    void initWired();

    int getBeanSize();
}
