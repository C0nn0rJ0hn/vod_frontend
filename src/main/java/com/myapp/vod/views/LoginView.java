package com.myapp.vod.views;

import com.myapp.vod.backend.domain.AccountDto;
import com.myapp.vod.exception.AuthenticationException;
import com.myapp.vod.service.AuthService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouterLink;
import com.vaadin.flow.server.VaadinSession;
import org.springframework.stereotype.Service;

@Route(value = "login")
@PageTitle("Login")
public class LoginView extends Div {

    public LoginView(AuthService authService) {
        /*setId("login-view");
        var email = new TextField("Email");
        var password = new PasswordField("Password");
        email.setPlaceholder("Enter email");
        password.setPlaceholder("Enter password");
        Button login = new Button("Login", event -> {
            try {
                authService.authenticate(email.getValue(), password.getValue());
                UI.getCurrent().navigate("home");
            }
            catch (AuthenticationException e) {
                Notification.show("Wrong credentials!");
            }

        });

        VerticalLayout layout = getContent();
        layout.add(
                new H1("Video On Demand Platform"),
                //new H2("Search over 10000 movies and tv shows!"),
                email,
                password,
                login,
                new RouterLink("Register", RegisterView.class)
        );
        layout.setSizeFull();
        layout.setAlignItems(FlexComponent.Alignment.CENTER);
        layout.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);*/
        LoginI18n i18n = LoginI18n.createDefault();

        LoginI18n.Form i18nForm = i18n.getForm();
        i18nForm.setTitle("Log in");
        i18nForm.setUsername("Email");
        i18nForm.setPassword("Password");
        i18nForm.setSubmit("Log in");
        i18nForm.setForgotPassword("Register");
        i18n.setForm(i18nForm);

        LoginI18n.ErrorMessage i18nError = i18n.getErrorMessage();
        i18nError.setTitle("Incorrect email or password");
        i18nError.setMessage("Check that you have entered the correct email and password and try again");
        i18n.setErrorMessage(i18nError);

        LoginOverlay loginOverlay = new LoginOverlay();
        loginOverlay.setI18n(i18n);
        loginOverlay.setTitle("Video On Demand App");
        loginOverlay.setDescription("Search over 10000 movies and tv shows!");
        loginOverlay.addForgotPasswordListener(e -> UI.getCurrent().navigate("register"));
        loginOverlay.addLoginListener(event -> {
            try {
                authService.authenticate(event.getUsername(), event.getPassword());
                UI.getCurrent().navigate("home");
            }
            catch (AuthenticationException e) {
                loginOverlay.setError(true);
            }
        });
        add(loginOverlay);
        loginOverlay.setOpened(true);
        loginOverlay.getElement().setAttribute("no-autofocus", "");
    }
}
