package com.example.springprac2023.config.auth.dto;

import com.example.springprac2023.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }

//    public SessionUser(User user) {
//        if (user != null) {
//            this.name = user.getName();
//            this.email = user.getEmail();
//            this.picture = user.getPicture();
//        } else {
//            // 예외 처리 또는 기본값 설정 등을 수행할 수 있습니다.
//            this.name = "DefaultName";
//            this.email = "DefaultEmail";
//            this.picture = "DefaultPicture";
//        }
//    }
}
