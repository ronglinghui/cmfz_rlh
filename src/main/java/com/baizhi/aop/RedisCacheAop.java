package com.baizhi.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

//创建springboot基于aop做缓存
@Component//交给工厂管理
@Aspect//声明这是一个切面
public class RedisCacheAop {
    @Autowired//注入一个redis封装的一个类
    private RedisTemplate redisTemplate;
    @Autowired//注入另一个类用于设置格式
    private StringRedisTemplate stringRedisTemplate;

    //创建环绕通知   添加缓存  + 查询缓存
    @Around("@annotation(com.baizhi.annotation.AddCache)")
    //这里的参数是换取方法名  和参数的
    public Object around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        //用于设置格式  方便在redis上查看
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //设置key格式
        redisTemplate.setKeySerializer(stringRedisSerializer);
        //获取hash类型
        HashOperations hashOperations = redisTemplate.opsForHash();
        //获取key namespace  key方法名+参数  value值
        String namespace = proceedingJoinPoint.getTarget().getClass().getName();
        String menth = proceedingJoinPoint.getSignature().getName();
        Object[] args = proceedingJoinPoint.getArgs();
        //创建stringbuilder  用于拼接
        StringBuilder stringBuilder = new StringBuilder();
        //先拼接方法名
        stringBuilder.append(menth);
        //遍历参数
        for (Object arg : args) {
            stringBuilder.append(arg.toString());
        }
        //判断缓存是否存在  有就直接返回
        //判断缓存中的key是否存在
        if (hashOperations.hasKey(namespace, stringBuilder)) {
            System.out.println("获取缓存");
            //获取值
            Object o = hashOperations.get(namespace, stringBuilder);
            return o;
        }
        //查询数据库
        System.out.println("添加缓存");
        Object proceed = proceedingJoinPoint.proceed();
        //添加缓存
        hashOperations.put(namespace, stringBuilder, proceed);
        //返回数据
        return proceed;
    }

    @After("@annotation(com.baizhi.annotation.ClearCache)")  //清除缓存
    public void after(JoinPoint joinPoint) {
        //获取key
        String name = joinPoint.getTarget().getClass().getName();
        System.out.println("清除缓存");
        /*redisTemplate.delete(name);*/
        stringRedisTemplate.delete(name);
    }

}
