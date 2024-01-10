package com.example.springprac2023.service;

import com.example.springprac2023.domain.posts.Posts;
import com.example.springprac2023.domain.posts.PostsRepository;
import com.example.springprac2023.web.dto.PostsListResponseDto;
import com.example.springprac2023.web.dto.PostsResponseDto;
import com.example.springprac2023.web.dto.PostsSaveRequestDto;
import com.example.springprac2023.web.dto.PostsUpdateRequestDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class PostsService {
    // 스프링에서 Bean을 주입받는 방식: @Autowired, setter, 생성자
    // 가장 권장하는 방식이 <생성자로 주입>받는 방식 (@Autowired는 권장하지 않음)
    // 즉 생성자로 Bean을 주입 받으면 @Autowired와 동일한 효과를 볼 수 있음
    // final이 선언된 모든 필드를 인자값으로 하는 생성자를 롬복의 @RequiredArgsConstructor가 대신 생성해줌
    // 생성자를 직접 안 쓰고 롬복 annotation을 사용하는 이유
    // -> 해당 클래스의 의존성 관계가 변경될 때마다 생성자 코드를 계속해서 수정하는 번거로움을 해결하기 위함

    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        // update 기능에서 DB에 쿼리를 날리는 부분이 없음 -> JPA의 영속성 컨텍스트 때문
        // "영속성 컨텍스트": 엔티티를 영구 저장하는 환경
        // JPA의 핵심 내용은 엔티티가 영속성 컨텍스트에 포함 되었냐 아니냐로 갈림
        // JPA의 엔티티 매니저가 활성화된 상태로 트랜잭션 안에서 DB에 데이터를 가져오면 이 데이터는 영속성 컨텍스트가 유지된 상태
        // 이 상태에서 해당 데이터의 값을 변경하면 트랜잭션이 끝나는 시점에 해당 테이블에 변경부분을 반영함
        // 즉 Entitiy 객체의 값만 변경하면 별도록 Update 쿼리를 날릴 필요가 없다는 것 == dirty checking
        Posts posts = postsRepository.findById(id)
                // 적절하지 못한 인자(매개변수)를 메소드에 넘겨주었을 때 예외 발생
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete (Long id) {
        Posts posts = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));

        postsRepository.delete(posts);
    }

    @Transactional //(readOnly = true)
    public PostsResponseDto findById (Long id) {
        Posts entity = postsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 사용자가 없습니다. id=" + id));
        return new PostsResponseDto(entity);
    }

    @Transactional //(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        return postsRepository.findAllDesc().stream()
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }


}
