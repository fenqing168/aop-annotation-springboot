package cn.fenqing.aop.annotation.config;

import cn.fenqing.aop.annotation.aspect.AspectHandler;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.*;

/**
 * @author fenqing
 */
@Configuration
@ConditionalOnBean(AspectHandler.class)
public class AopAnnotationFlagAutoConfiguration {

    @Bean
    public Invoker invoker(@Autowired List<AspectHandler> aspectHandlers,
                           @Autowired
                           @Qualifier("aspectHandlerAsyncThreadPool") ExecutorService aspectHandlerAsyncThreadPool){
        return new Invoker(aspectHandlers, aspectHandlerAsyncThreadPool);
    }

    @Bean
    public ExecutorService aspectHandlerAsyncThreadPool(){
        ThreadFactory namedThreadFactory = new ThreadFactoryBuilder()
                .setNameFormat("demo-pool-%d").build();
        return new ThreadPoolExecutor(5, 200,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(1024), namedThreadFactory, new ThreadPoolExecutor.AbortPolicy());
    }

}
