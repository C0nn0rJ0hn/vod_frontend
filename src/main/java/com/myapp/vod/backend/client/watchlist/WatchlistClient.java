package com.myapp.vod.backend.client.watchlist;

import com.myapp.vod.backend.config.BackendConfig;
import com.myapp.vod.backend.domain.MovieDto;
import com.myapp.vod.backend.domain.TvShowDto;
import com.myapp.vod.backend.domain.WatchListDto;
import com.myapp.vod.exception.WatchlistNotFoundException;
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
public class WatchlistClient {

    private final RestTemplate restTemplate;
    private final BackendConfig backendConfig;
    private static final Logger LOGGER = LoggerFactory.getLogger(WatchlistClient.class);

    public WatchListDto getWatchlist(Integer watchListId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "watchlists")
                .pathSegment(watchListId.toString())
                .build()
                .encode()
                .toUri();

        try {
            WatchListDto response = restTemplate.getForObject(url, WatchListDto.class);
            return Optional.ofNullable(response).orElseThrow(WatchlistNotFoundException::new);
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }
    }

    public void saveWatchlist(WatchListDto watchListDto) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "watchlists")
                .build()
                .encode()
                .toUri();

        restTemplate.postForObject(url, watchListDto, WatchListDto.class);
    }

    public List<MovieDto> getMoviesInWatchlist(Integer watchlistId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "watchlists")
                .pathSegment(watchlistId.toString())
                .pathSegment("movies")
                .build()
                .encode()
                .toUri();

        try {
            MovieDto[] response = restTemplate.getForObject(url, MovieDto[].class);
            return Optional.ofNullable(response).map(Arrays::asList).orElse(new ArrayList<>());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    public List<TvShowDto> getTvShowsInWatchlist(Integer watchlistId) {
        URI url = UriComponentsBuilder.fromHttpUrl(backendConfig.getBackendApiEndpoint() + "watchlists")
                .pathSegment(watchlistId.toString())
                .pathSegment("tvShows")
                .build()
                .encode()
                .toUri();

        try {
            TvShowDto[] response = restTemplate.getForObject(url, TvShowDto[].class);
            return Optional.ofNullable(response).map(Arrays::asList).orElse(new ArrayList<>());
        }
        catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return Collections.emptyList();
        }
    }
}
