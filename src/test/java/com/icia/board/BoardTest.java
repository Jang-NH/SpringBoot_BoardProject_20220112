package com.icia.board;

import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.service.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.IntStream;

@SpringBootTest
public class BoardTest {

    @Autowired
    private BoardService bs;

    @Test
    @DisplayName("글작성테스트")
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
    @DisplayName("글목록테스트")
    public void boardListTest() {

    }

}
