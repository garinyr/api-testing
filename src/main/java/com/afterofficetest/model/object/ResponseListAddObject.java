package com.afterofficetest.model.object;

public class ResponseListAddObject {
    private int id;
    private String name;
    private ResponseListAddObjectData data;

    public ResponseListAddObject() {
    }

    public ResponseListAddObject(int id, String name, ResponseListAddObjectData data) {
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

    public ResponseListAddObjectData getData() {
        return data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(ResponseListAddObjectData data) {
        this.data = data;
    }
}
