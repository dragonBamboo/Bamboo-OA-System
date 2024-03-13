package com.bamboo.boa.domain;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.bamboo.common.annotation.Excel;
import lombok.*;

import static com.bamboo.common.utils.DateUtils.YYYY_MM_DD_HH_MM_SS;

/**
 * 会议室信息对象 boa_conference_rooms
 * 
 * @author bamboo
 * @date 2024-03-07
 */
@TableName("boa_conference_rooms")
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoaConferenceRoomsDO
{
    private static final long serialVersionUID = 1L;

    /** ID */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 会议名 */
    @Excel(name = "会议名")
    private String meetingName;

    /** 会议室的详细位置信息 */
    @Excel(name = "会议室的详细位置信息")
    private String detailedLocation;

    /** 预定会议室的人员的ID */
    @Excel(name = "预定会议室的人员的ID")
    private Long bookingPerson;

    /** 会议室图片的URL地址，存储实际路径 */
    @Excel(name = "会议室图片的URL地址，存储实际路径")
    private String imageUrl;

    /** 会议的开始时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    @Excel(name = "会议的开始时间", width = 30, dateFormat = YYYY_MM_DD_HH_MM_SS)
    private Date meetingStartTime;

    /** 会议的结束时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    @Excel(name = "会议的结束时间", width = 30, dateFormat = YYYY_MM_DD_HH_MM_SS)
    private Date meetingEndTime;

    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date createTime;

    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date updateTime;

    /** 是否删除判断 */
    @Excel(name = "是否删除判断")
    @TableLogic
    private Integer isDeleted;
}
