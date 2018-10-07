package club.hicode.custom.hystrix.web.controller;

import club.hicode.custom.hystrix.domain.User;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * XXXX
 *
 * @author LiuChunfu
 * @date 2018/10/7
 */
@RestControllerAdvice
public class TimeoutControllerAdvice {

    @ExceptionHandler(value = TimeoutException.class)
    public User solveTimeOutExecption(TimeoutException excpeiton) throws IOException {
        return errorUser();
    }


    private User errorUser() {
        User user = new User();
        user.setName("error");
        return user;
    }

}
