package com.bamboo.boa.domain.vo;

import com.bamboo.boa.domain.BoaScheduleDO;
import lombok.Data;

@Data
public class BoaScheduleVO extends BoaScheduleDO {

    private Integer status;

    private String nickName;
}
