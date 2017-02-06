package com.pjm.familyAccountWx.vo;

import java.util.List;

/**
 * Created by PanJM on 2016/11/22.
 */
public class PurposePicker implements Comparable {

    private Long id;

    private String name;

    private List<PurposePicker> sub;

    private String type;

    private Integer seq;

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public int compareTo(Object o) {
        PurposePicker purposePicker = (PurposePicker) o;
        Integer seq = purposePicker.getSeq();
        return this.seq.compareTo(seq);
    }
}
