package com.example.kinnibackend.config.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.util.SerializationUtils;

import java.util.Base64;

public class CookieUtil {

    // 요청값 (이름, 값, 만료기간)을 바탕으로 쿠키 추가
    public static void addCookie(HttpServletResponse response, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);

        response.addCookie(cookie);
    }

    // 쿠키의 이름을 입력받아 쿠키 삭제
    public static void deleteCookie(HttpServletRequest request, HttpServletResponse response, String name) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            System.out.println("No cookies found"); // 로그 추가
            return;
        }

        for (Cookie cookie : cookies) {
            if (name.equals(cookie.getName())) {
                System.out.println("Deleting cookie 여기가 맞나: " + name); // 로그 추가
                cookie.setValue("");
                cookie.setPath("/");
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }
        System.out.println();
        System.out.println("쿠키삭제?");
    }

    // 객체를 직렬화해서 쿠키로 변환
    public static String serialize(Object obj) {
        return Base64.getUrlEncoder()
                .encodeToString(SerializationUtils.serialize(obj));
    }

    // 역직렬화
    public static <T> T deserialize(Cookie cookie, Class<T> cls) {
        if (cookie == null || cookie.getValue() == null) {
            return null; // 또는 다른 적절한 처리
        }

        byte[] decodedValue = Base64.getUrlDecoder().decode(cookie.getValue());

        return cls.cast(
                SerializationUtils.deserialize(decodedValue)
        );
    }

}

