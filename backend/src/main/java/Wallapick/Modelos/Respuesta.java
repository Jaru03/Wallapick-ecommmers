package Wallapick.Modelos;

import java.util.List;

public class Respuesta<T> {
    private int codigo;
    private String status;
    private T data;
    private List<?> datas;

    public Respuesta(int codigo, String status, T data) {
        this.codigo = codigo;
        this.status = status;
        this.data = data;
    }

    public Respuesta(int codigo, T data) {
        this.codigo = codigo;
        this.data = data;
    }
    public Respuesta(int codigo,List<?> datas) {
        this.codigo = codigo;
        this.datas = datas;
    }


    public Respuesta() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
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