package model.JSON_objects;

public class Profile {

    private String name;

    private String about;

    public Profile(String name, String about) {
        this.name = name;
        this.about = about;
    }

    public Profile() {
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}

