// HelloController.java

package com.example.springprac2023.web;

import com.example.springprac2023.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    // HTTP Method인 Get의 요청을 받을 수 있는 API 만들어줌
    // 예전에는 @RequestMapping (method = RequestMethod.GET)으로 사용되었음.
    // 이제 이 프로젝트는 /hello로 요청이 오면 문자열 hello를 반환하는 기능
    @GetMapping("/hello")
    public String hello(){
        return "hallo";
    }

    @GetMapping("/hello/dto")
    public HelloResponseDto helloDto(@RequestParam("name") String name, @RequestParam("amount") int amount) {
        // @RequestParam
        // - 외부에서 API로 넘긴 파라미터를 가져오는 annotation
        // - 외부에서 name(@RequestParam("name"))이란 이름으로 넘긴 파라미터를 메소드 파라미터 name(String name)에 저장
        return new HelloResponseDto(name, amount);
    }
}
