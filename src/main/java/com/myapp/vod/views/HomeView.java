package com.myapp.vod.views;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;


@Route(value = "home", layout = MainView.class)
@PageTitle("Home")
public class HomeView extends VerticalLayout {

    public HomeView() {
    }
}
