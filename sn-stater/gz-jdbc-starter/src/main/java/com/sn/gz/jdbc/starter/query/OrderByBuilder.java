/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: OrderByBuilder
 * Author:   xiaoxueliang
 * Date:     2018/7/7 10:08
 * @since 1.0.0
 */
package com.sn.gz.jdbc.starter.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 排序组装类
 * @author 肖学良<br>
 * Version: 1.0<br>
 * Date: 2015年12月24日
 */
public class OrderByBuilder implements Serializable {


	private List<OrderBy> orderByList = new ArrayList<>();
	
	/**
	 * 添加排序
	 * @author 肖学良<br>
	 * Date: 2015年12月24日
	 */
	public OrderByBuilder add(OrderBy orderBy){
		orderByList.add(orderBy);
		return this;
	}
	/**
	 * 返回排序
	 * @author 肖学良<br>
	 * Date: 2015年12月24日
	 */
	public List<OrderBy> build(){
		return orderByList;
	}
}
