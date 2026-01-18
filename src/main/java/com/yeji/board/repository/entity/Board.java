package com.yeji.board.repository.entity;


import com.yeji.board.controller.dto.BoardDto;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 게시글 Entity
 */
@Getter
@RequiredArgsConstructor
@Entity
public class Board extends UpdatedEntity {

    /**
     * 게시글 ID
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * 게시글 제목
     */
    private String title;

    /**
     * 게시글 내용
     */
    private String content;

    public Board(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
    }

    public static Board from(BoardDto boardDto) {
        return new Board(boardDto);
    }

    public void update(BoardDto boardDto) {
        this.title = boardDto.getTitle();
        this.content = boardDto.getContent();
    }
}
