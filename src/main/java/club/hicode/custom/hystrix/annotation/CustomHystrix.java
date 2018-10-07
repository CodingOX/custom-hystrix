package club.hicode.custom.hystrix.annotation;

import java.lang.annotation.*;

/**
 * XXXX
 *
 * @author LiuChunfu
 * @date 2018/10/7
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CustomHystrix {

    int timeout() default 3;
}
