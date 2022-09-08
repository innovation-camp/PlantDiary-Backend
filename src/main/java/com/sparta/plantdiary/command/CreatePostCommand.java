package com.sparta.plantdiary.command;

import com.sparta.plantdiary.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreatePostCommand {

    private String title;

    private String content;

    private String thumbnail;

    private Member writer;
}
