package com.myapp.vod.views;

import com.myapp.vod.backend.client.buy.BuyClient;
import com.myapp.vod.backend.client.movie.MovieClient;
import com.myapp.vod.backend.client.omdb.OmdbClient;
import com.myapp.vod.backend.client.rentMovie.RentMovieClient;
import com.myapp.vod.backend.client.rentTvShow.RentTvShowClient;
import com.myapp.vod.backend.client.tvshow.TvShowClient;
import com.myapp.vod.backend.client.watchlist.WatchlistClient;
import com.myapp.vod.backend.domain.*;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.VaadinSession;


@Route(value = "home", layout = MainView.class)
@PageTitle("Home")
public class HomeView extends VerticalLayout {

    Grid<MovieDto> movieGrid = new Grid<>(MovieDto.class);
    Grid<TvShowDto> tvGrid = new Grid<>(TvShowDto.class);
    TextField filterMovie = new TextField();
    TextField filterTv = new TextField();
    MovieClient movieClient;
    TvShowClient tvShowClient;
    OmdbClient omdbClient;
    WatchlistClient watchlistClient;
    BuyClient buyClient;
    RentMovieClient rentMovieClient;
    RentTvShowClient rentTvShowClient;


    public HomeView(MovieClient movieClient, TvShowClient tvShowClient, OmdbClient omdbClient,
                    WatchlistClient watchlistClient, BuyClient buyClient, RentMovieClient rentMovieClient, RentTvShowClient rentTvShowClient) {
        this.movieClient = movieClient;
        this.tvShowClient = tvShowClient;
        this.omdbClient = omdbClient;
        this.watchlistClient = watchlistClient;
        this.buyClient = buyClient;
        this.rentMovieClient = rentMovieClient;
        this.rentTvShowClient = rentTvShowClient;

        AccountDto currentAccount = VaadinSession.getCurrent().getAttribute(AccountDto.class);

        setSizeFull();
        configureMovieGrid();
        configureTvGrid();

        movieGrid.setItemDetailsRenderer(createMovieDetailsRenderer(omdbClient));
        tvGrid.setItemDetailsRenderer(createTvShowDetailsRenderer(omdbClient));

        VerticalLayout movieLayout = new VerticalLayout();
        movieLayout.setSizeFull();
        movieLayout.add(new H3("Movies"), getToolbarMovie(currentAccount), movieGrid);

        VerticalLayout tvLayout = new VerticalLayout();
        tvLayout.setSizeFull();
        tvLayout.add(new H3("Tv shows"), getToolbarTvShow(currentAccount), tvGrid);

        HorizontalLayout mainLayout = new HorizontalLayout();
        mainLayout.add(
                movieLayout,
                tvLayout
        );
        mainLayout.setSizeFull();

        updateMovieList();
        updateTvShowList();

        add(mainLayout);
    }

    private void toolbar(TextField textField, String placeholder) {
        textField.setPlaceholder(placeholder);
        textField.setClearButtonVisible(true);
        textField.setValueChangeMode(ValueChangeMode.LAZY);
        textField.setPrefixComponent(new Icon(VaadinIcon.SEARCH));
    }

    private void configureGrid(Grid grid) {
        grid.setSizeFull();
        grid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES, GridVariant.LUMO_WRAP_CELL_CONTENT);
    }

    private void configureTvGrid() {
        tvGrid.setColumns("name", "firstAirDate", "voteAverage");
        tvGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        configureGrid(tvGrid);
    }

    private void configureMovieGrid() {
        movieGrid.setColumns("title", "releaseDate", "voteAverage");
        movieGrid.getColumns().forEach(col -> col.setAutoWidth(true));
        configureGrid(movieGrid);
    }



    private HorizontalLayout getToolbarMovie(AccountDto accountDto) {
        filterMovie.addValueChangeListener(e -> updateMovieList());
        toolbar(filterMovie, "Filter by title...");

        Button watchlist = new Button("Add to watchlist");
        Button rent = new Button("Rent");
        Button buy = new Button("Buy");

        watchlist.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
        rent.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
        buy.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);

        WatchListDto watchListDto = watchlistClient.getWatchlist(accountDto.getWatchlistId());
        watchlist.addClickListener(event -> {
            watchListDto.getMoviesId().add(movieGrid.asSingleSelect().getValue().getId());
            watchlistClient.saveWatchlist(watchListDto);
            Notification.show("Selected movie added to watchlist!");
        });

        BuyDto buyDto = buyClient.getBuy(accountDto.getBuyId());
        buy.addClickListener(event -> {
            buyDto.getMoviesId().add(movieGrid.asSingleSelect().getValue().getId());
            buyClient.updateBuy(buyDto);
            Notification.show("Selected movie has been bought");
        });

        rent.addClickListener(event -> {
            RentMovieDto rentMovieDto = new RentMovieDto(accountDto.getId(), movieGrid.asSingleSelect().getValue().getId());
            rentMovieClient.rentMovie(rentMovieDto);
            Notification.show("Selected movie has been rented");
        });

        return new HorizontalLayout(filterMovie, watchlist, rent, buy);
    }

    private HorizontalLayout getToolbarTvShow(AccountDto accountDto) {
        filterTv.addValueChangeListener(e -> updateTvShowList());
        toolbar(filterTv, "Filter by nam...");

        Button watchlist = new Button("Add to watchlist");
        Button rent = new Button("Rent");
        Button buy = new Button("Buy");

        watchlist.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
        rent.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);
        buy.addThemeVariants(ButtonVariant.LUMO_CONTRAST, ButtonVariant.LUMO_PRIMARY);

        WatchListDto watchListDto = watchlistClient.getWatchlist(accountDto.getWatchlistId());
        watchlist.addClickListener(event -> {
            watchListDto.getTvShowsId().add(tvGrid.asSingleSelect().getValue().getId());
            watchlistClient.saveWatchlist(watchListDto);
            Notification.show("Selected tv show added to watchlist!");
        });

        BuyDto buyDto = buyClient.getBuy(accountDto.getBuyId());
        buy.addClickListener(event -> {
            buyDto.getTvShowsId().add(tvGrid.asSingleSelect().getValue().getId());
            buyClient.updateBuy(buyDto);
            Notification.show("Selected tv show has been bought");
        });

        rent.addClickListener(event -> {
            RentTvShowDto rentTvShowDto = new RentTvShowDto(accountDto.getId(), tvGrid.asSingleSelect().getValue().getId());
            rentTvShowClient.rentTvShow(rentTvShowDto);
            Notification.show("Selected tv show has been rented");
        });


        return  new HorizontalLayout(filterTv, watchlist, rent, buy);
    }

    private void updateMovieList() {
        movieGrid.setItems(movieClient.getMoviesByKeyword(filterMovie.getValue()));
    }

    private void updateTvShowList() {
        tvGrid.setItems(tvShowClient.getTvShowsByKeyword(filterTv.getValue()));
    }

    private static ComponentRenderer<MovieDetailsFormLayout, MovieDto> createMovieDetailsRenderer(OmdbClient omdbClient) {
        return new ComponentRenderer<>(MovieDetailsFormLayout::new,
                (movieDetailsFormLayout, movieDto) -> movieDetailsFormLayout.setMovie(movieDto, omdbClient));
    }

    private static ComponentRenderer<TvShowDetailsFormLayout, TvShowDto> createTvShowDetailsRenderer(OmdbClient omdbClient) {
        return new ComponentRenderer<>(TvShowDetailsFormLayout::new,
                (tvShowDetailsFormLayout, tvShowDto) -> tvShowDetailsFormLayout.setTvShow(tvShowDto, omdbClient));
    }
}
