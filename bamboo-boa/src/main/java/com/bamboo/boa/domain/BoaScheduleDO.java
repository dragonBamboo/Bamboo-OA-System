package com.bamboo.boa.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.bamboo.common.annotation.Excel;
import lombok.*;

import static com.bamboo.common.utils.DateUtils.YYYY_MM_DD_HH_MM_SS;

/**
 * 【请填写功能名称】对象 boa_schedule
 * 
 * @author bamboo
 * @date 2024-03-05
 */
@TableName("boa_schedule")
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoaScheduleDO
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 事件标题 */
    @Excel(name = "事件标题")
    private String title;

    /** 事件详情 */
    @Excel(name = "事件详情")
    private String description;

    /** 事件地点 */
    @Excel(name = "事件地点")
    private String location;

    /** 事件开始时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    @Excel(name = "事件开始时间", width = 30, dateFormat = YYYY_MM_DD_HH_MM_SS)
    private Date startTime;

    /** 事件结束时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    @Excel(name = "事件结束时间", width = 30, dateFormat = YYYY_MM_DD_HH_MM_SS)
    private Date endTime;

    /** 提交人id */
    @Excel(name = "提交人id")
    private Long submitterId;



    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date createTime;

    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date updateTime;

    /** 是否删除判断 */
    @Excel(name = "是否删除判断")
    @TableLogic
    private Integer isDeleted;
}
