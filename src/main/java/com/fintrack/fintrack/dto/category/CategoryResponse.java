package com.fintrack.fintrack.dto.category;

import com.fintrack.fintrack.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class CategoryResponse {

    private Long id;

    private String name;

    private TransactionType type;
}
