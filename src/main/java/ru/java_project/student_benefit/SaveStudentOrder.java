package ru.java_project.student_benefit;

import ru.java_project.student_benefit.domain.address.Address;
import ru.java_project.student_benefit.domain.person.Adult;
import ru.java_project.student_benefit.domain.person.Child;
import ru.java_project.student_benefit.domain.StudentOrder;

import java.time.LocalDate;

public class SaveStudentOrder {
    public static void main(String[] args) {
        long num = saveStudentOrder(buildStudentOrder(0));
        System.out.println(num);
    }

    public static long saveStudentOrder(StudentOrder order) {
        long num = -1L;
        System.out.println("SaveStudentOrder is running");
        System.out.println("Saving order...");
        return num;
    }

    static StudentOrder buildStudentOrder(long id) {
        StudentOrder order = new StudentOrder();
        order.setStudentOrderId(id);
        order.setMarriageCertificateId("" + (123456000 + id));
        order.setMarriageDate(LocalDate.of(2016, 7, 4));
        order.setMarriageOffice("Отдел ЗАГС");

        Address address = new Address("195000", "Заневский пр.", "12", "", "142");

        //Муж
        Adult husband = new Adult("Петров", "Виктор", "Сергеевич", LocalDate.of(1997, 4, 7));
        husband.setPassportSer("" + (1000 + id));
        husband.setPassportNumber("" + (100000 + id));
        husband.setIssueDate(LocalDate.of(2017, 9, 15));
        husband.setIssueDepartment("Отдел полиции №" + id);
        husband.setStudentId("" + (10000 + id));
        husband.setAddress(address);

        //Жена
        Adult wife = new Adult("Петрова", "Ирина", "Николаевна", LocalDate.of(1998, 2, 28));
        wife.setPassportSer("" + (2000 + id));
        wife.setPassportNumber("" + (200000 + id));
        wife.setIssueDate(LocalDate.of(2018, 3, 5));
        wife.setIssueDepartment("Отдел полиции №" + id);
        wife.setStudentId("" + (20000 + id));
        wife.setAddress(address);

        //Ребенок 1
        Child child_1 = new Child("Петрова", "Анна", "Викторовна", LocalDate.of(2018, 4, 7));
        child_1.setCertificateNumber("" + (300000 + id));
        child_1.setIssueDate(LocalDate.of(2018, 4, 20));
        child_1.setIssueDepartment("Отдел ЗАГС №" + id);
        child_1.setAddress(address);

        //Ребенок 2
        Child child_2 = new Child("Петров", "Сергей", "Викторович", LocalDate.of(2019, 9, 8));
        child_2.setCertificateNumber("" + (300000 + id));
        child_2.setIssueDate(LocalDate.of(2019, 9, 20));
        child_2.setIssueDepartment("Отдел ЗАГС №" + id);
        child_2.setAddress(address);

        order.setHusband(husband);
        order.setWife(wife);
        order.addChild(child_1);
        order.addChild(child_2);
        return order;
    }
}
