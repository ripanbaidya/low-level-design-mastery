package com.designpatterns.prototype;

public class Apartment implements Prototype<Apartment> {
    private String wallColor;
    private Furniture furniture;

    public Apartment(String wallColor, Furniture furniture) {
        this.wallColor = wallColor;
        this.furniture = furniture;
    }

    public Apartment(Apartment target) {
        if (target != null) {
            this.wallColor = target.wallColor;

            // Deep copy of furniture
            this.furniture = target.furniture.clone();
        }
    }

    public void setWallColor(String wallColor) {
        this.wallColor = wallColor;
    }

    public Furniture getFurniture() {
        return furniture;
    }

    @Override
    public Apartment clone() {
        return new Apartment(this);
    }

    public void display() {
        System.out.println(
                "Wall Color : " + wallColor + ", " + furniture
        );
    }
}
