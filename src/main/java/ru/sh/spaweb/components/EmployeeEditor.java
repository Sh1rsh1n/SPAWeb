package ru.sh.spaweb.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sh.spaweb.entity.Employee;
import ru.sh.spaweb.repo.Repository;


@SpringComponent
@UIScope
public class EmployeeEditor extends VerticalLayout implements KeyNotifier {
    private final Repository<Employee> repository;

    private Employee employee;

    TextField name = new TextField("Name");
    TextField surname = new TextField("Surname");
    TextField department = new TextField("Department");

    Button save = new Button("Save", VaadinIcon.CHECK.create());
    Button cancel = new Button("Cancel");
    Button delete = new Button("Delete", VaadinIcon.TRASH.create());
    HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

    Binder<Employee> binder = new Binder<>(Employee.class);

    private ChangeHandler changeHandler;

    public interface ChangeHandler {
        void onChanges();
    }

    @Autowired
    public EmployeeEditor(Repository<Employee> repository) {
        this.repository = repository;

        add(name, surname, department, actions);

        binder.bindInstanceFields(this);

        setSpacing(true);

        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);

        addKeyPressListener(Key.ENTER, e -> save());

        save.addClickListener(e -> save());
        delete.addClickListener(e -> delete());
        cancel.addClickListener(e -> editEmployee(employee));
        setVisible(false);
    }

    public void editEmployee(Employee newEmp) {
        if (newEmp == null) {
            setVisible(false);
            return;
        }

        if (newEmp.getId() != null) {
            this.employee = repository.getById(newEmp.getId()).orElse(newEmp);
        } else {
            this.employee = newEmp;
        }
        cancel.setVisible(newEmp.getId() != null);

        binder.setBean(employee);

        setVisible(true);

        name.focus();
    }

    public void setChangeHandler(ChangeHandler h) {
        changeHandler = h;
    }

    public void delete() {
        repository.remove(employee);
        changeHandler.onChanges();
    }

    public void save() {
        if (repository.getAll().stream().noneMatch(emp -> emp.getId().equals(employee.getId()))) {
            repository.add(employee);
        } else {
            repository.save(employee);
        }
        changeHandler.onChanges();
    }


}
