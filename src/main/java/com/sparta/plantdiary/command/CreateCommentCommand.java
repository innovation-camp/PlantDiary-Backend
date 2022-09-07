package com.sparta.plantdiary.command;

import com.sparta.plantdiary.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public class CreateCommentCommand {
    private String content;
    private Member writer;
    private Long postId;
}
