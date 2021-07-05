package cn.edu.bupt.pcsauth.dto;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author arron
 * @date 19-6-2
 * @description 信息DTO
 */
@Data
public class InfoDTO {
    ArrayList< String > roles;
    String trueName;
    String OrgName;
    String logoImage;

    Integer userId;

}
