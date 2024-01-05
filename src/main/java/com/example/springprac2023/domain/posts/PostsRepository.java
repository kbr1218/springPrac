package com.example.springprac2023.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository<Entity 클래스, PK 타입>을 상속하면 기본적인 CRUD 메소드가 자동으로 생성됨
// @Repository를 추가할 필요 없음
// 주의할 점: Entity 클래스와 기본 Entity Repository는 함께 위치해야 함
// Entity 클래스는 기본 Repository 없이는 제대로 역할을 할 수 없음
// 프로젝트 규모가 커져 도메인별로 프로젝트를 분리해야 한다면 Entity 클래스와 Repository는 함께 움직여야 하므로 도메인 패키지에서 함께 관리
public interface PostsRepository  extends JpaRepository<Posts, Long> {

}
