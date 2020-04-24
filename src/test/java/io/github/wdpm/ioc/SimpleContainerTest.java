package io.github.wdpm.ioc;

import io.github.wdpm.ioc.model.Location;
import io.github.wdpm.ioc.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author evan
 * @date 2020/4/23
 */
class SimpleContainerTest {

    private SimpleContainer sc;

    @BeforeEach
    void setUp() {
        sc = new SimpleContainer();
    }

    @AfterEach
    void tearDown() {
        sc = null;
    }

    @Test
    void getBean() {
        Object bean = sc.registerBean(User.class);
        assertNotNull(sc.getBean(User.class));
    }

    @Test
    void getBeanByName() {
        Object bean = sc.registerBean(User.class);
        User   user = sc.getBeanByName(User.class.getName());
        assertNotNull(user);
    }

    @Test
    void registerBean1() {
        Object bean = sc.registerBean(User.class);
        assertNotNull(bean);
    }

    @Test
    void registerBean2() {
        Object bean = sc.registerBean("user", User.class);
        assertNotNull(bean);
    }

    @Test
    void remove() {
        Object userBean = sc.registerBean(User.class);
        sc.remove(User.class);
        assertNull(sc.getBean(User.class));

        Object locationBean = sc.registerBean("location",Location.class);
        sc.removeByName("location");
        assertNull(sc.getBeanByName("location"));
    }

    @Test
    void removeByName() {
        Object      userBean     = sc.registerBean(User.class);
        Object      locationBean = sc.registerBean(Location.class);
        Set<String> beanNames    = sc.getBeanNames();
        assertTrue(beanNames.contains(userBean.getClass().getName()));
        assertTrue(beanNames.contains(locationBean.getClass().getName()));
    }

    @Test
    void getBeanNames() {
        Object      userBean     = sc.registerBean(User.class);
        Object      locationBean = sc.registerBean(Location.class);
        Set<String> beanNames    = sc.getBeanNames();
        assertTrue(beanNames.contains(userBean.getClass().getName()));
        assertTrue(beanNames.contains(locationBean.getClass().getName()));
    }

    @Test
    void initWired() {
        //todo
    }

    @Test
    void inject() {
        //todo
    }

    @Test
    void getBeanSize() {
        Object o = sc.registerBean(User.class);
        assertEquals(1, sc.getBeanSize());
        Object o1 = sc.registerBean(User.class);
        assertEquals(1, sc.getBeanSize());
    }

    @Test
    void singleton1() {
        Object o1 = sc.registerBean(User.class);
        assertNotNull(o1);
        Object o2 = sc.registerBean(User.class);
        assertNotNull(o2);
        assertSame(o1, o2);
    }

    @Test
    void singleton2() {
        Object o1 = sc.registerBean("user", User.class);
        assertNotNull(o1);
        Object o2 = sc.registerBean("user", User.class);
        assertNotNull(o2);
        assertSame(o1, o2);
    }
}