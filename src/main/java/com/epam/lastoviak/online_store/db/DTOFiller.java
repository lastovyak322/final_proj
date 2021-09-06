package com.epam.lastoviak.online_store.db;

import java.sql.ResultSet;

public interface DTOFiller<T> {
    T fill(ResultSet rs);

}
