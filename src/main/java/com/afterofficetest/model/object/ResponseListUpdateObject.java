package com.afterofficetest.model.object;

public class ResponseListUpdateObject {
    private int id;
    private String name;
    private ResponseListUpdateObjectData data;

    public ResponseListUpdateObject() {
    }

    public ResponseListUpdateObject(int id, String name, ResponseListUpdateObjectData data) {
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

    public ResponseListUpdateObjectData getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(ResponseListUpdateObjectData data) {
        this.data = data;
    }
}
