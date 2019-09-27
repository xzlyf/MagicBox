package com.xz.magicbox.entity;

public class News {
    private String img;
    private String url;
    private String hint;
    private String title;
    private String id;

    public News() {

    }

    public News(String img, String url, String hint, String title, String id) {
        this.img = img;
        this.url = url;
        this.hint = hint;
        this.title = title;
        this.id = id;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
