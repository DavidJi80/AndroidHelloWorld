package com.github.davidji80.helloworld.model;

public class Person {
    private int photo;
    private String name;
    private String intro;

    public Person(int photo,String name,String intro){
        this.photo=photo;
        this.name=name;
        this.intro=intro;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getPhoto() {
        return photo;
    }

    public void setPhoto(int photo) {
        this.photo = photo;
    }


}
