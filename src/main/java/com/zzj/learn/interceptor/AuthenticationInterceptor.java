package com.zzj.learn.interceptor;

import com.alibaba.fastjson.JSON;
import com.zzj.learn.constant.UserConstants;
import com.zzj.learn.domain.User;
import com.zzj.learn.service.LoginService;
import com.zzj.learn.utils.JSONResult;
import com.zzj.learn.utils.LoginRequired;
import com.zzj.learn.utils.TokenUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.SignatureException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthenticationInterceptor implements HandlerInterceptor {

    public final static String ACCESS_TOKEN = "Token";

    @Autowired
    private LoginService loginService;

    @Autowired
    private StringRedisTemplate redisService;

    // 在业务处理器处理请求之前被调用
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 判断接口是否需要登录
        LoginRequired methodAnnotation = method.getAnnotation(LoginRequired.class);

        // 有 @LoginRequired 注解，需要认证
        if (methodAnnotation != null) {
            // 判断是否存在令牌信息，如果存在，则允许登录
            String accessToken = request.getHeader(ACCESS_TOKEN);
            System.out.println("Token信息---->"+accessToken);

            if (null == accessToken) {
                throw new Exception("相应的状态码");
            } else {
                // 从Redis 中查看 token 是否过期
                long expire = redisService.getExpire(UserConstants.REDIS_USER + accessToken);
                if (expire <= 0 ){
                    //不存在该用户
                    response.setStatus(403);
                    throw new Exception("Token失效");
                }
                Claims claims;
                try{
                    claims = TokenUtil.parseJWT(accessToken);
                }catch (ExpiredJwtException e){
                    response.setStatus(401);
                    throw new Exception("相应的状态码");
                }catch (SignatureException se){
                    response.setStatus(401);
                    throw new Exception("相应的状态码");
                }catch (Exception ee){
                    response.setStatus(401);
                    throw new Exception("相应的状态码");
                }
                User user = new User();
                user.setUsername(claims.getId());
                // 根据用户名查找用户方法
                user = loginService.findUserByUname(user);
                if (user == null) {
                    response.setStatus(401);
                    throw new Exception("相应的状态码");
                }
                String userJson = redisService.opsForValue().get(UserConstants.REDIS_USER + accessToken);
                // 从Redis中获取用户信息
                User userA;
                userA= JSON.parseObject(userJson, User.class);
                if (userJson!=null && userJson!=""&&userA!=null){
                    // 当前登录用户@CurrentUser
                    request.setAttribute(UserConstants.CURRENT_USER, userA);
                }else {
                    // 用户信息有问题，不予登录
                    throw new Exception("相应的状态码");
                }
                return true;
            }

        } else {//不需要登录可请求
            return true;
        }
    }
    // 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    // 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }

}
