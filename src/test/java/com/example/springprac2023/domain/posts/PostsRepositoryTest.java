package com.example.springprac2023.domain.posts;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
// 별다른 설정 없이 @SpringBootTest를 사용할 경우, H2 데이터베이스를 자동으로 실행
public class PostsRepositoryTest {

    @Autowired
    PostsRepository postsRepository;

    // Junit에서 단위 테스트가 끝날 때마다 수행되는 메소드 지정
    // 보통은 배포 전 전체 테스트를 수행할 때 테스트간 데이터 침법을 막기 위해 사용
    // 여러 테스트가 동시에 수해오디면 테스트용 데이터베이스은 H2에 데이터가 그대로 남아있어 다음 테스트 실행 시 테스트가 실패할 수 있음
    @AfterEach
    public void cleanUp() {
        postsRepository.deleteAll();
    }

    @Test
    @DisplayName("게시글 저장 불러오기")
    public void 게시글_저장_불러오기() {
        // given
        String title = "테스트 게시글 title";
        String content = "테스트 본문 content";

        // 테이블 posts에 insert/update 쿼리 실행
        // id 값이 있다면 update가, 없다면 insert 쿼리 실행
        postsRepository.save(Posts.builder()
                .title(title)
                .content(content)
                .author("kbr_uni@naver.com")
                .build());
        
        // when
        // 테이블 posts에 있는 모든 데이터를 조회해오는 메소드
        List<Posts> list = postsRepository.findAll();
        
        // then
        Posts post = list.get(0);
        assertThat(post.getTitle()).isEqualTo(title);
        assertThat(post.getContent()).isEqualTo(content);
    }

    @Test
    @DisplayName("BaseTimeEntity 등록")
    public void BaseTimeEntity_등록() throws Exception {
        // given
        LocalDateTime now = LocalDateTime.of(2024, 1, 3, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());

        // when
        List<Posts> postsList = postsRepository.findAll();

        // then
        Posts posts = postsList.get(0);

        System.out.println(">>>>>>>>> createDate= " + posts.getCreatedDate() +
                ", modifiedDate= " + posts.getModifiedDate());

        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
