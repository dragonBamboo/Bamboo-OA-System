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
 * 审批对象 boa_approve
 * 
 * @author bamboo
 * @date 2024-03-11
 */
@TableName("boa_approve")
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoaApproveDO
{
    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.AUTO)
    private Long id;

    /** 提交人 */
    @Excel(name = "提交人")
    private Integer examineId;

    /** 审批状态 */
    @Excel(name = "审批状态")
    private Integer status;

    /** 类型与详情 */
    @Excel(name = "类型与详情")
    private String msg;

    @TableLogic
    private Integer isDeleted;

    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date createTime;

    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date updateTime;

}
