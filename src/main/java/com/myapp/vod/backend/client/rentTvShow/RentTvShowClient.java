package com.myapp.vod.backend.client.rentTvShow;

import com.myapp.vod.backend.config.BackendConfig;
import com.myapp.vod.backend.domain.RentTvShowDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class RentTvShowClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;

    public void rentTvShow(RentTvShowDto rentTvShowDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "rentTvShows")
                .build()
                .encode()
                .toUri();

        restTemplate.put(url, rentTvShowDto);
    }
}
