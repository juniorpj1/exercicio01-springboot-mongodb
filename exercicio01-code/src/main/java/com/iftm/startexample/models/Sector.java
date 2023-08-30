package com.iftm.startexample.models;

import java.util.Objects;

public class Sector {

    private String name;
    private String floor;

    public Sector() {
    }

    public Sector(String name, String floor) {
        this.name = name;
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public String getFloor() {
        return floor;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sector sector = (Sector) o;
        return Objects.equals(name, sector.name) && Objects.equals(floor, sector.floor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, floor);
    }

    @Override
    public String toString() {
        return "Sector{" +
                "name='" + name + '\'' +
                ", floor='" + floor + '\'' +
                '}';
    }
}
