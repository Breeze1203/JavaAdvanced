package org.example.message;

public class StudentMessage {
    private String id;

    private Object data;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "StudentMessage{" +
                "id='" + id + '\'' +
                ", data=" + data +
                '}';
    }
}
