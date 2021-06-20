package models;

public class Departments {


    private String name;
    private String description;
    private int id;
    private int size;


    public Departments(String name, String description) {
        this.name = name;
        this.description = description;
        this.size=0;
    }
//    getters


    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }

    public int getSize() {
        return size;
    }

//    overide

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Departments)) return false;

        Departments that = (Departments) o;

        if (getId() != that.getId()) return false;
        if (getSize() != that.getSize()) return false;
        if (!getName().equals(that.getName())) return false;
        return getDescription().equals(that.getDescription());
    }

    @Override
    public int hashCode() {
        int result = getName().hashCode();
        result = 31 * result + getDescription().hashCode();
        result = 31 * result + getId();
        result = 31 * result + getSize();
        return result;
    }

}
