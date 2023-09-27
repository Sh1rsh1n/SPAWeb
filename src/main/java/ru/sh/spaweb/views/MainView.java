package ru.sh.spaweb.views;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import org.springframework.beans.factory.annotation.Autowired;
import ru.sh.spaweb.components.EmployeeEditor;
import ru.sh.spaweb.entity.Employee;
import ru.sh.spaweb.repo.Repository;

@Route
public class MainView extends VerticalLayout {

    private final Repository<Employee> repository;

    private final Grid<Employee> grid;

    private final TextField filter = new TextField("", "enter name to filter");

    private final Button addNewBtn = new Button("New employee", VaadinIcon.PLUS.create());

    private final HorizontalLayout layout = new HorizontalLayout(filter, addNewBtn);

    private final EmployeeEditor editor;

    @Autowired
    public MainView(Repository<Employee> repository, EmployeeEditor editor) {
        this.repository = repository;
        this.editor = editor;
        this.grid = new Grid<>(Employee.class);
        add(layout, grid, editor);

        filter.setValueChangeMode(ValueChangeMode.LAZY);
        filter.addValueChangeListener(e -> showEmployees(e.getValue()));

        grid.asSingleSelect().addValueChangeListener(e -> {
            editor.editEmployee(e.getValue());
        });

        addNewBtn.addClickListener(e -> editor.editEmployee(new Employee()));

        editor.setChangeHandler(() -> {
            editor.setVisible(false);
            showEmployees(filter.getValue());
        });

        showEmployees("");
    }

    private void showEmployees(String name) {
        if (name.isEmpty()) {
            grid.setItems(repository.getAll());
        } else {
            grid.setItems(repository.getByName(name));
        }
    }

}
