package ru.java_project.student_benefit.validator;

import ru.java_project.student_benefit.domain.person.Person;
import ru.java_project.student_benefit.domain.register.CityRegisterResponse;
import ru.java_project.student_benefit.exception.CityRegisterException;

public interface CityRegisterChecker {
    public CityRegisterResponse checkPerson(Person person)
            throws CityRegisterException;
}
