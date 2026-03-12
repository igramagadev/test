package com.example.demo.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String email;
    private Integer role_id;
    private Integer points;
    private String avatar_url;
}
