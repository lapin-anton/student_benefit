package ru.java_project.student_benefit.domain;

public class StudentOrder {

    public String hFirstName;
    public String hLastName;
    public String wFirstName;
    public String wLastName;

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
