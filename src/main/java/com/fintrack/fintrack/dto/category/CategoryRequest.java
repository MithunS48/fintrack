package com.fintrack.fintrack.dto.category;


import com.fintrack.fintrack.enums.TransactionType;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CategoryRequest {


    private String name;

    private TransactionType type;
}
