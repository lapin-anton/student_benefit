package ru.java_project.student_benefit.dao;

import ru.java_project.student_benefit.domain.StudentOrder;
import ru.java_project.student_benefit.exception.DaoException;

import java.util.List;

public interface StudentOrderDAO {
    Long saveStudentOrder(StudentOrder order) throws DaoException;
    List<StudentOrder> getStudentOrders() throws DaoException;
}
