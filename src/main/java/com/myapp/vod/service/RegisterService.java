package com.myapp.vod.service;

import com.myapp.vod.backend.client.account.AccountClient;
import com.myapp.vod.backend.client.user.UserClient;
import com.myapp.vod.backend.domain.AccountDto;
import com.myapp.vod.backend.domain.Gender;
import com.myapp.vod.backend.domain.UserDto;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.notification.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterService {

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private UserClient userClient;

    public void register(String name, String lastname, String birthDate, Gender gender, String email, String password, String country, String language) {
        if (name.trim().isEmpty()) {
            Notification.show("Enter a user first name");
        }
        else if (password.isEmpty()) {
            Notification.show("Enter a password");
        }
        else if (email.isEmpty()) {
            Notification.show("Enter email address");
        }
        else {
            UserDto userDto = userClient.createUser(new UserDto(name, lastname, birthDate, gender));
            Integer userDtoId = userDto.getId();
            accountClient.createAccount(new AccountDto(password, email, country, language, userDtoId));
            Notification.show("Registration succeeded");
        }
    }
}
