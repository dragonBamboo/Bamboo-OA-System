package com.bamboo.boa.domain.vo;

import com.bamboo.common.annotation.Excel;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.util.Date;

import static com.bamboo.common.utils.DateUtils.YYYY_MM_DD_HH_MM_SS;

@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoaConferenceRoomsVO {
    /** ID */
    private Long id;

    /** 会议名 */
    private String meetingName;

    /** 会议室的详细位置信息 */
    private String detailedLocation;

    /** 预定会议室的人员的ID */
    private Long bookingPerson;

    private String bookingNickName;

    private Integer status;

    /** 会议室图片的URL地址，存储实际路径 */
    private String imageUrl;

    /** 会议的开始时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date meetingStartTime;

    /** 会议的结束时间 */
    @JsonFormat(pattern = YYYY_MM_DD_HH_MM_SS)
    private Date meetingEndTime;
}
