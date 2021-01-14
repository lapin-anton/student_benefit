package ru.java_project.student_benefit.domain.register;

import ru.java_project.student_benefit.domain.person.Person;

public class AnswerCityRegisterItem {

    public enum CityStatus {
        YES, NO, ERROR
    }

    public static class CityError {
        private String code;
        private String message;

        public CityError(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }
    }

    private CityStatus status;
    private Person person;
    private CityError error;

    public AnswerCityRegisterItem(CityStatus status, Person person) {
        this.status = status;
        this.person = person;
    }

    public AnswerCityRegisterItem(CityStatus status, Person person, CityError error) {
        this.status = status;
        this.person = person;
        this.error = error;
    }

    public CityStatus getStatus() {
        return status;
    }

    public Person getPerson() {
        return person;
    }

    public CityError getError() {
        return error;
    }
}
