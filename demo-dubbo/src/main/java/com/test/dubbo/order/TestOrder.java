package com.test.dubbo.order;

import java.io.Serializable;

/**
 * @Vesrion 1.0
 * @Author heyinbo
 * @Date 2016/2/3
 * @Description
 */
public class TestOrder implements Serializable {

    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
