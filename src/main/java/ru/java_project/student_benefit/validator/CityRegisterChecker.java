package ru.java_project.student_benefit.validator;

import ru.java_project.student_benefit.domain.CityRegisterCheckerResponse;
import ru.java_project.student_benefit.domain.Person;

public interface CityRegisterChecker {
    public CityRegisterCheckerResponse checkPerson(Person person);
}
