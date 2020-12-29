package cn.fenqing.aop.annotation.config;

import cn.fenqing.aop.annotation.annotation.PointcutFlag;
import cn.fenqing.aop.annotation.annotation.PointcutHandler;
import cn.fenqing.aop.annotation.aspect.AspectHandler;
import cn.fenqing.aop.annotation.constant.HandlerType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.function.Consumer;

/**
 * @author fenqing
 */
@Aspect
@Slf4j
public class Invoker {

    private final Map<String, Map<Type, List<AspectHandlerMethod>>> mapper;

    private final ExecutorService executorService;

    public Invoker(List<AspectHandler> handler, ExecutorService executorService) {
        this.executorService = executorService;
        mapper = new HashMap<>();
        for (AspectHandler obj : handler) {
            Class<?> clazz = obj.getClass();
            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                PointcutHandler pointcutHandler = method.getAnnotation(PointcutHandler.class);
                if(pointcutHandler != null){
                    String flag = pointcutHandler.flag();
                    boolean async = pointcutHandler.async();
                    HandlerType type = pointcutHandler.type();
                    Map<Type, List<AspectHandlerMethod>> aspectHandlerMethods = mapper.get(flag);
                    if(aspectHandlerMethods == null){
                        aspectHandlerMethods = new HashMap<>();
                    }
                    AspectHandlerMethod aspectHandlerMethod = new AspectHandlerMethod();
                    List<AspectHandlerMethod> a = aspectHandlerMethods.getOrDefault(Type.AFTER, new ArrayList<>());
                    List<AspectHandlerMethod> b = aspectHandlerMethods.getOrDefault(Type.BEFORE, new ArrayList<>());
                    List<AspectHandlerMethod> aa = aspectHandlerMethods.getOrDefault(Type.AFTER_ASYNC, new ArrayList<>());
                    List<AspectHandlerMethod> ba = aspectHandlerMethods.getOrDefault(Type.BEFORE_ASYNC, new ArrayList<>());
                    aspectHandlerMethods.put(Type.AFTER, a);
                    aspectHandlerMethods.put(Type.BEFORE, b);
                    aspectHandlerMethods.put(Type.AFTER_ASYNC, aa);
                    aspectHandlerMethods.put(Type.BEFORE_ASYNC, ba);
                    aspectHandlerMethod.setObject(obj);
                    aspectHandlerMethod.setMethod(method);
                    if(type == HandlerType.AFTER){
                        if(async){
                            aa.add(aspectHandlerMethod);
                        }else{
                            a.add(aspectHandlerMethod);
                        }
                    }else if(type == HandlerType.BEFORE){
                        if(async){
                            ba.add(aspectHandlerMethod);
                        }else{
                            b.add(aspectHandlerMethod);
                        }
                    }
                    mapper.put(flag, aspectHandlerMethods);
                }
            }
        }
    }

    @Pointcut("@annotation(cn.fenqing.aop.annotation.annotation.PointcutFlag)")
    public void point(){}

    @Around("point()")
    public Object invoker(ProceedingJoinPoint pjp){
        Object[] args = pjp.getArgs();
        Signature signature = pjp.getSignature();
        MethodSignature methodSignature = (MethodSignature)signature;
        Method targetMethod = methodSignature.getMethod();
        PointcutFlag pointcutFlag = targetMethod.getAnnotation(PointcutFlag.class);
        List<AspectHandlerMethod> a = new ArrayList<>();
        List<AspectHandlerMethod> b = new ArrayList<>();
        List<AspectHandlerMethod> aa = new ArrayList<>();
        List<AspectHandlerMethod> ba = new ArrayList<>();
        if(pointcutFlag != null){
            String flag = pointcutFlag.flag();
            Map<Type, List<AspectHandlerMethod>> typeListMap = mapper.get(flag);
            a = typeListMap.getOrDefault(Type.AFTER, new ArrayList<>());
            b = typeListMap.getOrDefault(Type.BEFORE, new ArrayList<>());
            aa = typeListMap.getOrDefault(Type.AFTER_ASYNC, new ArrayList<>());
            ba = typeListMap.getOrDefault(Type.BEFORE_ASYNC, new ArrayList<>());
        }
        Consumer<AspectHandlerMethod> consumer = aspectHandlerMethod -> {
            Method method = aspectHandlerMethod.getMethod();
            Object object = aspectHandlerMethod.getObject();
            try {
                method.invoke(object, args);
            } catch (Exception e) {
                throw new RuntimeException("调用失败！" + method.toGenericString());
            }
        };
        Consumer<AspectHandlerMethod> consumerAsync = aspectHandlerMethod -> {
            Method method = aspectHandlerMethod.getMethod();
            Object object = aspectHandlerMethod.getObject();
            executorService.execute(() -> {
                try {
                    method.invoke(object, args);
                } catch (Exception e) {
                    throw new RuntimeException("调用失败！" + method.toGenericString());
                }
            });
        };
        a.forEach(consumer);
        aa.forEach(consumerAsync);
        Object proceed;
        try {
            proceed = pjp.proceed();
        } catch (Throwable throwable) {
            throw new RuntimeException("执行失败");
        }
        b.forEach(consumer);
        ba.forEach(consumerAsync);
        return proceed;
    }

    @Data
    private static class AspectHandlerMethod{
        private Object object;
        private Method method;
    }

}

enum Type{
    /**
     * 类型
     */
    BEFORE, AFTER,  BEFORE_ASYNC, AFTER_ASYNC
}