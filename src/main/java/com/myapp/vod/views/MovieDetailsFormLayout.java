package com.myapp.vod.views;

import com.myapp.vod.backend.client.omdb.OmdbClient;
import com.myapp.vod.backend.domain.MovieDto;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;


import java.util.stream.Stream;

public class MovieDetailsFormLayout extends FormLayout {
    private final Image poster = new Image();
    private final TextArea overviewField = new TextArea("Overview");
    private final TextField popularityField = new TextField("Popularity");
    private final TextField voteCountField = new TextField("Total Votes");
    private final TextField idField = new TextField("Movie Id");

    public MovieDetailsFormLayout() {

        Stream.of(overviewField, popularityField, voteCountField, idField).forEach(field -> {
            field.setReadOnly(true);
            add(poster, overviewField, popularityField, voteCountField, idField);
            setResponsiveSteps(new ResponsiveStep("0", 3));
            setColspan(overviewField, 3);
        });
    }

    public void setMovie(MovieDto movie, OmdbClient omdbClient) {
        poster.setSrc(omdbClient.getPoster(movie.getTitle()));
        poster.setMaxHeight("445px");
        poster.setMaxWidth("300px");
        overviewField.setValue(movie.getOverview());
        popularityField.setValue(movie.getPopularity().toString());
        voteCountField.setValue(movie.getVoteCount().toString());
        idField.setValue(movie.getId().toString());
    }
}
