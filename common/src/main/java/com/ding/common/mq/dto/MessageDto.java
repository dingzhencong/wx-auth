package com.ding.common.mq.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

/**
 * 消息通道实体
 *
 * @author tianjiake
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDto {

    /** 全局流水号 */
    private String workflowNo;

    /** 创建时间 */
    private Timestamp createTime;

    /** 创建用户ID */
    private String createUserId;

    /** 创建用户姓名 */
    private String createUserName;

    /** 消息开始时间 */
    private Timestamp strtTm;

    /** 消息结束时间 */
    private Timestamp endTm;

    /** 消息标题 */
    private String msgTitle;

    /** 消息文本 */
    private String msgCntnt;

    /** 系统号 */
    private String systemNo;

    /** 系统名称 */
    private String systemName;

    /** 消息等级 */
    private Integer msgLvl;

    /** PC消息地址 */
    private String pcMsgUrl;

    /** APP消息地址 */
    private String appMsgUrl;

    /** 可见人员列表 */
    private List<MessageUserDto> userList;

}
