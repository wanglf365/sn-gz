package com.sn.gz.pmp.dsc.definition.field.bo.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
/**
 * 多选字段类
 *
 * @author lufeiwang
 * 2019/4/17
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class SelectManyField extends FieldBase {
    private List<ElementOption> options;

}
