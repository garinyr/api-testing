package com.afterofficetest.model.object;

public class ResponseGetListObjectById {
    private int id;
    private String name;
    private ResponseGetListObjectByIdData data;

    public ResponseGetListObjectById() {
    }

    public ResponseGetListObjectById(int id, String name, ResponseGetListObjectByIdData data) {
        this.id = id;
        this.name = name;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public ResponseGetListObjectByIdData getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(ResponseGetListObjectByIdData data) {
        this.data = data;
    }
}