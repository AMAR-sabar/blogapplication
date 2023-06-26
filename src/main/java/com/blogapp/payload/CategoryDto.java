package com.blogapp.payload;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
    private Integer categoryId;
    @NotEmpty
    @Size(min=4 , max = 15, message = "Title name should be in between 4 to 15 chars !!!")
    private String categoryTitle;
    @NotEmpty
    @Size(min = 10, message = "Description should be more than 10 chars !!!")
    private String categoryDescription;
}
