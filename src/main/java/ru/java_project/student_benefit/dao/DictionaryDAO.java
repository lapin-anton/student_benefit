package ru.java_project.student_benefit.dao;

import ru.java_project.student_benefit.domain.address.Street;
import ru.java_project.student_benefit.exception.DaoException;

import java.util.List;

public interface DictionaryDAO {
    List<Street> findStreets(String input) throws DaoException;
}
