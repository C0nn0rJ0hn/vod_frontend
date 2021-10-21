package com.myapp.vod.views;

import com.myapp.vod.backend.client.account.AccountClient;
import com.myapp.vod.backend.domain.*;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;

@Route(value = "rent", layout = MainView.class)
@PageTitle("Rented movies and tv shows")
public class RentView extends VerticalLayout {
    Grid<MovieDto> movieGrid = new Grid<>(MovieDto.class);
    Grid<RentMovieDto> rentMovieGrid = new Grid<>(RentMovieDto.class);
    Grid<TvShowDto> tvGrid = new Grid<>(TvShowDto.class);
    Grid<RentTvShowDto> rentTvShowGrid = new Grid<>(RentTvShowDto.class);
    AccountClient accountClient;

    public RentView(AccountClient accountClient) {
        this.accountClient = accountClient;

        HorizontalLayout movieLayout = new HorizontalLayout();
        configureMovieGrid();
        configureRentMovieGrid();
        movieLayout.setSizeFull();
        movieLayout.add(movieGrid, rentMovieGrid);

        HorizontalLayout tvLayout = new HorizontalLayout();
        configureTvGrid();
        configureRentTvShowGrid();
        tvLayout.setSizeFull();
        tvLayout.add(tvGrid, rentTvShowGrid);

        AccountDto currentAccount = VaadinSession.getCurrent().getAttribute(AccountDto.class);

        updateMovieLists(currentAccount);
        updateTvLists(currentAccount);

        setSizeFull();
        add(new H3("Rented Movies"), movieLayout, new H3("Rented tv shows"), tvLayout);
    }

    private void configureGrid(Grid grid) {
        grid.setSizeFull();
        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_WRAP_CELL_CONTENT);
    }

    private void configureMovieGrid() {
        movieGrid.setColumns("title", "popularity", "voteAverage");
        movieGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        configureGrid(movieGrid);
    }

    private void configureRentMovieGrid() {
        rentMovieGrid.setColumns("rentDate", "expireDate");
        rentMovieGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        configureGrid(rentMovieGrid);
    }

    private void configureTvGrid() {
        tvGrid.setColumns("name", "popularity", "voteAverage");
        tvGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        configureGrid(tvGrid);
    }

    private void configureRentTvShowGrid() {
        rentTvShowGrid.setColumns("rentDate", "expireDate");
        rentTvShowGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        configureGrid(rentTvShowGrid);
    }

    private void updateMovieLists(AccountDto accountDto) {
        movieGrid.setItems(accountClient.getRentedMoviesForAccount(accountDto.getId()));
        rentMovieGrid.setItems(accountClient.getMovieRentsForAccount(accountDto.getId()));
    }

    private void updateTvLists(AccountDto accountDto) {
        tvGrid.setItems(accountClient.getRentedTvShowsForAccount(accountDto.getId()));
        rentTvShowGrid.setItems(accountClient.getTvShowRentsForAccount(accountDto.getId()));
    }
}
