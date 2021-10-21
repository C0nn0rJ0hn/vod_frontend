package com.myapp.vod.views;

import com.myapp.vod.backend.client.account.AccountClient;
import com.myapp.vod.backend.client.watchlist.WatchlistClient;
import com.myapp.vod.backend.domain.AccountDto;
import com.myapp.vod.backend.domain.MovieDto;
import com.myapp.vod.backend.domain.TvShowDto;
import com.myapp.vod.backend.domain.WatchListDto;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "watchlist", layout = MainView.class)
@PageTitle("Watchlist")
public class WatchlistView extends VerticalLayout {
    Grid<MovieDto> movieGrid = new Grid<>(MovieDto.class);
    Grid<TvShowDto> tvGrid = new Grid<>(TvShowDto.class);
    AccountClient accountClient;
    WatchlistClient watchlistClient;
    Button deleteMovie = new Button("Delete");
    Button deleteTv = new Button("Delete");

    public WatchlistView(AccountClient accountClient, WatchlistClient watchlistClient) {
        this.accountClient = accountClient;
        this.watchlistClient = watchlistClient;

        AccountDto currentAccount = VaadinSession.getCurrent().getAttribute(AccountDto.class);

        configureMovieGrid(currentAccount);
        configureTvGrid(currentAccount);


        deleteMovie.addThemeVariants(ButtonVariant.LUMO_ERROR);
        deleteTv.addThemeVariants(ButtonVariant.LUMO_ERROR);

        setSizeFull();
        add(new H3("Movies"), movieGrid, deleteMovie, new H3("Tv shows"), tvGrid, deleteTv);

        updateMovieLists(currentAccount);
        updateTvLists(currentAccount);
    }

    private void configureMovieGrid(AccountDto accountDto) {
        movieGrid.setSizeFull();
        movieGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_WRAP_CELL_CONTENT);
        movieGrid.setColumns("id", "title", "releaseDate", "popularity", "voteAverage", "voteCount");
        movieGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        WatchListDto watchListDto = watchlistClient.getWatchlist(accountDto.getWatchlistId());
        deleteMovie.addClickListener(event -> {
            watchListDto.getMoviesId().remove(movieGrid.asSingleSelect().getValue().getId());
            watchlistClient.saveWatchlist(watchListDto);
            updateMovieLists(accountDto);
        });
    }

    private void configureTvGrid(AccountDto accountDto) {
        tvGrid.setSizeFull();
        tvGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_WRAP_CELL_CONTENT);
        tvGrid.setColumns("id", "name", "firstAirDate", "popularity", "voteAverage", "voteCount");
        tvGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        WatchListDto watchListDto = watchlistClient.getWatchlist(accountDto.getWatchlistId());
        deleteTv.addClickListener(event -> {
            watchListDto.getTvShowsId().remove(tvGrid.asSingleSelect().getValue().getId());
            watchlistClient.saveWatchlist(watchListDto);
            updateTvLists(accountDto);
        });

    }

    private void updateMovieLists(AccountDto accountDto) {
        movieGrid.setItems(watchlistClient.getMoviesInWatchlist(accountDto.getWatchlistId()));
    }

    private void updateTvLists(AccountDto accountDto) {
        tvGrid.setItems(watchlistClient.getTvShowsInWatchlist(accountDto.getWatchlistId()));
    }
}
