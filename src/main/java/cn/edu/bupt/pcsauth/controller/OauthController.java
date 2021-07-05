package cn.edu.bupt.pcsauth.controller;

import cn.edu.bupt.pcsauth.util.CommonResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
/**
 * @author arron
 * @date 19-5-24
 */
@RestController
@RequestMapping("/oauth")
public class OauthController {

    @Resource
    RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore redisToken() {
        return new RedisTokenStore(redisConnectionFactory);
    }

    Logger logger = LoggerFactory.getLogger(OauthController.class);

    @RequestMapping(value = "/principal")
    public Principal getUser(Principal principal) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>");
        logger.info(principal.getName());
        return principal;
    }


    @DeleteMapping(value = "/access-token")
    @ResponseStatus(HttpStatus.OK)
    public Object revokeToken(HttpServletRequest request) {

        String authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            String tokenValue = authHeader.replace("bearer", "").trim();
            OAuth2AccessToken accessToken = redisToken().readAccessToken(tokenValue);
            if (accessToken != null) {
                redisToken().removeAccessToken(accessToken);
            }
        }
            return new CommonResult().success("注销成功");
    }
}
