package ru.java_project.student_benefit.domain.register;

import ru.java_project.student_benefit.domain.person.Person;
import ru.java_project.student_benefit.util.LocalDateAdapter;

import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

public class CityRegisterRequest {
    private String surName;
    private String givenName;
    private String patronymic;

    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate dateOfBirth;

    private Long districtCode;
    private Long streetCode;
    private String building;
    private String extension;
    private String apartment;

    public CityRegisterRequest() {
    }

    public CityRegisterRequest(Person person) {
        this.surName = person.getSurName();
        this.givenName = person.getGivenName();
        this.patronymic = person.getPatronymic();
        this.dateOfBirth = person.getDateOfBirth();
        this.districtCode = 1L;
        this.streetCode = person.getAddress().getStreet().getStreetCode();
        this.building = person.getAddress().getBuilding();
        this.extension = person.getAddress().getExtension();
        this.apartment = person.getAddress().getApartment();
    }

    public String getSurName() {
        return surName;
    }

    public String getGivenName() {
        return givenName;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Long getDistrictCode() {
        return districtCode;
    }

    public Long getStreetCode() {
        return streetCode;
    }

    public String getBuilding() {
        return building;
    }

    public String getExtension() {
        return extension;
    }

    public String getApartment() {
        return apartment;
    }

    public void setSurName(String surName) {
        this.surName = surName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setDistrictCode(Long districtCode) {
        this.districtCode = districtCode;
    }

    public void setStreetCode(Long streetCode) {
        this.streetCode = streetCode;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }

    public void setApartment(String apartment) {
        this.apartment = apartment;
    }

    @Override
    public String toString() {
        return "CityRegisterRequest{" +
                "surName='" + surName + '\'' +
                ", givenName='" + givenName + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", districtCode=" + districtCode +
                ", streetCode=" + streetCode +
                ", building='" + building + '\'' +
                ", extension='" + extension + '\'' +
                ", apartment='" + apartment + '\'' +
                '}';
    }
}
