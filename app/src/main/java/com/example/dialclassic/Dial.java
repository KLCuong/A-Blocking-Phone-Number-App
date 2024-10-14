package com.example.dialclassic;

public class Dial {
    private int image1, image2;
    private String info;

    public Dial(int image1, int image2, String info) {
        this.image1 = image1;
        this.image2 = image2;
        this.info = info;
    }

    public int getImage1() {
        return image1;
    }

    public void setImage1(int image1) {
        this.image1 = image1;
    }

    public int getImage2() {
        return image2;
    }

    public void setImage2(int image2) {
        this.image2 = image2;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
