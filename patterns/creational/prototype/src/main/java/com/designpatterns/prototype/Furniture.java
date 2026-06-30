package com.designpatterns.prototype;

public class Furniture implements Prototype<Furniture> {
    private String sofa;

    public Furniture(String sofa) {
        this.sofa = sofa;
    }

    public Furniture(Furniture target) {
        this.sofa = target.sofa;
    }

    public void setSofa(String sofa) {
        this.sofa = sofa;
    }

    @Override
    public Furniture clone() {
        return new Furniture(this);
    }

    @Override
    public String toString() {
        return "Furniture{" +
                "sofa='" + sofa + '\'' +
                '}';
    }
}
