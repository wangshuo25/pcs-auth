package cn.edu.bupt.pcsauth.controller;

import cn.edu.bupt.pcsauth.util.CommonResult;
import cn.edu.bupt.pcsauth.dto.InfoDTO;
import cn.edu.bupt.pcsauth.service.PrivilegeService;
import cn.edu.bupt.pcsmavenjpa.entity.TesteeEntity;
import cn.edu.bupt.pcsmavenoauth2.pojo.CounselorPOJO;
import cn.edu.bupt.pcsmavenoauth2.util.Oauth2User;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 提供用户的角色名，
 * 根据角色提供权限表
 *
 * @author arron
 * @date 19-6-2
 * @description
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    PrivilegeService privilegeService;


    /**
     * 提供用户的角色名
     * @author arron
     * @date 19-6-2
     * @param
     * @return java.lang.Object
     */
    @GetMapping(value = "/info")
    public Object getInfo() {
        InfoDTO infoDTO = privilegeService.getInfo();
        return new CommonResult().success(infoDTO);
    }



    @GetMapping(value = "/permission")
    public Object getPermission() {
        Object infoDTO = privilegeService.getPrivilegeTree();
        return new CommonResult().success(infoDTO);
    }

    @GetMapping(value = "/getLoginUserInfo")
    public Object test(OAuth2Authentication oAuth2Authentication) {
        CounselorPOJO counselor = (CounselorPOJO) Oauth2User.getUser(oAuth2Authentication);

        TesteeEntity testeeEntity  = (TesteeEntity)oAuth2Authentication.getUserAuthentication().getPrincipal();

        return testeeEntity;
    }

}