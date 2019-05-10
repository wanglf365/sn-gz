package com.sn.gz.pmp.dsc.definition.field.bo.element;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;
/**
 * 第一级选项类
 *
 * @author lufeiwang
 * 2019/4/17
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class FirstLevelOption extends ElementOption {
	private List<ElementOption> childOptions;
}
