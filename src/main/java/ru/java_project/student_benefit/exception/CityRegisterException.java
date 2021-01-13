package ru.java_project.student_benefit.exception;

public class CityRegisterException extends Exception {
    public CityRegisterException() {
    }

    public CityRegisterException(String message) {
        super(message);
    }

    public CityRegisterException(String message, Throwable cause) {
        super(message, cause);
    }
}
