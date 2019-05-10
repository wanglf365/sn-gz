package com.sn.gz.pmp.dsc.definition.field.bo.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * 百分比字段类
 *
 * @author lufeiwang
 * 2019/4/17
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class PercentageField extends FieldBase {
	private int length;
	private int decimalPlaces;
	private int roundMode;

}
