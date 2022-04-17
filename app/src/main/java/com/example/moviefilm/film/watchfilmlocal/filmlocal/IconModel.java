package com.example.moviefilm.film.watchfilmlocal.filmlocal;

public class IconModel {
    private String id;
    private int imageView;
    private String iconTitle;

    public IconModel(String id, int imageView, String iconTitle) {
        this.id = id;
        this.imageView = imageView;
        this.iconTitle = iconTitle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getImageView() {
        return imageView;
    }

    public void setImageView(int imageView) {
        this.imageView = imageView;
    }

    public String getIconTitle() {
        return iconTitle;
    }

    public void setIconTitle(String iconTitle) {
        this.iconTitle = iconTitle;
    }
}
