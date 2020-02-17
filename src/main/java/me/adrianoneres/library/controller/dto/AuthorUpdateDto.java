package me.adrianoneres.library.controller.dto;

import javax.validation.constraints.NotNull;

public class AuthorUpdateDto {

    @NotNull
    public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
