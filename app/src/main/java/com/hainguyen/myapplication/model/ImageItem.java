package com.hainguyen.myapplication.model;

import androidx.annotation.NonNull;

public class ImageItem implements Comparable, Cloneable {
    public static String key = "isSelected";

    public String name;
    public String url;
    public boolean isSelected;

    public ImageItem(String name, String url) {
        this.name = name;
        this.url = url;
        isSelected = false;
    }

    @Override
    public int compareTo(Object o) {
        ImageItem compare = (ImageItem) o;
        if (compare.name.equals(this.name) && compare.url.equals(this.url) && compare.isSelected == this.isSelected) {
            return 0;
        }
        return 1;
    }

    @NonNull
    @Override
    public ImageItem clone() throws CloneNotSupportedException {
        ImageItem clone;
        try {
            clone = (ImageItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e); //should not happen
        }
        return clone;
    }
}
