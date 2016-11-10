package org.minigur.site.models;

public class Tag {
    private String tag;
    private Integer count;

    public Tag(String tag, Integer count) {
        this.tag = tag;
        this.count = count;
    }

    public Tag() {

    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
