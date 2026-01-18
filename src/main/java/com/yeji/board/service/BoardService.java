package com.yeji.board.service;

import com.yeji.board.controller.dto.BoardDto;
import com.yeji.board.repository.BoardRepository;
import com.yeji.board.repository.entity.Board;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BoardService {

    private final BoardRepository boardRepository;

    /**
     * 게시글 등록
     *
     * @param boardDto 게시글 등록 DTO
     */
    public void createBoard(BoardDto boardDto) {
        Board board = Board.from(boardDto);
        boardRepository.save(board);
    }

    /**
     * 게시글 수정
     *
     * @param boardDto 게시글 수정 DTO
     */
    public void updateBoard(BoardDto boardDto) {
        Optional<Board> optionalBoard = boardRepository.findById(boardDto.getBoardId());
        if (optionalBoard.isEmpty()) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다.");
        }
        Board board = optionalBoard.get();
        board.update(boardDto);
    }

    /**
     * 게시글 삭제
     *
     * @param boardId 게시글 ID
     */
    public void deleteBoard(Long boardId) {
        boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        boardRepository.deleteById(boardId);
    }

    /**
     * 게시글 조회
     *
     * @param boardId 게시글 ID
     */
    public BoardDto getBoardByBoardId(Long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글입니다."));

        return BoardDto.from(board);
    }

    /**
     * 게시글 목록 조회
     *
     * @return 게시글 목록
     */
    public List<BoardDto> getBoardList() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream()
                .map(BoardDto::from)
                .toList();
    }
}
