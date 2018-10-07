package club.hicode.custom.hystrix;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class CustomHystrixApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomHystrixApplication.class, args);
    }
}
