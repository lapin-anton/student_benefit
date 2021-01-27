package ru.java_project.student_benefit.domain;

public enum StudentOrderStatus {
    START, CHECKED;

    public static StudentOrderStatus fromValue(int value) {
        for (StudentOrderStatus s: StudentOrderStatus.values()) {
            if(value == s.ordinal()) {
                return s;
            }
        }
        throw new RuntimeException("Unknown value:" + value);
    }
}
