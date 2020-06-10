package com.weixin.wx_auth.common.pojo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;


@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Data
public class UserDetailDTO extends BaseDTO{
    private String gender;
    private String name;
    private String mobile;
    private String qr_code;
    private String position;
    private String avatar;
    private List<Integer> department;
    private String userid;
    private String email;
    private List<?> order;
}
