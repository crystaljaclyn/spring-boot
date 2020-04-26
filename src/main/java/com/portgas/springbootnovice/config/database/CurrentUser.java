package com.portgas.springbootnovice.config.database;

import io.ebean.config.CurrentUserProvider;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser implements CurrentUserProvider {
    @Override
    public Object currentUser() {
        return "system";
    }
}
