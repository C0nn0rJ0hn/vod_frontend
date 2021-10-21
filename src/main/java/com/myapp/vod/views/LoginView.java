package com.myapp.vod.views;

import com.myapp.vod.backend.client.account.AccountClient;
import com.myapp.vod.backend.domain.AccountDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.login.LoginOverlay;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;


@Route(value = "")
@PageTitle("Login")
public class LoginView extends  Div {

    public LoginView(AccountClient accountClient) {
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
            accountClient.authenticate(event.getUsername(), event.getPassword());
            if (!accountClient.authenticate(event.getUsername(), event.getPassword())) {
                loginOverlay.setError(true);
            }
            else {
                AccountDto currentAccount = accountClient.getAccountByEmailAndPassword(event.getUsername(), event.getPassword());
                VaadinSession.getCurrent().setAttribute(AccountDto.class, currentAccount);
                Notification.show("User logged in!");
                UI.getCurrent().navigate("home");
            }
        });
        add(loginOverlay);
        loginOverlay.setOpened(true);
        loginOverlay.getElement().setAttribute("no-autofocus", "");
    }
}
