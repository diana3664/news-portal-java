package models;

public class Users {

    private int id;
    private  String name;
    private String position;
    private String role;


    public Users(String name, String position, String role) {
        this.name = name;
        this.position = position;
        this.role = role;

    }

//    getters

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPosition() {
        return position;
    }

    public String getRole() {
        return role;
    }

//    setters

    public void setId(int id) {
        this.id = id;
    }

//    overide


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Users)) return false;

        Users users = (Users) o;

        if (getId() != users.getId()) return false;
        if (!getName().equals(users.getName())) return false;
        if (!getPosition().equals(users.getPosition())) return false;
        return getRole().equals(users.getRole());
    }

    @Override
    public int hashCode() {
        int result = getId();
        result = 31 * result + getName().hashCode();
        result = 31 * result + getPosition().hashCode();
        result = 31 * result + getRole().hashCode();
        return result;
    }
}
