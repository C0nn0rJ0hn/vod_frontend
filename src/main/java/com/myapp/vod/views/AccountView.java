package com.myapp.vod.views;

import com.myapp.vod.backend.client.account.AccountClient;
import com.myapp.vod.backend.client.user.UserClient;
import com.myapp.vod.backend.domain.AccountDto;
import com.myapp.vod.backend.domain.Gender;
import com.myapp.vod.backend.domain.UserDto;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

import java.time.LocalDate;


@Route(value = "account", layout = MainView.class)
@PageTitle("Account details")
public class AccountView extends HorizontalLayout {
    TextField firstName = new TextField("First name");
    TextField lastName = new TextField("Last name");
    DatePicker birthDate = new DatePicker("Birth date");
    ComboBox<Gender> comboBox = new ComboBox<>("Gender");
    TextField email = new TextField("Email address");
    PasswordField password = new PasswordField("Password");
    TextField country = new TextField("Country");
    TextField language = new TextField("Language");

    Button save = new Button("Save");
    Button delete = new Button("Delete Account");
    Button cancel = new Button("Cancel");

    AccountClient accountClient;
    UserClient userClient;

    public AccountView(AccountClient accountClient, UserClient userClient) {
        this.accountClient = accountClient;
        this.userClient = userClient;

        comboBox.setItems(Gender.FEMALE, Gender.MALE);

        AccountDto currentAccount = VaadinSession.getCurrent().getAttribute(AccountDto.class);
        UserDto currentUser = userClient.getUser(currentAccount.getUserId());
        setAccountDetails(currentAccount, currentUser);

        cancel.addClickListener(event -> setAccountDetails(currentAccount, currentUser));
        delete.addClickListener(event -> {
            accountClient.deleteAccount(currentAccount.getId());
            UI.getCurrent().navigate(LoginView.class);
            Notification.show("Account has been deleted!");
            Notification.show("To access application You must register again");
        });
        save.addClickListener(event -> {
            AccountDto accountDto = currentAccount;
            accountDto.setEmail(email.getValue());
            accountDto.setPassword(password.getValue());
            accountDto.setCountry(country.getValue());
            accountDto.setLanguage(language.getValue());
            accountClient.updateAccount(accountDto);

            UserDto userDto = currentUser;
            userDto.setGender(comboBox.getValue());
            userDto.setBirthDate(birthDate.getValue().toString());
            userDto.setName(firstName.getValue());
            userDto.setLastname(lastName.getValue());
            userClient.updateUser(userDto);

            Notification.show("All changes have been saved!");
        });


        FormLayout formLayout = new FormLayout();
        formLayout.add(
                firstName,
                lastName,
                email,
                password,
                birthDate,
                comboBox,
                country,
                language,
                createButtonsLayout()
        );
        formLayout.setHeightFull();
        formLayout.setMaxWidth("500px");

        add(formLayout);
        setJustifyContentMode(JustifyContentMode.CENTER);
        setAlignItems(FlexComponent.Alignment.CENTER);
        setSizeFull();
    }

    private HorizontalLayout createButtonsLayout() {
        save.addThemeVariants(ButtonVariant.LUMO_SUCCESS);
        delete.addThemeVariants(ButtonVariant.LUMO_ERROR);
        cancel.addThemeVariants(ButtonVariant.LUMO_CONTRAST);

        save.addClickShortcut(Key.ENTER);

        HorizontalLayout layout = new HorizontalLayout(save, delete, cancel);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        return layout;
    }

    private void setAccountDetails(AccountDto accountDto, UserDto userDto) {
        firstName.setValue(userDto.getName());
        lastName.setValue(userDto.getLastname());
        birthDate.setValue(LocalDate.parse(userDto.getBirthDate()));
        comboBox.setValue(userDto.getGender());

        email.setValue(accountDto.getEmail());
        password.setValue(accountDto.getPassword());
        country.setValue(accountDto.getCountry());
        language.setValue(accountDto.getLanguage());
    }
}
