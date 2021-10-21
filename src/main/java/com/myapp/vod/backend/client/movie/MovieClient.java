package com.myapp.vod.backend.client.movie;

import com.myapp.vod.backend.config.BackendConfig;
import com.myapp.vod.backend.domain.MovieDto;
import jdk.dynalink.linker.LinkerServices;
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
public class MovieClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(MovieClient.class);

    public List<MovieDto> getMoviesByKeyword(String keyword) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "movies/searchByKeyword")
                .queryParam("keyword", keyword)
                .build()
                .encode()
                .toUri();

        try {
            MovieDto[] response = restTemplate.getForObject(url, MovieDto[].class);
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
