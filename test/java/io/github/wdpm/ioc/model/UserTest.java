package io.github.wdpm.ioc.model;

import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author evan
 * @date 2020/4/23
 */
class UserTest {

    @org.junit.jupiter.api.Test
    @DisplayName("sayOK() should be same.")
    void sayOK() {
        assertSame(User.sayOK(), "OK");
    }

}