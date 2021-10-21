package com.myapp.vod.views;

import com.myapp.vod.backend.client.buy.BuyClient;
import com.myapp.vod.backend.domain.AccountDto;
import com.myapp.vod.backend.domain.MovieDto;
import com.myapp.vod.backend.domain.TvShowDto;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "buy", layout = MainView.class)
@PageTitle("Purchased movies and tv shows")
public class BuyView extends VerticalLayout {
    Grid<MovieDto> movieGrid = new Grid<>(MovieDto.class);
    Grid<TvShowDto> tvGrid = new Grid<>(TvShowDto.class);
    BuyClient buyClient;

    public BuyView(BuyClient buyClient) {
        this.buyClient = buyClient;

        AccountDto currentAccount = VaadinSession.getCurrent().getAttribute(AccountDto.class);

        configureMovieGrid();
        configureTvGrid();

        setSizeFull();
        add(new H3("Movies"), movieGrid, new H3("Tv shows"), tvGrid);

        updateMovieLists(currentAccount);
        updateTvLists(currentAccount);

    }

    private void updateTvLists(AccountDto accountDto) {
        tvGrid.setItems(buyClient.getBoughtTvShows(accountDto.getBuyId()));
    }

    private void updateMovieLists(AccountDto accountDto) {
        movieGrid.setItems(buyClient.getBoughtMovies(accountDto.getBuyId()));
    }

    private void configureTvGrid() {
        tvGrid.setSizeFull();
        tvGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_WRAP_CELL_CONTENT);
        tvGrid.setColumns("id", "name", "firstAirDate", "popularity", "voteAverage", "voteCount");
        tvGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }

    private void configureMovieGrid() {
        movieGrid.setSizeFull();
        movieGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_WRAP_CELL_CONTENT);
        movieGrid.setColumns("id", "title", "releaseDate", "popularity", "voteAverage", "voteCount");
        movieGrid.getColumns().forEach(col -> col.setAutoWidth(true));
    }
}
