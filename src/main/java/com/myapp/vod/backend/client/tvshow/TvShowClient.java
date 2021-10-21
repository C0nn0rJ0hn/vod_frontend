package com.myapp.vod.backend.client.tvshow;

import com.myapp.vod.backend.client.movie.MovieClient;
import com.myapp.vod.backend.config.BackendConfig;
import com.myapp.vod.backend.domain.MovieDto;
import com.myapp.vod.backend.domain.TvShowDto;
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
public class TvShowClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(TvShowClient.class);

    public List<TvShowDto> getTvShowsByKeyword(String keyword) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "tvShows/searchByKeyword")
                .queryParam("keyword", keyword)
                .build()
                .encode()
                .toUri();

        try {
            TvShowDto[] response = restTemplate.getForObject(url, TvShowDto[].class);
            return new ArrayList<>(Optional.ofNullable(response)
                    .map(Arrays::asList)
                    .orElse(Collections.emptyList()));
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
