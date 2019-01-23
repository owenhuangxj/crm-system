package com.ss.entity;

/**
 * @Author: 王顺坤
 * @Date: 2019/1/21 10:24
 * @Description:
 */
public class Selector {
private Integer value;
private String label;
private Object data;

    public Integer getValue() {
        return value;
    }

    public String getLabel() {
        return label;
    }

    public Object getData() {
        return data;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Selector() {

    }

    public Selector(Integer value, String label, Object data) {
        this.value = value;
        this.label = label;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Selector{" +
                "value=" + value +
                ", label='" + label + '\'' +
                ", data=" + data +
                '}';
    }
}
