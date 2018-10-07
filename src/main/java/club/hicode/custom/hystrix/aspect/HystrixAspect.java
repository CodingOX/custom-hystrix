package club.hicode.custom.hystrix.aspect;

import club.hicode.custom.hystrix.annotation.CustomHystrix;
import club.hicode.custom.hystrix.domain.User;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.*;

/**
 * 切面的时候，不仅要aspect还要
 *
 * @author LiuChunfu
 * @date 2018/10/7
 */
@Aspect
@Component
public class HystrixAspect {

    @Autowired
    private ExecutorService service;

    @Around(value = "@annotation(club.hicode.custom.hystrix.annotation.CustomHystrix) && " +
            " @annotation(hystrix)  && args(username)")
    public Object aroundHystrix(ProceedingJoinPoint joinPoint, CustomHystrix hystrix, String username) {
        int timeout = hystrix.timeout();
        Future<Object> future = service.submit(() -> {
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
                return errorUser();
            }
        });

        try {
            return future.get(timeout, TimeUnit.SECONDS);
        } catch (Exception e) {
            //e.printStackTrace();
            future.cancel(true);
            System.out.println("超时。。。");
            return errorUser();
        }
    }


    private User errorUser() {

        User user = new User();
        user.setName("error");

        return user;
    }

}
