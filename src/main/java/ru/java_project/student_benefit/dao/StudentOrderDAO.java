package ru.java_project.student_benefit.dao;

import ru.java_project.student_benefit.domain.StudentOrder;
import ru.java_project.student_benefit.exception.DaoException;

import java.sql.SQLException;

public interface StudentOrderDAO {
    Long saveStudentOrder(StudentOrder order) throws DaoException;
}
