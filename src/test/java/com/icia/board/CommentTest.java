package com.icia.board;

import com.icia.board.Repository.BoardRepository;
import com.icia.board.Repository.CommentRepository;
import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.CommentDetailDTO;
import com.icia.board.dto.CommentSaveDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.entity.CommentEntity;
import com.icia.board.service.BoardService;
import com.icia.board.service.CommentService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class CommentTest {

    @Autowired
    private BoardService bs;

    @Autowired
    private BoardRepository br;

    @Autowired
    private CommentService cs;

    @Autowired
    private CommentRepository cr;

    @Test
    @Transactional // 연관관계 있을 시 반드시 작성
    @Rollback(value = false)
    @DisplayName("댓글작성 테스트")
    public void newComments() {

        // 게시판 글 생성
        BoardSaveDTO boardSaveDTO = new BoardSaveDTO("bwriter1", "bpw1", "btitle1", "bcontents1");
        Long boardId = bs.save(boardSaveDTO);

        // 댓글 생성
        CommentSaveDTO commentSaveDTO = new CommentSaveDTO(boardId, "cwriter1", "ccontents1");
        cs.save(commentSaveDTO);
    }

    @Test
    @Transactional
    @DisplayName("댓글 조회")
    public void findByIdTest() {
        CommentEntity commentEntity = cr.findById(1L).get(); // 1L 이라고 쓴 이유 : findById 매개변수 타입 = Long (Long 타입은 숫자 뒤에 L 붙여야 함)
        System.out.println("commentEntity.toString() = " + commentEntity.toString());
        System.out.println("commentEntity.getId() = " + commentEntity.getId());
        System.out.println("commentEntity.getCommentWriter() = " + commentEntity.getCommentWriter());
        System.out.println("commentEntity.getCommentContents() = " + commentEntity.getCommentContents());
        System.out.println("commentEntity.getBoardEntity() = " + commentEntity.getBoardEntity());
        System.out.println("commentEntity.getBoardEntity().getId() = " + commentEntity.getBoardEntity().getId());
    }

    @Test
    @Transactional
    @DisplayName("댓글 목록 출력")
    public void findAllTest() {
        List<CommentDetailDTO> commentDetailDTOS = cs.findAll(1L);
        for (CommentDetailDTO c: commentDetailDTOS) {
            System.out.println("c.toString() = " + c.toString());
        }
    }
}
