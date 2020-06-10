package com.ding.common.mq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户对象实体
 * @author tianjiake
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageUserDto {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 所属机构ID
     */
    private String orgId;

    /**
     * 所属机构名称
     */
    private String orgName;

    /**
     * 所属部门ID
     */
    private String deptId;

    /**
     * 所属部门名称
     */
    private String deptName;
}
