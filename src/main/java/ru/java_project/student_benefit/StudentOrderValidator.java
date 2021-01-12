package ru.java_project.student_benefit;

import ru.java_project.student_benefit.domain.*;
import ru.java_project.student_benefit.mail.MailSender;
import ru.java_project.student_benefit.validator.ChildrenValidator;
import ru.java_project.student_benefit.validator.CityRegisterValidator;
import ru.java_project.student_benefit.validator.MarriageValidator;
import ru.java_project.student_benefit.validator.StudentValidator;

public class StudentOrderValidator {
    public static void main(String[] args) {
        checkAll();
    }

    public static void checkAll() {
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

    private static void sendMail(StudentOrder order) {
        new MailSender().sendMail(order);
    }

    private static StudentOrder readStudentOrder() {
        return new StudentOrder();
    }

    public static AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        CityRegisterValidator crv = new CityRegisterValidator();
        crv.hostName = "hostName";
        crv.login = "Login";
        crv.password = "password";
        return crv.checkCityRegister(studentOrder);
    }

    public static AnswerStudent checkStudent(StudentOrder studentOrder) {
        StudentValidator sv = new StudentValidator();
        return sv.checkStudent(studentOrder);
    }

    public static AnswerMarriage checkMarriage(StudentOrder studentOrder) {
        MarriageValidator mv = new MarriageValidator();
        return mv.checkMarriage(studentOrder);
    }

    public static AnswerChildren checkChildren(StudentOrder studentOrder) {
        ChildrenValidator cv = new ChildrenValidator();
        return cv.checkChildren(studentOrder);
    }
}
