package com.company.genpro.service;

public interface JsonService {
    String NAME = "genpro_JsonService";
    String getJsonById(String uuid);
    void setJsonById(String uuid,String json);
}