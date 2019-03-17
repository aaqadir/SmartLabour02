package com.example.smartlabour01;

public class Labour {
private String Name,Contact,Experience,Image,Type1;
public Labour(){}
Labour(String Name,String Contact,String Experience,String Image,String Type1){
    this.Name = Name;
    this.Contact = Contact;
    this.Experience = Experience;
    this.Image = Image;
    this.Type1 = Type1;
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


    public void setExperience(String experience) {
        Experience = experience;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getType1() {
        return Type1;
    }

    public void setType1(String type1) {
        Type1 = type1;
    }
}
