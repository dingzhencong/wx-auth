package com.weixin.wx_auth.common.pojo.dingxin.res;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoRes {
    private String departmentId;
    private String creatorId;
    private int pageSize;
    private List<?> sortTypes;
    private String employeeIdentityDocument;
    private String organizationId;
    private String modifyTime;
    private int pageNo;
    private List<?> sortNames;
    private String email;
    private String departmentName;
    private String employeeName;
    private int workYears;
    private String organizationName;
    private String mobile;
    private long updateTime;
    private String postId;
    private String employeeState;
    private String userId;
    private String lastLoginTime;
    private int sexCode;
    private long createTime;
    private String organizationCode;
    private String sortType;
    private String fullSpell;
    private String account;
}
