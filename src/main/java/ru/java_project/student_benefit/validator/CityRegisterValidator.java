package ru.java_project.student_benefit.validator;

import ru.java_project.student_benefit.domain.AnswerCityRegister;
import ru.java_project.student_benefit.domain.Child;
import ru.java_project.student_benefit.domain.CityRegisterCheckerResponse;
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
        try {
            CityRegisterCheckerResponse hans = personChecker.checkPerson(studentOrder.getHusband());
            CityRegisterCheckerResponse wans = personChecker.checkPerson(studentOrder.getWife());
            List<Child> children = studentOrder.getChildren();
            CityRegisterCheckerResponse cans;
            for (Child child: children) {
                cans = personChecker.checkPerson(child);
            }
        } catch (CityRegisterException e) {
            e.printStackTrace();
        }

        AnswerCityRegister ans = new AnswerCityRegister();
        ans.success = false;
        return ans;
    }
}
