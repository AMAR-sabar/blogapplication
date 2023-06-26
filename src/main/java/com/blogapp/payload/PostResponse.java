package com.blogapp.payload;



import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PostResponse {
    private List<PostDto> contents;
    private int pageNumber;
    private int pageSize;
    private long totalElement;
    private int totalPage;
    private  boolean lastPage;
}
