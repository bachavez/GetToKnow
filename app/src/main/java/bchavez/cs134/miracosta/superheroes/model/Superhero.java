package bchavez.cs134.miracosta.superheroes.model;

import java.util.Objects;

public class Superhero {

    private String name;
    private String fileName;
    private String superPower;
    private String oneThing;

    public Superhero() {
        name = "none";
        fileName = "none";
        superPower = "none";
        oneThing = "none";
    }

    public Superhero(String name, String fileName, String superPower, String oneThing) {
        this.name = name;
        this.fileName = fileName;
        this.superPower = superPower;
        this.oneThing = oneThing;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getSuperPower() {
        return superPower;
    }

    public void setSuperPower(String superPower) {
        this.superPower = superPower;
    }

    public String getOneThing() {
        return oneThing;
    }

    public void setOneThing(String oneThing) {
        this.oneThing = oneThing;
    }

    @Override
    public String toString() {
        return "Superhero{" +
                "name='" + name + '\'' +
                ", fileName='" + fileName + '\'' +
                ", superPower='" + superPower + '\'' +
                ", oneThing='" + oneThing + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Superhero superhero = (Superhero) o;
        return Objects.equals(name, superhero.name) &&
                Objects.equals(fileName, superhero.fileName) &&
                Objects.equals(superPower, superhero.superPower) &&
                Objects.equals(oneThing, superhero.oneThing);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, fileName, superPower, oneThing);
    }
}