// HelloControllerTest.java

package com.example.springprac2023.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
// import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.oauth2Login;


// @RunWith(SpringRunner.class)
// JUnit5로 넘어오면서 @RunWith(SpringRunner.class)에서 해당 어노테이션으로 바뀌었다.
// @SpringBootTest를 적용하면 @ExtendWith (SpringExtension.class)를 표함하고 있어 생략 가능
@MockBean(JpaMetamodelMappingContext.class)
@ExtendWith(SpringExtension.class)   // JUnit5부터 @RunWith(SpringRunner.class 대체)
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {
    @Autowired // 스프링 빈(Bean) 주입
    private MockMvc mvc;

    @Test
    @DisplayName("hallo가 리턴된다")
    public void hallo_리턴() throws Exception {
        String hello = "hallo";

        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
    }


    @Test
    public void helloDto_리턴() throws Exception {
        String name = "thisIsName";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                // param
                // - API 테스트할 때 사용될 요청 파라미터를 설정 (값은 String만 허용)
                // - 숫자/날짜 등의 데이터도 등록할 때는 문자열로 변경해야만 가능
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                // jsonPath
                // - JSON 응답값을 필드별로 검증할 수 있는 메소드
                // - $를 기준으로 필드명 명시
                // - 여기선 name과 amount를 검증하니 $.name, $.amount로 검증
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));

    }

}
