package com.example.smartlabour01;

public class HiredLabour {
    private String Name,Contact,Experience,Image,Location,Skills;
    public HiredLabour(){}
    HiredLabour(String Name,String Contact,String Experience,String Image,String Location,String Skills){
        this.Name = Name;
        this.Contact = Contact;
        this.Experience = Experience;
        this.Image = Image;
        this.Location = Location;
        this.Skills=Skills;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getExperience() {
        return Experience;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public String getSkills() {
        return Skills;
    }

    public void setSkills(String skills) {
        Skills = skills;
    }
}
