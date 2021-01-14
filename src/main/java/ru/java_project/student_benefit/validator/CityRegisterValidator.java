package ru.java_project.student_benefit.validator;

import ru.java_project.student_benefit.domain.person.Person;
import ru.java_project.student_benefit.domain.register.AnswerCityRegister;
import ru.java_project.student_benefit.domain.person.Child;
import ru.java_project.student_benefit.domain.register.AnswerCityRegisterItem;
import ru.java_project.student_benefit.domain.register.CityRegisterResponse;
import ru.java_project.student_benefit.domain.StudentOrder;
import ru.java_project.student_benefit.exception.CityRegisterException;
import ru.java_project.student_benefit.exception.TransportException;

public class CityRegisterValidator {

    public static final String IN_CODE = "NO_GRN";

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
        AnswerCityRegisterItem.CityStatus status = null;
        AnswerCityRegisterItem.CityError error = null;
        try {
            CityRegisterResponse tmp = personChecker.checkPerson(person);
            status = (tmp.isExisting()) ? AnswerCityRegisterItem.CityStatus.YES: AnswerCityRegisterItem.CityStatus.NO;

        } catch (CityRegisterException | TransportException e) {
            status = AnswerCityRegisterItem.CityStatus.ERROR;
            if (e instanceof CityRegisterException) {
                CityRegisterException t = (CityRegisterException) e;
                error = new AnswerCityRegisterItem.CityError(t.getCode(), t.getMessage());
            } else {
                TransportException t = (TransportException) e;
                error = new AnswerCityRegisterItem.CityError(IN_CODE, "transport error");
             }
        }
        item = new AnswerCityRegisterItem(status, person, error);
        return item;
    }
}
