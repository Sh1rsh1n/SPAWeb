package ru.sh.spaweb.repo;


import ru.sh.spaweb.entity.Employee;

import java.util.List;
import java.util.Optional;

public interface Repository<T> {

    boolean add(T item);

    boolean remove(T item);

    void save(T item);

    Optional<Employee> getById(int id);

    List<T> getAll();

    List<Employee> getByName(String name);
}
