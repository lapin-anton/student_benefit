package ru.java_project.student_benefit;

import ru.java_project.student_benefit.dao.StudentDAOImpl;
import ru.java_project.student_benefit.domain.StudentOrder;
import ru.java_project.student_benefit.domain.children.AnswerChildren;
import ru.java_project.student_benefit.domain.marriage.AnswerMarriage;
import ru.java_project.student_benefit.domain.register.AnswerCityRegister;
import ru.java_project.student_benefit.domain.student.AnswerStudent;
import ru.java_project.student_benefit.mail.MailSender;
import ru.java_project.student_benefit.validator.ChildrenValidator;
import ru.java_project.student_benefit.validator.CityRegisterValidator;
import ru.java_project.student_benefit.validator.MarriageValidator;
import ru.java_project.student_benefit.validator.StudentValidator;

import java.util.List;

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
        try {
            List<StudentOrder> orders = readStudentOrders();
            for (StudentOrder so : orders) {
                checkOneOrder(so);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void checkOneOrder(StudentOrder order) {
        AnswerCityRegister answerCityRegister = checkCityRegister(order);
//        AnswerMarriage answerMarriage = checkMarriage(order);
//        AnswerChildren answerChildren = checkChildren(order);
//        AnswerStudent answerStudent = checkStudent(order);
//        sendMail(order);
    }

    private void sendMail(StudentOrder order) {
        mailSender.sendMail(order);
    }

    private List<StudentOrder> readStudentOrders() throws Exception {
        return new StudentDAOImpl().getStudentOrders();
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
