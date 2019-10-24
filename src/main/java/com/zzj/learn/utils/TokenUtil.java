package com.zzj.learn.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.Key;
import java.util.Date;

public class TokenUtil {

    /**
     * 签名秘钥,可以换成 秘钥 注入
     */
    public static final String SECRET = "smile";
    /**
     * 签发地
     */
    public static final String issuer  = "smile.com";
    /**
     * 过期时间
     */
    public static final long ttlMillis = 10*1000*60;

    /**
     * 生成token
     *
     * @param id 一般传入userName
     * @return
     */
    public static String createJwtToken(String id,String subject) {
        return createJwtToken(id, issuer, subject, ttlMillis);
    }
    public static String createJwtToken(String id) {
        return createJwtToken(id, issuer, "", ttlMillis);
    }

    /**
     * 生成Token
     *
     * @param id        编号
     * @param issuer    该JWT的签发者，是否使用是可选的
     * @param subject   该JWT所面向的用户，是否使用是可选的；
     * @param ttlMillis 签发时间 （有效时间，过期会报错）
     * @return token String
     */
    public static String createJwtToken(String id, String issuer, String subject, long ttlMillis) {

        // 签名算法 ，将对token进行签名
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成签发时间
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        // 通过秘钥签名JWT
        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(SECRET);
        Key signingKey = new SecretKeySpec(apiKeySecretBytes, signatureAlgorithm.getJcaName());

        // 让我们设置JWT声明
        JwtBuilder builder = Jwts.builder().setId(id)
                .setIssuedAt(now)
                .setSubject(subject)
                .setIssuer(issuer)
                .signWith(signatureAlgorithm, signingKey);

        // if it has been specified, let's add the expiration
        if (ttlMillis >= 0) {
            long expMillis = nowMillis + ttlMillis;
            Date exp = new Date(expMillis);
            builder.setExpiration(exp);
        }

        // 构建JWT并将其序列化为一个紧凑的url安全字符串
        return builder.compact();

    }

    /**
     * Token解析方法
     * @param jwt Token
     * @return
     */
    public static Claims parseJWT(String jwt) {
        // 如果这行代码不是签名的JWS(如预期)，那么它将抛出异常
        Claims claims = Jwts.parser()
                .setSigningKey(DatatypeConverter.parseBase64Binary(SECRET))
                .parseClaimsJws(jwt).getBody();
        return claims;
    }

    public static void main(String[] args) {

        String token = TokenUtil.createJwtToken("1","ltz");

        System.out.println(TokenUtil.createJwtToken("1","ltz"));

        Claims claims = TokenUtil.parseJWT(token);

        System.out.println(claims);

    }

}
