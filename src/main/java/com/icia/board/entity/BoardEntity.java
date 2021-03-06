package com.icia.board.entity;

import com.icia.board.dto.BoardDetailDTO;
import com.icia.board.dto.BoardSaveDTO;
import com.icia.board.dto.BoardUpdateDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "board_table")
public class BoardEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(length = 20)
    private String boardWriter;

    @Column(length = 20)
    private String boardPassword;

    @Column(length = 30)
    private String boardTitle;

    @Column(length = 100)
    private String boardContents;

    // 댓글 연관 관계
    // 게시판 기준 (게시글:댓글 = 1:n) -> OneToMany
    // cascade = CascadeType.ALL, orphanRemoval = true -> cascade on delete
    @OneToMany(mappedBy = "boardEntity", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)

    private List<CommentEntity> commentEntityList = new ArrayList<>();

//    @Column
//    private LocalDateTime boardDate;

    public static BoardEntity toSaveEntity(BoardSaveDTO boardSaveDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setBoardWriter(boardSaveDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardSaveDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardSaveDTO.getBoardTitle());
        boardEntity.setBoardContents(boardSaveDTO.getBoardContents());
//        boardEntity.setBoardDate(LocalDateTime.now());

        return boardEntity;
    }

    public static BoardEntity toUpdateBoard(BoardUpdateDTO boardUpdateDTO) {
        BoardEntity boardEntity = new BoardEntity();

        boardEntity.setId(boardUpdateDTO.getBoardId());
        boardEntity.setBoardWriter(boardUpdateDTO.getBoardWriter());
        boardEntity.setBoardPassword(boardUpdateDTO.getBoardPassword());
        boardEntity.setBoardTitle(boardUpdateDTO.getBoardTitle());
        boardEntity.setBoardContents(boardUpdateDTO.getBoardContents());
//        boardEntity.setBoardDate(boardDetailDTO.getBoardDate());

        return boardEntity;
    }
}
