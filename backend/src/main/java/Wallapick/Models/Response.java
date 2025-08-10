package Wallapick.Models;

import java.util.List;

public class Response<T> {

    private int code;
    private String status;
    private T data;
    private List<?> datas;

    public Response() {
    }

    public Response(int code, String status, T data) {
        this.code = code;
        this.status = status;
        this.data = data;
    }

    public Response(int code, T data) {
        this.code = code;
        this.data = data;
    }
    public Response(int code, List<?> datas) {
        this.code = code;
        this.datas = datas;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<?> getDatas() {
        return datas;
    }

    public void setDatas(List<?> datas) {
        this.datas = datas;
    }
}