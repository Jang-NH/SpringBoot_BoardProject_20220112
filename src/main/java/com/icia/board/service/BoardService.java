package com.icia.board.service;

import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardSaveDTO;

import java.util.List;

public interface BoardService {
    Long save(BoardSaveDTO boardSaveDTO);
    List<BoardDetailDTO> findAll();

    BoardDetailDTO findById(Long boardId);
}
