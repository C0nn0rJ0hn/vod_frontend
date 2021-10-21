package com.myapp.vod.backend.client.omdb;

import com.myapp.vod.backend.config.BackendConfig;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
@RequiredArgsConstructor
public class OmdbClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(OmdbClient.class);

    public String getPoster(String title) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "omdb/search/poster")
                .queryParam("title", title)
                .build()
                .encode()
                .toUri();

        return restTemplate.getForObject(url, String.class);
    }
}
