package com.myapp.vod.backend.client.rentMovie;

import com.myapp.vod.backend.config.BackendConfig;
import com.myapp.vod.backend.domain.RentMovieDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class RentMovieClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;

    public void rentMovie(RentMovieDto rentMovieDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "rentMovies")
                .build()
                .encode()
                .toUri();

        restTemplate.put(url, rentMovieDto);
    }
}
