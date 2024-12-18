package com.lookatme.todo.Service;

import com.lookatme.todo.DTO.TodoDTO;
import com.lookatme.todo.Entity.TodoEntity;
import com.lookatme.todo.Repository.TodoRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    //서비스에서 작업할 내용을 최종 Repository 동작
    private final TodoRepository todoRepository;
    //컨트롤러(DTO)을 값을 전달받아서 Repository(Entity)작업
    //Repository(Entity)로 값을 받아서 컨트롤러(DTO) 전달
    private final ModelMapper modelMapper;

    /*-------------------------------------
        함수명 : Page<TodoDTO> list(Pageable page)
        인수 : 없음
        결과 : Page<TodoDTO>
        내용 : 페이지 번호를 받아서 테이블의 해당 페이지의 데이터를 읽어와서 컨트롤러에 전달
     --------------------------------------*/
    public Page<TodoDTO> list(Pageable page){
        //화면페이지번호 1,2,3,4.. DB에서 페이지번호 0,1,2,3...
        int currentPage = page.getPageNumber()-1; //화면에 페이지번호를 DB 페이지번호
        int pageLimit = 5; //한 페이지를 구성하는 레코드의 수

        //지정된 내용으로 페이지정보를 재생산(정렬 생략시 기본키로 오름차순(ASC), 내림차순(DES)
        //해당페이지에서 5개의 레코드를 기본키로 내림차순해서 페이지를 구성(최신순)
        Pageable pageable = PageRequest.of(currentPage, pageLimit,
                Sort.by(Sort.Direction.DESC,"id"));

        //페이지 정보에 해당하는 모든 데이터를 읽어온다.
        Page<TodoEntity> todoEntities = todoRepository.findAll(pageable);

        //Entity에 페이지정보와 레코드정보 2개로 구성
        //Entityies에서 하나의 entity를 읽어서 data에 저장
        //data의 Entity를 DTO로 변환해서 todoDTOS에 저장
        Page<TodoDTO> todoDTOS = todoEntities.map(data->modelMapper.map(data, TodoDTO.class));

        return todoDTOS;
    }

    /*-------------------------------------
        함수명 : TodoDTO read(Integer id)
        인수 : 기본키 일련번호
        결과 : TodoDTO
        내용 : 테이블에서 해당번호를 조회해서 데이터를 일어와서 컨트롤러에 전달
     --------------------------------------*/
    public TodoDTO read(Integer id){
        //번호에 해당하는 내용을 테이블에서 조회해서 읽어온다.
        Optional<TodoEntity> read = todoRepository.findById(id);
        //Entity->DTO 변환
        //Todo : 오류처리를 추가할 수 있다. 없으면 null값으로 처리
        TodoDTO todoDTO = modelMapper.map(read, TodoDTO.class);

        return todoDTO; //컨트롤러에 DTO를 전달
    }
    /*-----------------------------------
        함수명 : void insert(TodoDTO todoDTO)
        인수 : DTO로 데이터 그룹
        결과 : 없음
        내용 : 컨트롤러에서 DTO 데이터를 받아서 테이블에 저장
    -----------------------------------*/
    public void insert(TodoDTO todoDTO) {
        //DTO->Entity 변환
        TodoEntity todoEntity = modelMapper.map(todoDTO, TodoEntity.class);
        //저장
        todoRepository.save(todoEntity);
    }
    /*-----------------------------------
        함수명 : void update(TodoDTO todoDTO)
        인수 : DTO로 데이터 그룹
        결과 : 없음
        내용 : 컨트롤러에서 DTO 데이터를 받아서 테이블에 수정
    -----------------------------------*/
    public void update(TodoDTO todoDTO) {
        //DTO->Entity 변환
        TodoEntity todoEntity = modelMapper.map(todoDTO, TodoEntity.class);
        //유효성 검사
        //id값으로 테이블에서 조회해서 레코드를 읽어온다.(있으면 레코드, 없으면 Null)
        Optional<TodoEntity> read = todoRepository.findById(todoDTO.getId());
        if (read.isPresent()) { //전달받은 레코드에 내용이 있으면
            //저장
            todoRepository.save(todoEntity);
        }

    }
    /*-----------------------------------
        함수명 : void delete(Integer id)
        인수 : 기본키 일련번호
        결과 : 없음
        내용 : 컨트롤러에서 id를 받아서 테이블에서 해당 레코드를 삭제
    -----------------------------------*/
    public void delete(Integer id) {
        //삭제처리
        todoRepository.deleteById(id);
    }
}

//삽입, 수정, 삭제, 전체조회(페이지번호, 정렬, 검색), 개별조회
