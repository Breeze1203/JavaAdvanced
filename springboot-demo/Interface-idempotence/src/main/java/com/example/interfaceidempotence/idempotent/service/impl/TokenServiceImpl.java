package com.example.interfaceidempotence.idempotent.service.impl;


import com.example.interfaceidempotence.idempotent.common.ResponseCode;
import com.example.interfaceidempotence.idempotent.common.ServerResponse;
import com.example.interfaceidempotence.idempotent.common.ServiceException;
import com.example.interfaceidempotence.idempotent.service.TokenService;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;



import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * token业务处理，提供token创建、token验证接口
 * Created by double on 2019/7/11.
 */
@Service
public class TokenServiceImpl implements TokenService {

    private static final String TOKEN_NAME = "token";

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Override
    public ServerResponse createToken() {
        //通过UUID来生成token
        String tokenValue = "idempotent:token:" + UUID.randomUUID().toString();
        //将token放入redis中，设置有效期为60S
        stringRedisTemplate.opsForValue().set(tokenValue, "0", 60, TimeUnit.SECONDS);
        return ServerResponse.success(tokenValue);
    }

    /**
     * @param request
     */
    @Override
    public void checkToken(HttpServletRequest request) {
        String token = request.getHeader(TOKEN_NAME);
        if (StringUtils.isBlank(token)) {
            token = request.getParameter(TOKEN_NAME);
            if (StringUtils.isBlank(token)) {
                //没有携带token，抛异常，这里的异常需要全局捕获
                throw new ServiceException(ResponseCode.ILLEGAL_ARGUMENT.getMsg());
            }
        }
        /*
        先生成token，拿到token去访问需要幂等性的接口，因为第一次访问，里面有token，不执行下面方法，直接走删除token
        若重复访问，之前token删除掉了，redis里面没有token返回false,！false为true，走下面方法，或者访问的是不是生成的token
        也为true，走下面逻辑
         */
        //token不存在，说明token已经被其他请求删除或者是非法的token
        if (!stringRedisTemplate.hasKey(token)) {
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
        boolean del = stringRedisTemplate.delete(token);
        if (!del) {
            //token删除失败，说明token已经被其他请求删除
            throw new ServiceException(ResponseCode.REPETITIVE_OPERATION.getMsg());
        }
    }

}