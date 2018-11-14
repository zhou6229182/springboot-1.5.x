package com.ytjr.entity.api;

import java.io.Serializable;
import java.util.Date;

public class UserEntity implements Serializable {

    private static final long serialVersionUID = 5468536091791728251L;

    private Long id;
    private String name;
    private Date birthday;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    @Override
    public String toString() {
        return "name:" + this.name;
    }
}
