package com.myapp.vod.backend.client.account;

import com.myapp.vod.backend.config.BackendConfig;
import com.myapp.vod.backend.domain.*;
import com.myapp.vod.exception.AccountNotFoundException;
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

    public Boolean authenticate(String email, String password) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "accounts/authenticate")
                .queryParam("email", email)
                .queryParam("password", password)
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(url, Boolean.class);
    }

    public AccountDto getAccountByEmailAndPassword(String email, String password) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "accounts/byEmailAndPassword")
                .queryParam("email", email)
                .queryParam("password", password)
                .build()
                .encode()
                .toUri();

        try {
            return Optional.ofNullable(restTemplate.getForObject(url, AccountDto.class)).orElseThrow(AccountNotFoundException::new);
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public void updateAccount(AccountDto accountDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "accounts")
                .build()
                .encode()
                .toUri();

        restTemplate.put(url, accountDto);
    }

    public List<MovieDto> getRentedMoviesForAccount(Integer accountId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "accounts")
                .pathSegment(accountId.toString())
                .pathSegment("rentedMovies")
                .build()
                .encode()
                .toUri();

        try {
            MovieDto[] response = restTemplate.getForObject(url, MovieDto[].class);
            return Optional.ofNullable(response).map(Arrays::asList).orElse(new ArrayList<>());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<RentMovieDto> getMovieRentsForAccount(Integer accountId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "accounts")
                .pathSegment(accountId.toString())
                .pathSegment("movieRents")
                .build()
                .encode()
                .toUri();

        try {
            RentMovieDto[] response = restTemplate.getForObject(url, RentMovieDto[].class);
            return Optional.ofNullable(response).map(Arrays::asList).orElse(new ArrayList<>());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<TvShowDto> getRentedTvShowsForAccount(Integer accountId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "accounts")
                .pathSegment(accountId.toString())
                .pathSegment("rentedTvShows")
                .build()
                .encode()
                .toUri();

        try {
            TvShowDto[] response = restTemplate.getForObject(url, TvShowDto[].class);
            return Optional.ofNullable(response).map(Arrays::asList).orElse(new ArrayList<>());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public List<RentTvShowDto> getTvShowRentsForAccount(Integer accountId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "accounts")
                .pathSegment(accountId.toString())
                .pathSegment("tvShowRents")
                .build()
                .encode()
                .toUri();

        try {
            RentTvShowDto[] response = restTemplate.getForObject(url, RentTvShowDto[].class);
            return Optional.ofNullable(response).map(Arrays::asList).orElse(new ArrayList<>());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }

    public void deleteAccount(Integer accountId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "accounts")
                .pathSegment(accountId.toString())
                .build()
                .encode()
                .toUri();

        restTemplate.delete(url);
    }

}
