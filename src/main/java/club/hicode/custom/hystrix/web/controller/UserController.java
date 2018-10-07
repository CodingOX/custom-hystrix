package club.hicode.custom.hystrix.web.controller;

import club.hicode.custom.hystrix.annotation.CustomHystrix;
import club.hicode.custom.hystrix.domain.User;
import cn.hutool.core.util.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * XXXX
 *
 * @author LiuChunfu
 * @date 2018/10/7
 */
@RestController
public class UserController {


    @Autowired
    private ExecutorService executorService;

    @GetMapping("/user")
    public User user(@RequestParam String userName) {
        return findUser(userName);
    }

    @GetMapping("/user-1")
    public User user1(@RequestParam String userName) {
        Future<User> future = executorService.submit(() -> findUser(userName));
        User user;
        try {
            user = future.get(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            //可以通过该方式取消线程执行
            future.cancel(true);
            return errorUser();
        }
        return user;
    }

    @GetMapping("/user-2")
    public User user2(@RequestParam String userName) throws Exception {
        Future<User> future = executorService.submit(() -> findUser(userName));
        User user;
        try {
            user = future.get(2, TimeUnit.SECONDS);
        } catch (TimeoutException e) {
            //可以通过该方式取消线程执行
            future.cancel(true);
            throw e;
        }
        return user;
    }

    @CustomHystrix(timeout = 1)
    @GetMapping("/user-3")
    public User user3(@RequestParam String userName) throws Exception {
        return findUser(userName);
    }


    /////////////均为私有的方法


    private User findUser(String userName) {
        long start = System.currentTimeMillis();
        try {
            TimeUnit.SECONDS.sleep(RandomUtil.randomInt(1, 4));
        } catch (InterruptedException e) {
            //e.printStackTrace();
            //上面设置了可以被阻断，所以此时会发生该异常，而且该异常是合理的。
            //试想下，如果不设置，那么该线程一直阻塞，会存在很大问题。
            long end = System.currentTimeMillis();
            System.out.println("cost:" + (end - start));
            return errorUser();
        }
        User user = new User();
        user.setName(userName);
        return user;
    }

    private User errorUser() {
        User user = new User();
        user.setName("error");
        return user;
    }
}
