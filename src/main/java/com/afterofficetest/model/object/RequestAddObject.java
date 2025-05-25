package com.afterofficetest.model.object;

public class RequestAddObject {
    private String name;
    private RequestAddObjectData data;

    public RequestAddObject() {
    }

    public RequestAddObject(String name, RequestAddObjectData data) {
        this.name = name;
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public RequestAddObjectData getData() {
        return data;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setData(RequestAddObjectData data) {
        this.data = data;
    }
}
