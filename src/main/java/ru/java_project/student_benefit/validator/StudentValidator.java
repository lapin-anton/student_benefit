package ru.java_project.student_benefit.validator;

import ru.java_project.student_benefit.domain.student.AnswerStudent;
import ru.java_project.student_benefit.domain.StudentOrder;

public class StudentValidator {
    public AnswerStudent checkStudent(StudentOrder studentOrder) {
        System.out.println("Students is checking");
        return new AnswerStudent();
    }
}
