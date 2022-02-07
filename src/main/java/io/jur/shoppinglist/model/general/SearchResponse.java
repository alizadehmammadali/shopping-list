package io.jur.shoppinglist.model.general;

import lombok.Data;

@Data
public class SearchResponse<T> {
    T data;
    int size;
    int totalPages;
    long totalElements;
}

