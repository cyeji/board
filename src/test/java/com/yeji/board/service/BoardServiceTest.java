package com.yeji.board.service;

import com.yeji.board.controller.dto.BoardDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Test
    public void 게시글_등록() {
        BoardDto boardDto = new BoardDto();
        boardDto.setTitle("테스트제목");
        boardDto.setContent("테스트내용");
        boardService.createBoard(boardDto);
    }

    @Test
    public void 게시글_수정() {
        BoardDto boardDto = new BoardDto();
        boardDto.setBoardId(1L);
        boardDto.setTitle("수정된제목");
        boardDto.setContent("수정된내용");
        boardService.updateBoard(boardDto);
    }

    @Test
    public void 게시글_삭제() {
        boardService.deleteBoard(1L);
    }

    @Test
    public void 게시글_조회() {
        boardService.getBoardByBoardId(1L);
    }

    @Test
    public void 게시글_목록조회() {
        boardService.getBoardList();
    }

}
