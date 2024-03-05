package com.bamboo.boa.domain;


import com.bamboo.common.core.domain.BaseEntity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.*;

@TableName("boa_test")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BoaTestDO extends BaseEntity {
    @TableId(type = IdType.AUTO)
    private Integer id;

    private String name;
}
