package ru.java_project.student_benefit;

import ru.java_project.student_benefit.domain.StudentOrder;

public class SaveStudentOrder {
    public static void main(String[] args) {
        StudentOrder order = new StudentOrder();
        order.sethFirstName("Ivan");
        order.sethLastName("Ivanov");
        order.setwFirstName("Anna");
        order.setwLastName("Ivanova");
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
