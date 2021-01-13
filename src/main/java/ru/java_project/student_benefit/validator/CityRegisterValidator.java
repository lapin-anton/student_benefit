package ru.java_project.student_benefit.validator;

import ru.java_project.student_benefit.domain.AnswerCityRegister;
import ru.java_project.student_benefit.domain.StudentOrder;

public class CityRegisterValidator {

    public String hostName;
    public String login;
    public String password;

    private CityRegisterChecker personChecker;

    public CityRegisterValidator() {
        personChecker = new FakeCityRegisterChecker();
    }

    public AnswerCityRegister checkCityRegister(StudentOrder studentOrder) {
        personChecker.checkPerson(studentOrder.getHusband());
        personChecker.checkPerson(studentOrder.getWife());
        personChecker.checkPerson(studentOrder.getChild());

        AnswerCityRegister ans = new AnswerCityRegister();
        ans.success = false;
        return ans;
    }
}
