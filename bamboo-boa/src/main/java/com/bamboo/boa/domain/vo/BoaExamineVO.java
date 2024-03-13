package com.bamboo.boa.domain.vo;

import com.bamboo.common.annotation.Excel;
import lombok.Data;

@Data
public class BoaExamineVO {

    private Integer id;
    /**
     * 提交人
     */
    private String submitName;

    /** 审批类型`1：请假`，`2：报销`，`3：采购` */
    @Excel(name = "审批类型`1：请假`，`2：报销`，`3：采购`")
    private Long approvalType;

    /** 具体事项 */
    @Excel(name = "具体事项")
    private String message;

    /**
     * 审批状态
     */
    private Integer status;
}
