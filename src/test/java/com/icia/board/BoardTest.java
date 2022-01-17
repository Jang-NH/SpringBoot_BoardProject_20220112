package com.icia.board;

import com.icia.board.Repository.BoardRepository;
import com.icia.board.common.PagingConst;
import com.icia.board.dto.BoardPagingDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardService bs;

    @Autowired
    private BoardRepository br;

    @Test
    @DisplayName("글작성 테스트")
    public void newBoards() {
        /*
            IntStream.rangeClosed(1, 30) : 끝 숫자 포함
            IntStream.range(1, 30) : 끝 숫자 미포함
         */
        IntStream.rangeClosed(1, 30).forEach(i -> {
            bs.save(new BoardSaveDTO("writer" + i,"pw" + i, "title" + i, "contents" + i));
        });
    }

    @Test
    @DisplayName("삼항연산자")
    public void test1() {

        int num1 = 10;
        int num2 = 0;

        if (num1 == 10) {
            num2 = 5;
        } else {
            num2 = 100;
        }

        // 좌변 = 조건식? true:false; (단순한 조건식, 좌변 필수)
        num2 = (num1==10)? 5:100; // 조건식 좌변 num2에 대입
    }

    @Test
    @Transactional
    @DisplayName("페이징 테스트")
    public void pagingTest() {
        int page = 0;
        Page<BoardEntity> boardEntities = br.findAll(PageRequest.of(page, PagingConst.PAGE_LIMIT, Sort.by(Sort.Direction.DESC, "id")));
        // Page 객체가 제공해주는 메서드 확인
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청페이지에 들어있는 데이터
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글 갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // 요청페이지(jpa 기준)
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지인지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지인지 여부

        // Page<BoardEntity> -> Page<BoardPagingDTO>
        // map() : 엔티티가 담긴 페이지 객체를 dto가 담긴 페이지객체로 변환해주는 역할 (converter)
        Page<BoardPagingDTO> boardList = boardEntities.map(
                board -> new BoardPagingDTO(board.getId(),
                                            board.getBoardWriter(),
                                            board.getBoardTitle())
        );
    }

    @Test
    @Transactional
    @Rollback(value = false)
    @DisplayName("게시글 삭제 테스트")
    public void boardDelete() {
        br.deleteById(1L);
    }
}
