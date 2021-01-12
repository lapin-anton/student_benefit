package ru.java_project.student_benefit.domain;

public class StudentOrder {

    private String hFirstName;
    private String hLastName;
    private String wFirstName;
    private String wLastName;

    public String gethFirstName() {
        return hFirstName;
    }

    public void sethFirstName(String hFirstName) {
        this.hFirstName = hFirstName;
    }

    public String gethLastName() {
        return hLastName;
    }

    public void sethLastName(String hLastName) {
        this.hLastName = hLastName;
    }

    public String getwFirstName() {
        return wFirstName;
    }

    public void setwFirstName(String wFirstName) {
        this.wFirstName = wFirstName;
    }

    public String getwLastName() {
        return wLastName;
    }

    public void setwLastName(String wLastName) {
        this.wLastName = wLastName;
    }

    @Override
    public String toString() {
        return "StudentOrder{" +
                "hFirstName='" + hFirstName + '\'' +
                ", hLastName='" + hLastName + '\'' +
                ", wFirstName='" + wFirstName + '\'' +
                ", wLastName='" + wLastName + '\'' +
                '}';
    }
}
