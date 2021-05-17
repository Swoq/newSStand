package com.swoqe.newSStand.model.entity;

public class Period {
    private int id;
    private String name;
    private String description;

    public Period(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Period(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Period(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Period)) return false;

        Period period = (Period) o;

        if (id != period.id) return false;
        if (!name.equals(period.name)) return false;
        return description.equals(period.description);
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + name.hashCode();
        result = 31 * result + description.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Period{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
