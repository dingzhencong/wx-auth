package com.weixin.wx_auth.controller;

/**
 * @author hp
 */

public enum NodesEnum {
    /**
     * 审核节点信息
     */
    APPROVAL("Task_3", "审核","Pro4022667306379"),
    /**
     * 复核节点信息
     */
    REVIEW("Task_2", "复核","Pro4022667306379"),
    /**
     * 录入节点信息
     */
    ENTRY("Task_1", "录入","Pro4022667306379");
    private String nodeId;
    private String nodeName;
    private String processId;

    NodesEnum(String nodeId, String nodeName, String processId) {
        this.nodeId = nodeId;
        this.nodeName = nodeName;
        this.processId = processId;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getNodeName() {
        return nodeName;
    }

    public String getProcessId() {
        return processId;
    }
}
