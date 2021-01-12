package ru.java_project.student_benefit;

import ru.java_project.student_benefit.domain.StudentOrder;

public class SaveStudentOrder {
    public static void main(String[] args) {
        StudentOrder order = new StudentOrder();
        order.hFirstName = "Ivan";
        order.hLastName = "Ivanov";
        order.wFirstName = "Anna";
        order.wLastName = "Ivanova";
        long num = saveStudentOrder(order);
        System.out.println(num);
    }

    public static long saveStudentOrder(StudentOrder order) {
        long num = -1L;
        System.out.println("SaveStudentOrder is running");
        System.out.println(String.format("Saving order: %s", order));
        return num;
    }
}
