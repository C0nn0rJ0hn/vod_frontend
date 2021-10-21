package com.myapp.vod.backend.client.buy;

import com.myapp.vod.backend.client.account.AccountClient;
import com.myapp.vod.backend.config.BackendConfig;
import com.myapp.vod.backend.domain.BuyDto;
import com.myapp.vod.backend.domain.MovieDto;
import com.myapp.vod.backend.domain.TvShowDto;
import com.myapp.vod.exception.BuyNotFoundException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class BuyClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(BuyClient.class);

    public List<MovieDto> getBoughtMovies(Integer buyId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "buys")
                .pathSegment(buyId.toString())
                .pathSegment("movies")
                .build()
                .encode()
                .toUri();

        try {
            MovieDto[] response = restTemplate.getForObject(url, MovieDto[].class);
            return Optional.ofNullable(response).map(Arrays::asList).orElse(Collections.emptyList());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<TvShowDto> getBoughtTvShows(Integer buyId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "buys")
                .pathSegment(buyId.toString())
                .pathSegment("tvShows")
                .build()
                .encode()
                .toUri();

        try {
            TvShowDto[] response = restTemplate.getForObject(url, TvShowDto[].class);
            return Optional.ofNullable(response).map(Arrays::asList).orElse(Collections.emptyList());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public BuyDto getBuy(Integer buyId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "buys")
                .pathSegment(buyId.toString())
                .build()
                .encode()
                .toUri();

        try {
            BuyDto response = restTemplate.getForObject(url, BuyDto.class);
            return Optional.ofNullable(response).orElseThrow(BuyNotFoundException::new);
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public void updateBuy(BuyDto buyDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "buys")
                .build()
                .encode()
                .toUri();

        restTemplate.put(url, buyDto);
    }
}
