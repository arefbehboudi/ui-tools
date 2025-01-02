package io.github.arefbehboudi.model;

import java.io.Serializable;
import java.util.Objects;

public class Alert implements Serializable {

    String name;

    public Alert(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alert alert = (Alert) o;
        return Objects.equals(name, alert.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
