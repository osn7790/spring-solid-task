package com.puzzlix.solid_task.domain.issue.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
    private Long issueId;
    private String sender; // 메세지를 보낸 사람
    private String content; // 메세지 내용


}
