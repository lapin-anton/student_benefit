package ru.java_project.student_benefit;

import ru.java_project.student_benefit.domain.Adult;
import ru.java_project.student_benefit.domain.Child;
import ru.java_project.student_benefit.domain.StudentOrder;

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
        Adult h = new Adult();
        h.setSurName("Ivanov");
        h.setGivenName("Ivan");
        h.setPatronymic("Ivanovich");
        h.setPassportNumber("123456");
        order.setHusband(new Adult());
        Adult w = new Adult();
        w.setSurName("Ivanova");
        w.setGivenName("Anna");
        w.setPatronymic("Sergeevna");
        order.setWife(w);
        Child c = new Child();
        c.setSurName("Ivanov");
        c.setGivenName("Nikolay");
        c.setPatronymic("Ivanovna");
        return order;
    }
}
