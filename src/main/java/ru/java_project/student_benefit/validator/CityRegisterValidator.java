package ru.java_project.student_benefit.validator;

import ru.java_project.student_benefit.domain.person.Person;
import ru.java_project.student_benefit.domain.register.AnswerCityRegister;
import ru.java_project.student_benefit.domain.person.Child;
import ru.java_project.student_benefit.domain.register.AnswerCityRegisterItem;
import ru.java_project.student_benefit.domain.register.CityRegisterResponse;
import ru.java_project.student_benefit.domain.StudentOrder;
import ru.java_project.student_benefit.exception.CityRegisterException;

import java.util.List;

public class CityRegisterValidator {

    public String hostName;
    public String login;
    public String password;

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        AnswerCityRegister ans = new AnswerCityRegister();
        ans.addItem(checkPerson(studentOrder.getHusband()));
        ans.addItem(checkPerson(studentOrder.getWife()));
        for (Child child: studentOrder.getChildren()) {
            ans.addItem(checkPerson(child));
        }
        return ans;
    }

    private AnswerCityRegisterItem checkPerson(Person person) {
        AnswerCityRegisterItem item;
        try {
            personChecker.checkPerson(person);
        } catch (CityRegisterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
