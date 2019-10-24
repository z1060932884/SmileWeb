package com.zzj.learn.filter;


import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 加解密过滤器
 */

// 注入spring容器
@Component
// 定义filterName 和过滤的url
@WebFilter(filterName = "CryptologyFilter" ,urlPatterns = "/*")
public class CryptologyFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        WrapperedRequest wrapperedRequest = new WrapperedRequest((HttpServletRequest) servletRequest);
        // do something 处理request 或response
        WrapperedResponse wrapResponse = new WrapperedResponse((HttpServletResponse) servletResponse);
        filterChain.doFilter(servletRequest,wrapResponse);
        System.out.println("filter1"+wrapResponse.getContent().toString());
        System.out.println("当前时间--->"+System.currentTimeMillis());
        String content = wrapResponse.getContent();
        String responseBodyMw = sm2EncUtils(content);
        System.out.println("加密完成--->"+System.currentTimeMillis());
        System.out.println("【加密返回数据为】 responseBodyMw = {}"+responseBodyMw);
        servletResponse.setContentLength(-1);
        PrintWriter out = servletResponse.getWriter();
        out.write(responseBodyMw);
        out.flush();
        out.close();

    }

    @Override
    public void destroy() {

    }

    public static String sm2EncUtils(String data) {
        String privateKeyStr = "MIICdQIBADANBgkqhkiG9w0BAQEFAASCAl8wggJbAgEAAoGBAK3nXZSpYv0JRNFDT3XG0jURw0itaylhma/w8k9LSbtpNqtUG1lvi8mke9UCY+xkn5vXY9+Bk565s4QcPYwQdbb8cT7CXPWgW0cQCYkAmIuyekoh55bVtXTa9zW0nqpxXJs82qNcTpYs7e0mdLuxZIn0YeJonWJJChOOaGaRkBubAgMBAAECgYAqOQqXlajbFu0GgflA900CZZWsh66FFZVjCnVKm1UDk8AaSQl65YJjKvSF+1aXhrbZ96ngEm3tE9lqMhEfeL+bjsmZnc6uxYwANzv+wh54Uf5AVso9uXbMXlMe1MNaR7oZkhI+du6VdLSu0n1WlRrmcg+zliDkLrKfYclzcahIwQJBAPBzNS1LkmtPfi0vRi/MZfF3ChMvEVeCGyCWvb0lJ5bPnkezKDCxWBcfWRozt+qgz6Q/fgv+LXm1+6CqMgCFkX8CQQC5JnH5U5kyTG6cH8uplg0I2sxljp5nFs/tnF0RkTVzYp9R5CiFXwGKH91st8t95FzKkLuILoy5VrLScYo8a4vlAkBJuhmhFN4Fd29p7Wfo+hR8EJMPRMxdd7BXssDlAUJ9VJXkyENXgtlO5bbNePQ4xixE4Y8FoF9TRYCtR+JjFJGDAkAvsCdLAK1Et0sGC2p5k5xn23Mp9UH3a3jCyrNuAuixf4VpokqNj5rl6K8vgWd4VYlQ41ZqDRNR6XLFoVjplwnBAkAN2gcitZZBvS9XE7woaFohUL8QR3MHuAlbmhwTxRCt+01vDvpUlMj1MyvkG3h/uL/uR2aVTV+Cpu72I04Trag1";
        String publicKeyStr = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCt512UqWL9CUTRQ091xtI1EcNIrWspYZmv8PJPS0m7aTarVBtZb4vJpHvVAmPsZJ+b12PfgZOeubOEHD2MEHW2/HE+wlz1oFtHEAmJAJiLsnpKIeeW1bV02vc1tJ6qcVybPNqjXE6WLO3tJnS7sWSJ9GHiaJ1iSQoTjmhmkZAbmwIDAQAB";
        RSA rsa = new RSA(privateKeyStr,publicKeyStr);
        //私钥加密，公钥解密
        byte[] encrypt2 = rsa.encrypt(StrUtil.bytes(data, CharsetUtil.CHARSET_UTF_8), KeyType.PrivateKey);
        byte[] decrypt2 = rsa.decrypt(encrypt2, KeyType.PublicKey);
        System.out.println("加密--->" + HexUtil.encodeHexStr(encrypt2));
        System.out.println("解密--->" + StrUtil.str(decrypt2, CharsetUtil.CHARSET_UTF_8));
        return HexUtil.encodeHexStr(encrypt2);
    }
}
