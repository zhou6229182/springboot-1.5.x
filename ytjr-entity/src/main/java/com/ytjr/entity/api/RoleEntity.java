package com.ytjr.entity.api;

import java.io.Serializable;

public class RoleEntity implements Serializable {
    private static final long serialVersionUID = 1609346400596378001L;
    private Long id;
    private String name;

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
}
