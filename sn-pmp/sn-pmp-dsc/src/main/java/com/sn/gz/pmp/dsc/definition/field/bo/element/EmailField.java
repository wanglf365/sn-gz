package com.sn.gz.pmp.dsc.definition.field.bo.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * 邮件字段类
 *
 * @author lufeiwang
 * 2019/4/17
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class EmailField extends FieldBase {
    private int maxLength;
    private String pattern;

}
