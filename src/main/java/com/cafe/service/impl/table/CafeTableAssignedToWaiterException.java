package com.cafe.service.impl.table;

public class CafeTableAssignedToWaiterException extends RuntimeException {
    public CafeTableAssignedToWaiterException(Long id) {
        super("No cafe table assigned to waiter having an id of " + id);
    }
}
