package com.myapp.vod.backend.client.account;

import com.myapp.vod.backend.config.BackendConfig;
import com.myapp.vod.backend.domain.AccountDto;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AccountClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountClient.class);

    public List<AccountDto> getAccounts() {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "accounts")
                .build()
                .encode()
                .toUri();

        try {
            AccountDto[] response = restTemplate.getForObject(url, AccountDto[].class);
            return Optional.ofNullable(response).map(Arrays::asList).orElse(new ArrayList<>());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public void createAccount(AccountDto accountDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "accounts")
                .build()
                .encode()
                .toUri();

        try {
            restTemplate.postForObject(url, accountDto, AccountDto.class);
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }


}
