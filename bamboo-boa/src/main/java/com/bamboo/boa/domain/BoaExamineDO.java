package com.bamboo.boa.domain;

import com.bamboo.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

import static com.bamboo.common.utils.DateUtils.YYYY_MM_DD_HH_MM_SS;

/**
 * 审批管理对象 boa_examine
 * 
 * @author bamboo
 * @date 2024-03-11
 */
@TableName("boa_examine")
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoaExamineDO
{
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 提交人id */
    @Excel(name = "提交人id")
    private Long submitterId;

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

    @TableLogic
    private Integer isDeleted;

    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date createTime;

    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date updateTime;
}
