package com.mopstat.mopstat.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserDTO {
    private Long id;

    @NotBlank(message = "Nazwa użytkownika nie może być pusta")
    @Size(min = 4, max = 50, message = "Nazwa musi mieć 4–50 znaków")
    private String username;

    @NotBlank(message = "Hasło nie może być puste")
    @Size(min = 6, message = "Hasło musi mieć co najmniej 6 znaków")
    private String password;

    public UserDTO() {}

    public UserDTO(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setPassword(String password) { this.password = password; }
}
