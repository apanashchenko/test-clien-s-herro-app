package com.s.hero.model;

import lombok.Data;


/**
 * Created by alpa on 11/24/18
 */
@Data
public class RequestMessage {

    private Long id;
    private Long heroId;
    private String status;
    private String description;
}
