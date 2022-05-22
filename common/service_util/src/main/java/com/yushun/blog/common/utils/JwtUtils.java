package com.yushun.blog.common.utils;

import com.yushun.blog.common.exception.MyException;
import com.yushun.blog.model.user.User;
import io.jsonwebtoken.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author yushun zeng
 * @since 2022-5-21
 */

public class JwtUtils {
    public static String token = "token";

    //密钥
    public static String jwt_secret = "990415zys@gmail.com";

    //过期时间
    public static long jwt_expr = 3600 * 24 * 1000;

    //1、生成token
    public static String sign(User user){
        // 1、指定签名的时候使用的签名算法
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        //2、生成签发时间
        long nowMillis=System.currentTimeMillis();
        Date date = new Date(nowMillis);

        //3、创建playLoad的私有声明
        Map<String,Object> claim = new HashMap<>();
        claim.put("id",user.getId());
        claim.put("userName",user.getUserName());

        //4、生成签发人
        String subject = user.getUserName();

        JwtBuilder builder = Jwts.builder()
                .setClaims(claim)
                .setId(UUID.randomUUID().toString())
                .setIssuedAt(date)
                .setSubject(subject)
                .signWith(signatureAlgorithm, jwt_secret);

        //设置过期时间
        Date exprDate = new Date(nowMillis + jwt_expr);
        builder.setExpiration(exprDate);

        return builder.compact();
    }

    //2、check token
    public static boolean verify(String token){
        try {
            if (StringUtils.isEmpty(token)){
                return false;
            }
            Jwts.parser().setSigningKey(jwt_secret).parseClaimsJws(token).getBody();
            return true;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    //3、get user information
    public static User getUser(String token){
        try {
            if (StringUtils.isEmpty(token)) {
                throw new MyException("token can not be null");
            }
            if (verify(token)){
                Claims claims= Jwts.parser().setSigningKey(jwt_secret).parseClaimsJws(token).getBody();
                User user=new User();
                user.setId(Long.parseLong(claims.get("id") + ""));
                user.setUserName(claims.get("userName") + "");
                return user;
            }else {
                throw new MyException("token not be found");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("token not be found");
        }
    }
}
