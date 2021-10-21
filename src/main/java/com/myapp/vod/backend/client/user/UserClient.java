package com.myapp.vod.backend.client.user;

import com.myapp.vod.backend.config.BackendConfig;
import com.myapp.vod.backend.domain.UserDto;
import com.myapp.vod.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@Component
@RequiredArgsConstructor
public class UserClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserClient.class);


    public UserDto createUser(UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "users")
                .build()
                .encode()
                .toUri();

        try {
            return restTemplate.postForObject(url, userDto, UserDto.class);
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public UserDto getUser(Integer userId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "users")
                .pathSegment(userId.toString())
                .build()
                .encode()
                .toUri();

        try {
            UserDto response = restTemplate.getForObject(url, UserDto.class);
            return Optional.ofNullable(response).orElseThrow(UserNotFoundException::new);
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public void updateUser(UserDto userDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "users")
                .build()
                .encode()
                .toUri();

           restTemplate.put(url, userDto);
    }

}
