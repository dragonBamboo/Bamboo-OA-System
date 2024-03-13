package com.bamboo.boa.domain.vo;

import com.bamboo.boa.domain.BoaApproveDO;
import lombok.*;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class BoaApproveVO extends BoaApproveDO {
    private String submitName;
}
