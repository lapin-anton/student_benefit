package ru.java_project.student_benefit;

import ru.java_project.student_benefit.dao.DictionaryDAOImpl;
import ru.java_project.student_benefit.domain.PassportOffice;
import ru.java_project.student_benefit.domain.RegisterOffice;
import ru.java_project.student_benefit.domain.address.Street;

import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        DictionaryDAOImpl dao = new DictionaryDAOImpl();
        List<Street> streetList = dao.findStreets("fir");
        for (Street s: streetList) {
            System.out.println(s.getStreetCode() + " : " + s.getStreetName());
        }

        List<PassportOffice> offices = dao.findPassportOffices("010010000000");
        for (PassportOffice po: offices) {
            System.out.println(po);
        }

        List<RegisterOffice> r_offices = dao.findRegisterOffices("010010000000");
        for (RegisterOffice po: r_offices) {
            System.out.println(po);
        }

    }
}
