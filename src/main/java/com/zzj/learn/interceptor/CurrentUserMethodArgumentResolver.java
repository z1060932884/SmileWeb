package com.zzj.learn.interceptor;

import com.zzj.learn.constant.UserConstants;
import com.zzj.learn.domain.User;
import com.zzj.learn.utils.CurrentUser;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
/**
 *
 * @Description: 自定义参数解析器
 * 增加方法注入，将含有 @CurrentUser 注解的方法参数注入当前登录用户
 */

public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    /**
     * supportsParameter：用于判定是否需要处理该参数分解，返回true为需要，并会去调用下面的方法resolveArgument。
     * @param parameter
     * @return
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        System.out.println("----------supportsParameter-----------" + parameter.getParameterType());
        //判断是否能转成User 类型
        return parameter.getParameterType().isAssignableFrom(User.class)
                //是否有CurrentUser注解
                && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    /**
     * resolveArgument：真正用于处理参数分解的方法，返回的Object就是controller方法上的形参对象。
     * @param parameter
     * @param mavContainer
     * @param webRequest
     * @param binderFactory
     * @return
     * @throws Exception
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        System.out.println("--------------resolveArgument-------------" + parameter);
        User user = (User) webRequest.getAttribute(UserConstants.CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
        if (user != null) {
            return user;
        }
        throw new MissingServletRequestPartException(UserConstants.CURRENT_USER);
    }

}
