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
 * 员工反馈对象 boa_emp_feedback
 * 
 * @author bamboo
 * @date 2024-03-13
 */
@TableName("boa_emp_feedback")
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoaEmpFeedbackDO
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 员工ID */
    @Excel(name = "员工ID")
    private Long userId;

    /** 反馈类别 */
    @Excel(name = "反馈类别")
    private Integer category;

    /** 反馈内容 */
    @Excel(name = "反馈内容")
    private String feedbackText;

    /** 满意度评分 */
    @Excel(name = "满意度评分")
    private Integer satisfaction;

    /** 删除标记，0表示未删除，1表示已删除 */
    @Excel(name = "删除标记，0表示未删除，1表示已删除")
    @TableLogic
    private Integer isDeleted;

    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date createTime;

    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date updateTime;
}
