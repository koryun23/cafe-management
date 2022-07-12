package com.cafe.service.impl.table;

public class CafeTableNotFoundException extends RuntimeException {
    public CafeTableNotFoundException(Long id) {
        super("No cafe table found having an id of " + id);
    }
}
