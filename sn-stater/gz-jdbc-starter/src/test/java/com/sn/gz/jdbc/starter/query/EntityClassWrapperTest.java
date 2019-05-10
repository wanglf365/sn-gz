/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: TestEntityClassWrapper
 * Author:   hanson
 * Date:   2018/6/27
 */
package com.sn.gz.jdbc.starter.query;

import com.sn.gz.jdbc.starter.entity.BaseEntityTest;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试实体类包装
 *
 * @author hanson
 * 2018/6/27
 */
public class EntityClassWrapperTest {

    /**
     * 测试类字段和表字段的映射关系，尤其是对sql语句和多列名时
     */
    @Test
    void testFieldName(){
        EntityClassWrapper<BaseEntityTest> ecw = new EntityClassWrapper<>(BaseEntityTest.class);
        Assert.assertEquals("name='jobs'",ecw.getSqlColumns("testName='jobs'"));
        Assert.assertEquals("name='groupId'",ecw.getSqlColumns("testName='groupId'"));
        Assert.assertEquals("(name , group_id,create_datetime,modified_datetime)",
                ecw.getColumns("(testName , groupId,createDatetime,modifiedDatetime)"));
        Assert.assertEquals("name , group_id,create_datetime ,modified_datetime",
                ecw.getColumns("testName , groupId,createDatetime ,modifiedDatetime"));
        Map<String,Object> params = new HashMap<>();
        params.put("groupId",2);
        params.put("testName","name");
        ecw.where("testName={0} and groupId = {1}","jobs",23)
                .andNew("modifiedDatetime={0} and createDatetime = {1}","jobs",23)
                .allEq(params)
                .ne("groupId",345);
        //System.out.println(ecw.originalSql());
        Assert.assertTrue(!ecw.originalSql().contains("testName"));
        Assert.assertTrue(!ecw.originalSql().contains("groupId"));
        Assert.assertTrue(!ecw.originalSql().contains("createDatetime"));
        Assert.assertTrue(!ecw.originalSql().contains("modifiedDatetime"));
        Assert.assertTrue(ecw.originalSql().contains("name"));
        Assert.assertTrue(ecw.originalSql().contains("group_id"));
        Assert.assertTrue(ecw.originalSql().contains("create_datetime"));
        Assert.assertTrue(ecw.originalSql().contains("modified_datetime"));
    }
}
