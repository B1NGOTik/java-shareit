package ru.practicum.shareit.item.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CommentDto {
    private Long id;
    private Long itemId;
    private String authorName;
    private String text;
    private LocalDateTime created;

    public CommentDto(Comment comment) {
        this.id = comment.getId();
        this.itemId = comment.getItem().getId();
        this.authorName = comment.getUser().getName();
        this.text = comment.getText();
        this.created = comment.getCreated();
    }
}
