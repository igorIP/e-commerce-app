package com.projects.shop.domain.models.service;

import java.util.UUID;

public abstract class BaseServiceModel {

    //private String id;
    private UUID id;


    public BaseServiceModel() {
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    //    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
}
