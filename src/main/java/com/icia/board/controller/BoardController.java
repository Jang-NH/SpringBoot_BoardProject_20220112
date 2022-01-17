package com.icia.board.controller;

import com.icia.board.common.PagingConst;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardPagingDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import com.icia.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    // 페이징 처리 (/board?page=5) -> 글이 추가되면 페이지에 해당하는 글이 바뀜(페이지 : 고유 정보 아님)으로 Query String(주소값 뒤에 물음표)을 쓰는 것이 좋다.
    // restful한 주소(주소만으로 뭘하고 싶은지 알 수 있음)로 5번 글(글 : 고유 정보) 확인 (/board/5)
    @GetMapping
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) { // page defailt(기본) 값 : 1
        Page<BoardPagingDTO> boardList = bs.paging(pageable);;
        model.addAttribute("boardList", boardList);
        int startPage = (((int) (Math.ceil((double) pageable.getPageNumber() / PagingConst.BLOCK_LIMIT))) - 1) * PagingConst.BLOCK_LIMIT + 1;
        int endPage = ((startPage + PagingConst.BLOCK_LIMIT - 1) < boardList.getTotalPages()) ? startPage + PagingConst.BLOCK_LIMIT - 1 : boardList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "board/paging";
    }

    // 상세 조회 (get, /board/{boardId})
    @GetMapping("/{boardId}")
    public String findById(@PathVariable Long boardId, Model model) {
        log.info("글보기 메서드 호출. 요청 글번호 : {}", boardId); // 변수 출력 원할 시 {} 반드시 필요!
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board", board);
        return "board/findById";
    }

    // 상세 조회 (post, ajax)
    @PostMapping("/{boardId}")
    public ResponseEntity findById2(@PathVariable Long boardId) {
        BoardDetailDTO board = bs.findById(boardId);
        return new ResponseEntity<BoardDetailDTO>(board, HttpStatus.OK); // ResponseEntity를 사용해 data와 상태코드를 함께 전달, <> 안에 객체(board)의 타입 입력
    }

    // 글 수정 폼
    @GetMapping("/update/{boardId}")
    public String updateForm(@PathVariable Long boardId, Model model) {
        BoardDetailDTO board = bs.findById(boardId);
        model.addAttribute("board", board);
        return "board/update";
    }

    // 글 수정 처리 (post)
    @PostMapping("/update")
    public String update1(@ModelAttribute BoardUpdateDTO boardUpdateDTO) {
        bs.update(boardUpdateDTO);
        return "redirect:/board/" + boardUpdateDTO.getBoardId();
    }

    // 글 수정 처리 (put)
    @PutMapping("/{boardId}")
    public ResponseEntity update2(@RequestBody BoardUpdateDTO boardUpdateDTO) {  // ajax로 넘어오면 @RequestBody 필요
        bs.update(boardUpdateDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    // 글 삭제 (delete)
    @DeleteMapping("/{boardId}")
    public ResponseEntity deleteById(@PathVariable Long boardId) {
        bs.deleteById(boardId);
        return new ResponseEntity<>(HttpStatus.OK);
    }



}
