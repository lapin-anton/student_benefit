package ru.java_project.student_benefit.validator;

import ru.java_project.student_benefit.domain.AnswerMarriage;
import ru.java_project.student_benefit.domain.StudentOrder;

public class MarriageValidator {
    public AnswerMarriage checkMarriage(StudentOrder studentOrder) {
        System.out.println("Marriage is checking");
        return new AnswerMarriage();
    }
}
