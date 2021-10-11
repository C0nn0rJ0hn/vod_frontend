package com.myapp.vod.views;

import com.myapp.vod.backend.domain.Gender;
import com.myapp.vod.service.RegisterService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "register")
@PageTitle("Register")
public class RegisterView extends Composite {

        private final RegisterService registerService;

        public RegisterView(RegisterService registerService) {
        this.registerService = registerService;
        }

        @Override
        protected Component initContent() {

                TextField name = new TextField("First name");
                TextField lastname = new TextField("Last name");
                DatePicker birthDate = new DatePicker("Birth date");
                ComboBox<Gender> comboBox = new ComboBox<>("Gender");
                comboBox.setItems(Gender.MALE, Gender.FEMALE);
                comboBox.setItemLabelGenerator(Gender::name);
                TextField email = new TextField("Email address");
                PasswordField password = new PasswordField("Password");
                TextField country = new TextField("Country");
                TextField language = new TextField("Language");
                Button send = new Button("Register" ,event -> {
                registerService.register(
                    name.getValue(),
                    lastname.getValue(),
                    birthDate.getValue().toString(),
                    comboBox.getValue(),
                    email.getValue(),
                    password.getValue(),
                    country.getValue(),
                    language.getValue());
                });

                return new VerticalLayout(
                new H2("Register"),
                new HorizontalLayout(name, lastname),
                new HorizontalLayout(birthDate, comboBox),
                new HorizontalLayout(email, password),
                new HorizontalLayout(country, language),
                send
                );

        }
}
