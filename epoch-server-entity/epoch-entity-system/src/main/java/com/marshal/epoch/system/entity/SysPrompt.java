package com.marshal.epoch.system.entity;

import lombok.Data;;
import javax.persistence.Id;

import org.hibernate.validator.constraints.Length;

import javax.persistence.Table;

import com.marshal.epoch.core.dto.BaseDto;

@Data
@Table(name = "sys_prompt")
public class SysPrompt extends BaseDto {

    /**
     * 主键
     */
    @Id
    private Long promptId;

    /**
     * 描述编码
     */
    @Length(max = 255)
    private String promptCode;

    /**
     * 语言
     */
    @Length(max = 10)
    private String lang;

    /**
     * 描述
     */
    @Length(max = 240)
    private String description;

}
