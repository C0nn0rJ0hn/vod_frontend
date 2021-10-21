package com.myapp.vod.views;

import com.myapp.vod.backend.client.account.AccountClient;
import com.myapp.vod.backend.domain.AccountDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.VaadinSession;



@Route(value = "logout", layout = MainView.class)
@PageTitle("Logout")
public class LogoutView extends VerticalLayout {

    AccountClient accountClient;

    public LogoutView(AccountClient accountClient) {
        this.accountClient = accountClient;
        AccountDto accountDto = VaadinSession.getCurrent().getAttribute(AccountDto.class);
        accountDto.setLoggedIn(false);
        accountClient.updateAccount(accountDto);
        UI.getCurrent().getPage().setLocation("http://localhost:8081");
    }

}
