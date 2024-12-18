package com.lookatme.todo.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
//#Builder 변수들을 DTO 만드는 작업
//ToString DTO를 변수로 출력
public class TodoDTO {

    private Integer id; //일련번호

    private String title; //제목

    private String contents; //해야할일

    private LocalDateTime datetodo; //날짜

    private String importance; //중요도

    private String status; //상태

    private LocalDateTime regdate;
    private LocalDateTime moddate;



}
