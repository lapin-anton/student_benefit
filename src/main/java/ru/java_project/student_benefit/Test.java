package ru.java_project.student_benefit;

import ru.java_project.student_benefit.dao.DictionaryDAOImpl;
import ru.java_project.student_benefit.domain.address.Street;

import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        DictionaryDAOImpl dao = new DictionaryDAOImpl();
        List<Street> streetList = dao.findStreets("fir");
        for (Street s: streetList) {
            System.out.println(s.getStreetCode() + " : " + s.getStreetName());
        }
    }
}
