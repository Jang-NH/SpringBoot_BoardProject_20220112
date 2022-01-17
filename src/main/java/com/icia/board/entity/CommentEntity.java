package com.icia.board.entity;

import com.icia.board.dto.CommentSaveDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "comment_talbe")
public class CommentEntity  extends BaseEntity{
    // 댓글 번호, 댓글 작성자, 댓글 내용, 원글

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    // 공식처럼 생각~
    // 원글의 게시글 번호를 참조하기 위한 설정, (댓글:게시글 = n:1) -> ManyToOne
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_table") // 부모테이블(참조하고자하는 테이블)의 pk 컬럼 이름
    private BoardEntity boardEntity; // 참조하고자 하는 테이블을 관리하는 엔티티 자체

    @Column
    private String commentWriter;

    @Column
    private String commentContents;

    public static CommentEntity toSaveEntity(CommentSaveDTO commentSaveDTO, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentSaveDTO.getCommentWriter());
        commentEntity.setCommentContents(commentSaveDTO.getCommentContents());
        commentEntity.setBoardEntity(boardEntity);
        return commentEntity;
    }
}
