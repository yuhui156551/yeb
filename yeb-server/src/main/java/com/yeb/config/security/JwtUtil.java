package com.yeb.config.security;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yuhui
 * @date 2023/4/2 21:43
 */
@Component
public class JwtUtil {
    private static final String CLAIM_KEY_USERNAME = "sub";
    private static final String CLAIM_KEY_CREATED = "created";

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Long expiration;

    // 可供外界调用的方法：public 修饰的方法
    
    /**
     * 根据用户信息生成 token，用户信息从 UserDetails 获取
     *
     * @param userDetails
     * @return
     */
    public String generateToken(UserDetails userDetails) {
        HashMap<String, Object> claims = new HashMap<>();// 荷载，存放用户信息
        claims.put(CLAIM_KEY_USERNAME, userDetails.getUsername());// 用户名
        claims.put(CLAIM_KEY_CREATED, new Date());// 创建时间
        return generateToken(claims);
    }

    /**
     * 从 token 中获取用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        String username = null;
        try {
            Claims claims = getClaimsFormToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return username;
    }

    /**
     * 从 token 中获取荷载
     *
     * @param token
     * @return
     */
    private Claims getClaimsFormToken(String token) {
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * 验证 token 是否有效
     *
     * @param token
     * @param userDetails
     * @return
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        String username = getUsernameFromToken(token); // 从 token 中获取 用户名
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token); // 本类内新建方法
    }
    
    /**
     * 刷新 token
     *
     * @param token
     * @return
     */
    public String refreshToken(String token) {
        Claims claims = getClaimsFormToken(token);
        claims.put(CLAIM_KEY_CREATED, new Date());
        return generateToken(claims);
    }

    /**
     * 判断 token 是否失效
     *
     * @param token
     * @return
     */
    private boolean isTokenExpired(String token) {
        Date expireDate = getExpiredDateFromToken(token);// 获取 token 失效时间
        return expireDate.before(new Date());// 如果 token 过期时间在当前时间前面，有效
    }

    /**
     * 从 token 中获取过期时间
     *
     * @param token
     * @return
     */
    private Date getExpiredDateFromToken(String token) {
        Claims claims = getClaimsFormToken(token);// 根据 token 获取荷载
        return claims.getExpiration();// 从荷载中获取过期时间
    }

    /**
     * 根据荷载生成 token
     *
     * @param claims
     * @return
     */
    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())// 失效时间
                .signWith(SignatureAlgorithm.HS512, secret)// 签名
                .compact();
    }

    /**
     * 生成 token 失效时间
     *
     * @return
     */
    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + expiration * 1000);
    }
}
