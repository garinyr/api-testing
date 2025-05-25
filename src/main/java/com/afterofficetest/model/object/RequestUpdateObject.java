package com.afterofficetest.model.object;

public class RequestUpdateObject {
    private String name;
    private RequestUpdateObjectData data;

    public RequestUpdateObject() {
    }

    public RequestUpdateObject(String name, RequestUpdateObjectData data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public RequestUpdateObjectData getData() {
        return data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(RequestUpdateObjectData data) {
        this.data = data;
    }
}
