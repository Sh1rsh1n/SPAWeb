package ru.sh.spaweb.repo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sh.spaweb.entity.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class EmployeeRepository implements Repository<Employee> {

    private final List<Employee> employeeList;

    @Autowired
    public EmployeeRepository() {
        employeeList = new ArrayList<>();
        employeeList.add(new Employee("Alex", "Ivanov", "IT"));
        employeeList.add(new Employee("Pavel", "Petrov", "HR"));
        employeeList.add(new Employee("Dima", "Smirnov", "Maintenance"));
    }


    @Override
    public boolean add(Employee item) {
        return employeeList.add(item);
    }

    @Override
    public boolean remove(Employee item) {
        return employeeList.remove(item);
    }

    @Override
    public void save(Employee item) {

        employeeList.forEach(employee -> {
            if (employee.getId().equals(item.getId())) {
                employee.setName(item.getName());
                employee.setSurname(item.getSurname());
                employee.setDepartment(item.getDepartment());
            }
        });
    }

    @Override
    public Optional<Employee> getById(int id) {
        return employeeList.stream().filter(employee -> employee.getId() == id).findFirst();
    }

    @Override
    public List<Employee> getAll() {
        return employeeList;
    }

    @Override
    public List<Employee> getByName(String nameOrSurname) {
        return employeeList.stream().filter(employee -> employee.getName().equalsIgnoreCase(nameOrSurname) || employee.getSurname().equalsIgnoreCase(nameOrSurname)).toList();
    }
}
