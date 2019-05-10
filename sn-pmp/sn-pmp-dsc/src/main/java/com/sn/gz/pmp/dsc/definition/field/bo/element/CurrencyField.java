package com.sn.gz.pmp.dsc.definition.field.bo.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
/**
 * 金额字段
 *
 * @author lufeiwang
 * 2019/4/17
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class CurrencyField extends FieldBase {

    private int length;
    private int decimalPlaces;
    private int roundMode;
    private String currencyUnit;

}