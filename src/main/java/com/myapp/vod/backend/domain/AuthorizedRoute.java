package com.myapp.vod.backend.domain;

import com.vaadin.flow.component.Component;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
public class AuthorizedRoute {
    private final String path;
    private final String name;
    private final Class<? extends Component> view;
}
