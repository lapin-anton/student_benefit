package ru.java_project.student_benefit.validator;

import ru.java_project.student_benefit.domain.AnswerChildren;
import ru.java_project.student_benefit.domain.StudentOrder;

public class ChildrenValidator {
    public AnswerChildren checkChildren(StudentOrder studentOrder) {
        System.out.println("Children is checking");
        return new AnswerChildren();
    }
}
