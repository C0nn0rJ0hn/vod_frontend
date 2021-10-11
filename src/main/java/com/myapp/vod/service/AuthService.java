package com.myapp.vod.service;

import com.myapp.vod.backend.client.account.AccountClient;
import com.myapp.vod.backend.domain.AccountDto;
import com.myapp.vod.exception.AuthenticationException;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




@Service
public class AuthService {

    @Autowired
    private AccountClient accountClient;

    public void authenticate(String email, String password) throws AuthenticationException {

        for (AccountDto account : accountClient.getAccounts()) {
            if (account.getEmail().equals(email) && account.getPassword().equals(password)) {
                UI.getCurrent().getSession().setAttribute(AccountDto.class, account);
                Notification.show("User logged in!");
            }
            else {
                throw new AuthenticationException();
            }
        }
    }
}
