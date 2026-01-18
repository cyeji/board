package com.yeji.board.controller.dto;

import com.yeji.board.repository.entity.Board;
import java.time.LocalDateTime;
import lombok.Data;


/**
 * 게시글 등록/수정 DTO
 */
@Data
public class BoardDto {

    private Long boardId;

    /**
     * 게시글 제목
     */
    private String title;

    /**
     * 게시글 내용
     */
    private String content;

    /**
     * 등록일자
     */
    private LocalDateTime createdTime;

    /**
     * 수정일자
     */
    private LocalDateTime updatedTime;

    public static BoardDto from(Board board) {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(board.getId());
        boardDto.setTitle(board.getTitle());
        boardDto.setContent(board.getContent());
        return boardDto;
    }
}
