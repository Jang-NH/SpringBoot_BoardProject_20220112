package com.icia.board.controller;

import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/board")
@RequiredArgsConstructor
@Slf4j
public class BoardController {
    private final BoardService bs;

    // 글작성 폼
    @GetMapping("/save")
    public String saveForm() {

        return "board/save";
    }

    // 글작성 처리
    @PostMapping("/save")
    public String save(@ModelAttribute BoardSaveDTO boardSaveDTO) { // ModelAttribute 생략 가능(Timeleaf 사용하면 필수 작성!)
        Long boardId = bs.save(boardSaveDTO);
        return "redirect:/board/";
    }

    // 글목록
    @GetMapping("/")
    public String findAll(Model model) {
        List<BoardDetailDTO> boardList = bs.findAll();
        model.addAttribute("boardList", boardList);
        log.info("findAll 호출"); // 로그 남기기
        return "board/findAll";
    }

    // 상세 조회 (get, /board/{boardId})
    @GetMapping("/{boardId}")
    public String findById(@PathVariable Long boardId, Model model) {
        log.info("글보기 메서드 호출. 요청 글번호 : {}", boardId); // 변수 출력 원할 시 {} 반드시 필요!
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board", board);
        return "board/findById";
    }

}
