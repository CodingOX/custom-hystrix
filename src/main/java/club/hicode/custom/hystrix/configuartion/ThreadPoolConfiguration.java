package club.hicode.custom.hystrix.configuartion;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * XXXX
 *
 * @author LiuChunfu
 * @date 2018/10/7
 */
@Configuration
public class ThreadPoolConfiguration {

    @Bean
    public ExecutorService executor() {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        return pool;
    }
}
