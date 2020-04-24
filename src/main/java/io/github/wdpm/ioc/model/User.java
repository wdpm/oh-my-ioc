package io.github.wdpm.ioc.model;

/**
 * @author evan
 * @date 2020/4/23
 */
public class User {
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static String sayOK() {
        return "OK";
    }

    public void printName() {
        System.out.println(this.name);
    }
}
