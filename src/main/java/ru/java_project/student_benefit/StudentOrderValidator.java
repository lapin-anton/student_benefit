package ru.java_project.student_benefit;

import ru.java_project.student_benefit.domain.*;
import ru.java_project.student_benefit.mail.MailSender;
import ru.java_project.student_benefit.validator.ChildrenValidator;
import ru.java_project.student_benefit.validator.CityRegisterValidator;
import ru.java_project.student_benefit.validator.MarriageValidator;
import ru.java_project.student_benefit.validator.StudentValidator;

public class StudentOrderValidator {

    private final CityRegisterValidator cityRegisterValidator;
    private final MarriageValidator marriageValidator;
    private final ChildrenValidator childrenValidator;
    private final StudentValidator studentValidator;
    private final MailSender mailSender;

    public StudentOrderValidator() {
        cityRegisterValidator = new CityRegisterValidator();
        marriageValidator = new MarriageValidator();
        childrenValidator = new ChildrenValidator();
        studentValidator = new StudentValidator();
        mailSender = new MailSender();
    }

    public static void main(String[] args) {
        StudentOrderValidator sov = new StudentOrderValidator();
        sov.checkAll();
    }

    public void checkAll() {
        while(true) {
            StudentOrder order = readStudentOrder();
            if (order == null) break;
            AnswerCityRegister answerCityRegister = checkCityRegister(order);
            if(!answerCityRegister.success) continue;
            AnswerMarriage answerMarriage = checkMarriage(order);
            AnswerChildren answerChildren = checkChildren(order);
            AnswerStudent answerStudent = checkStudent(order);
            sendMail(order);
        }
    }

    private void sendMail(StudentOrder order) {
        mailSender.sendMail(order);
    }

    private StudentOrder readStudentOrder() {
        return new StudentOrder();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        return cityRegisterValidator.checkCityRegister(studentOrder);
    }

    public AnswerStudent checkStudent(StudentOrder studentOrder) {
        return studentValidator.checkStudent(studentOrder);
    }

    public AnswerMarriage checkMarriage(StudentOrder studentOrder) {
        return marriageValidator.checkMarriage(studentOrder);
    }

    public AnswerChildren checkChildren(StudentOrder studentOrder) {
        return childrenValidator.checkChildren(studentOrder);
    }
}
