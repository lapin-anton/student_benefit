package ru.java_project.student_benefit;

import ru.java_project.student_benefit.dao.DirectoryDAO;
import ru.java_project.student_benefit.domain.address.Street;

import java.util.List;

public class Test {
    public static void main(String[] args) throws Exception {
        DirectoryDAO dao = new DirectoryDAO();
        List<Street> streetList = dao.findStreets("fir");
        for (Street s: streetList) {
            System.out.println(s.getStreetCode() + " : " + s.getStreetName());
        }
    }
}
