package com.s.hero.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alpa on 11/23/18
 */
@Data
public class Hero {

    private Long id;
    private String name;
    private String title;
    private String description;
    private String status;
    private String authorComment;
    private String arproverComment;
    List<RequestMessage> messages = new ArrayList<>();

}
