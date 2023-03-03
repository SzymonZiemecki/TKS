package com.tks.infrastructure.products;

import java.util.UUID;

public interface DeleteProductPort {
    void removeItem(UUID id);
}
