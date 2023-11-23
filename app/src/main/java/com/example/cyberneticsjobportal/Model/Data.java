package com.example.cyberneticsjobportal.Model;

//DATA CLASS initializes and stores all our variables for use in all activities, classes and databases.

public class Data {

    String title;
    String position;
    String description;
    String skills;
    String area;
    String salary;

    String id;
    String date;

    public Data(){

    }

    public Data(String title, String position, String description, String skills, String area, String salary, String id, String date) {
        this.title = title;
        this.position = position;
        this.description = description;
        this.skills = skills;
        this.area = area;
        this.salary = salary;
        this.id = id;
        this.date = date;
    }

    //GET & SET Methods

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
