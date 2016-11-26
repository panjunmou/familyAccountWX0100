package com.pjm.familyAccountWx.vo;

import java.util.List;

/**
 * Created by PanJM on 2016/11/22.
 */
public class PurposePicker {

    private Long id;

    private String name;

    private List<PurposePicker> sub;

    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<PurposePicker> getSub() {
        return sub;
    }

    public void setSub(List<PurposePicker> sub) {
        this.sub = sub;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
