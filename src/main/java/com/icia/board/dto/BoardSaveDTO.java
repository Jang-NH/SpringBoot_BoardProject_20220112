package com.icia.board.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardSaveDTO {
    // Timeleaf 사용해서 주의 사항 문구 출력해보기!!
    private String boardWriter;
    private String boardPassword;
    private String boardTitle;
    private String boardContents;
}
