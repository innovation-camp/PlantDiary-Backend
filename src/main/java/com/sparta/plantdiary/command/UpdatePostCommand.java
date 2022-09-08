package com.sparta.plantdiary.command;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdatePostCommand {

    private Long id;

    private String title;

    private String content;

    private String thumbnail;
}
