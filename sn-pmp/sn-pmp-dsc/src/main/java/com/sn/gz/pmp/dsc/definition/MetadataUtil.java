package com.sn.gz.pmp.dsc.definition;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sn.gz.core.BusinessException;
import com.sn.gz.pmp.dsc.constants.DefinitionConstants;
import com.sn.gz.pmp.dsc.definition.field.FieldType;
import com.sn.gz.pmp.dsc.definition.field.bo.element.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * 元数据操作工具类
 *
 * @author luffy
 * @date 2017年6月16日 上午10:49:43
 */
@Slf4j
public class MetadataUtil {

    /**
     * 根据元素类型json转Object
     *
     * @param dataType 字段类型
     * @param json     json
     * @return FieldBase
     */
    private static FieldBase json2FieldDefinition(String dataType, String json) {
        FieldType type = FieldType.fromString(dataType);
        if (null == type) {
            return null;
        }
        FieldBase field;
        switch (type) {
            case Text: {
                field = JSON.parseObject(json, TextField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case LongText: {
                field = JSON.parseObject(json, LongTextField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case SelectOne: {
                field = JSON.parseObject(json, SelectOneField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case SelectMany: {
                field = JSON.parseObject(json, SelectManyField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case MutiLevelSelectOne: {
                field = JSON.parseObject(json, MutiLevelSelectOneField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case Boolean: {
                field = JSON.parseObject(json, BooleanField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case Currency: {
                field = JSON.parseObject(json, CurrencyField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case Date: {
                field = JSON.parseObject(json, DateField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case Datetime: {
                field = JSON.parseObject(json, DatetimeField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case Email: {
                field = JSON.parseObject(json, EmailField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case File: {
                field = JSON.parseObject(json, FileAttachmentField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case Image: {
                field = JSON.parseObject(json, ImageField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case Number: {
                field = JSON.parseObject(json, NumberField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case Percentage: {
                field = JSON.parseObject(json, PercentageField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case Phone: {
                field = JSON.parseObject(json, PhoneField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case Time: {
                field = JSON.parseObject(json, TimeField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case Long: {
                field = JSON.parseObject(json, LongField.class);
                field.setDataType(type.getValue());
                return field;
            }
            case URL: {
                field = JSON.parseObject(json, URLField.class);
                field.setDataType(type.getValue());
                return field;
            }

            default:
                throw new BusinessException(DefinitionConstants.NOT_EXISTS_FIELD_DATA_ERROR);
        }
    }

    /**
     * 合并列表
     *
     * @param srcList  源
     * @param destList 目标
     * @return java.util.List<com.sn.gz.pmp.dsc.definition.field.bo.element.FieldBase>
     * @author lufeiwang
     * 2019/4/18
     */
    public static List<FieldBase> mergeFieldList(List<FieldBase> srcList, List<FieldBase> destList) {
        List<FieldBase> resList = new ArrayList<>();
        Map<String, FieldBase> map = new HashMap<>();
        for (FieldBase fieldBase : srcList) {
            map.put(fieldBase.getFieldApiName(), fieldBase);
        }
        for (FieldBase fieldBase : destList) {
            map.put(fieldBase.getFieldApiName(), fieldBase);
        }
        for (Map.Entry<String, FieldBase> entry : map.entrySet()) {
            resList.add(entry.getValue());
        }
        return resList;
    }

    /**
     * json串转化为对象数组
     *
     * @param json json
     * @return List<FieldBase>
     */
    public static List<FieldBase> json2FieldList(String json) {
        JSONArray arr = JSONArray.parseArray(json);
        if (null == arr || arr.size() == 0) {
            return new ArrayList<>();
        }

        List<FieldBase> list = new ArrayList<>();
        for (Iterator iterator = arr.iterator(); iterator.hasNext(); ) {
            JSONObject jsonObject = (JSONObject) iterator.next();
            FieldBase field = json2FieldDefinition(jsonObject.get("dataType").toString(), jsonObject.toJSONString());
            list.add(field);
        }
        return list;
    }

//    public static List<ViewColumnBTO> json2ViewList(String viewJson) {
//        JSONArray arr = JSONArray.parseArray(viewJson);
//        if (null == arr || arr.size() == 0) {
//            return null;
//        }
//
//        List<ViewColumnBTO> list = new ArrayList<>();
//        for (Iterator iterator = arr.iterator(); iterator.hasNext(); ) {
//            JSONObject jsonObject = (JSONObject) iterator.next();
//            ViewColumnBTO view = JSON.parseObject(jsonObject.toJSONString(), ViewColumnBTO.class);
//            list.add(view);
//        }
//        return list;
//    }

//    /**
//     * map
//     *
//     * @Description
//     * @author luffy
//     * @date 2017年6月22日 下午6:13:50
//     * @lastModifier
//     */
//    private static Map<String, FieldBTO> getFieldMap(List<FieldBase> fieldDescribes) {
//        if (CollectionUtils.isEmpty(fieldDescribes)) {
//            return null;
//        }
//
//        Map<String, FieldBTO> map = new HashMap<String, FieldBTO>();
//        for (FieldBase desc : fieldDescribes) {
//            FieldBTO fieldBto = new FieldBTO();
//            fieldBto.setFieldDescribeBase(desc);
//            map.put(desc.getFieldApiName(), fieldBto);
//        }
//        return map;
//    }

//    /**
//     * 组合列值和描述数据
//     *
//     * @Description
//     * @author luffy
//     * @date 2017年6月22日 下午6:39:12
//     * @lastModifier
//     */
//    public static List<FieldBTO> getFieldList(List<FieldValueBTO> fields, List<FieldBase> fieldDescribes)
//            throws BusinessException {
//        if (CollectionUtils.isEmpty(fieldDescribes)) {
//            return null;
//        }
//        Map<String, FieldBTO> map = getFieldMap(fieldDescribes);
//        if (null == map) {
//            return null;
//        }
//
//        List<FieldBTO> fieldBtoList = new ArrayList<FieldBTO>();
//        for (FieldValueBTO bto : fields) {
//            if (!map.containsKey(bto.getFieldApiName())) {
//                throw new BusinessException(ErrorMessage.NOT_EXISTS_FIELD_DESCRIPTION);
//            } else {
//                FieldBTO fieldBto = new FieldBTO();
//                fieldBto.setFieldDescribeBase(map.get(bto.getFieldApiName()).getFieldDescribeBase());
//                fieldBto.setValue(bto.getValue());
//                fieldBtoList.add(fieldBto);
//            }
//        }
//        return fieldBtoList;
//    }

//    /**
//     * 更新数据转化
//     *
//     * @Description
//     * @author luffy
//     * @date 2017年6月27日 下午8:50:05
//     * @lastModifier
//     */
//    public static UpdateData getUpdateData(List<FieldBTO> fieldList, String objectApiName, Long objectId) {
//        if (CollectionUtils.isEmpty(fieldList)) {
//            return null;
//        }
//
//        UpdateData updateData = new UpdateData();
//        try {
//            FieldType type;
//            for (FieldBTO bto : fieldList) {
//                type = FieldType.fromString(bto.getFieldDescribeBase().getType());
//                if (null == type) {
//                    throw new BusinessException(ErrorMessage.NOT_EXISTS_FIELD_DESCRIPTION_ERROR);
//                }
//                switch (type) {
//                    case Text: {
//                        if (null == updateData.getTextData()) {
//                            updateData.setTextData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        TextFieldUpdate textFieldUpdate = new TextFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                bto.getValue());
//                        updateData.getTextData().getList().add(textFieldUpdate);
//                        continue;
//                    }
//                    case LongText: {
//                        if (null == updateData.getTextData()) {
//                            updateData.setTextData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        TextFieldUpdate textFieldUpdate = new TextFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                bto.getValue());
//                        updateData.getTextData().getList().add(textFieldUpdate);
//                        continue;
//                    }
//                    case SelectOne: {
//                        if (null == updateData.getTextData()) {
//                            updateData.setTextData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        TextFieldUpdate textFieldUpdate = new TextFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                bto.getValue());
//                        updateData.getTextData().getList().add(textFieldUpdate);
//                        continue;
//                    }
//                    case SelectMany: {
//                        if (null == updateData.getTextData()) {
//                            updateData.setTextData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        TextFieldUpdate textFieldUpdate = new TextFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                bto.getValue());
//                        updateData.getTextData().getList().add(textFieldUpdate);
//                        continue;
//                    }
//                    case MutiLevelSelectOne: {
//                        if (null == updateData.getTextData()) {
//                            updateData.setTextData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        TextFieldUpdate textFieldUpdate = new TextFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                bto.getValue());
//                        updateData.getTextData().getList().add(textFieldUpdate);
//                        continue;
//                    }
//                    case Boolean: {
//                        if (null == updateData.getTextData()) {
//                            updateData.setTextData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        TextFieldUpdate textFieldUpdate = new TextFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                bto.getValue());
//                        updateData.getTextData().getList().add(textFieldUpdate);
//                        continue;
//                    }
//                    case Currency: {
//                        if (null == updateData.getNumberData()) {
//                            updateData.setNumberData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        NumberFieldUpdate numberFieldUpdate = new NumberFieldUpdate(
//                                bto.getFieldDescribeBase().getFieldApiName(), new BigDecimal(bto.getValue()));
//                        updateData.getNumberData().getList().add(numberFieldUpdate);
//                        continue;
//                    }
//                    case Date: {
//                        if (null == updateData.getDateData()) {
//                            updateData.setDateData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        DateFieldUpdate dateFieldUpdate = new DateFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                DateUtils.parseDate(bto.getValue(), new String[]{MetaConstants.DATE_FORMAT}));
//                        updateData.getDateData().getList().add(dateFieldUpdate);
//                        continue;
//                    }
//                    case Datetime: {
//
//                        if (null == updateData.getDateData()) {
//                            updateData.setDateData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        DateFieldUpdate dateFieldUpdate = new DateFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                DateUtils.parseDate(bto.getValue(), new String[]{MetaConstants.DATETIME_FORMAT}));
//                        updateData.getDateData().getList().add(dateFieldUpdate);
//                        continue;
//                    }
//                    case Time: {
//                        if (null == updateData.getDateData()) {
//                            updateData.setDateData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        DateFieldUpdate dateFieldUpdate = new DateFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                DateUtils.parseDate(bto.getValue(), new String[]{MetaConstants.DATETIME_FORMAT}));
//                        updateData.getDateData().getList().add(dateFieldUpdate);
//                        continue;
//                    }
//                    case Email: {
//                        if (null == updateData.getTextData()) {
//                            updateData.setTextData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        TextFieldUpdate textFieldUpdate = new TextFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                bto.getValue());
//                        updateData.getTextData().getList().add(textFieldUpdate);
//                        continue;
//                    }
//                    case File: {
//                        if (null == updateData.getTextData()) {
//                            updateData.setTextData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        TextFieldUpdate textFieldUpdate = new TextFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                bto.getValue());
//                        updateData.getTextData().getList().add(textFieldUpdate);
//                        continue;
//                    }
//                    case Image: {
//                        if (null == updateData.getTextData()) {
//                            updateData.setTextData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        TextFieldUpdate textFieldUpdate = new TextFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                bto.getValue());
//                        updateData.getTextData().getList().add(textFieldUpdate);
//                        continue;
//                    }
//                    case Number: {
//                        if (null == updateData.getNumberData()) {
//                            updateData.setNumberData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        NumberFieldUpdate numberFieldUpdate = new NumberFieldUpdate(
//                                bto.getFieldDescribeBase().getFieldApiName(), new BigDecimal(bto.getValue()));
//                        updateData.getNumberData().getList().add(numberFieldUpdate);
//                        continue;
//                    }
//                    case Percentage: {
//                        if (null == updateData.getNumberData()) {
//                            updateData.setNumberData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        NumberFieldUpdate numberFieldUpdate = new NumberFieldUpdate(
//                                bto.getFieldDescribeBase().getFieldApiName(), new BigDecimal(bto.getValue()));
//                        updateData.getNumberData().getList().add(numberFieldUpdate);
//                        continue;
//                    }
//                    case Long: {
//                        if (null == updateData.getLongData()) {
//                            updateData.setLongData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        LongFieldUpdate longFieldUpdate = new LongFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                new Long(bto.getValue()));
//                        updateData.getLongData().getList().add(longFieldUpdate);
//                        continue;
//                    }
//
//                    case Phone: {
//                        if (null == updateData.getTextData()) {
//                            updateData.setTextData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        TextFieldUpdate textFieldUpdate = new TextFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                bto.getValue());
//                        updateData.getTextData().getList().add(textFieldUpdate);
//                        continue;
//                    }
//                    case URL: {
//                        if (null == updateData.getTextData()) {
//                            updateData.setTextData(new FieldUpdate(objectApiName, objectId));
//                        }
//                        TextFieldUpdate textFieldUpdate = new TextFieldUpdate(bto.getFieldDescribeBase().getFieldApiName(),
//                                bto.getValue());
//                        updateData.getTextData().getList().add(textFieldUpdate);
//                        continue;
//                    }
//                    default:
//                        throw new BusinessException(ErrorMessage.NOT_EXISTS_FIELD_DATA_ERROR);
//                }
//            }
//        } catch (ParseException e) {
//            throw new BusinessException(ErrorMessage.NOT_EXISTS_FIELD_DESCRIPTION_ERROR);
//        }
//
//        return updateData;
//    }

//    /**
//     * 数据类型转化
//     *
//     * @Description
//     * @author luffy
//     * @date 2017年6月22日 下午8:21:00
//     * @lastModifier
//     */
//    public static FieldBatchBTO getFieldBatchBto(List<FieldBTO> fieldList, String objectApiName, Long objectId) {
//        if (CollectionUtils.isEmpty(fieldList)) {
//            return null;
//        }
//
//        FieldBatchBTO fieldBatchBto = new FieldBatchBTO();
//        List<FieldDatetimeBTO> dateList = new ArrayList<FieldDatetimeBTO>();
//        List<FieldLongBTO> longList = new ArrayList<FieldLongBTO>();
//        List<FieldNumberBTO> numberList = new ArrayList<FieldNumberBTO>();
//        List<FieldTextBTO> textList = new ArrayList<FieldTextBTO>();
//
//        try {
//            FieldType type;
//            for (FieldBTO bto : fieldList) {
//                type = FieldType.fromString(bto.getFieldDescribeBase().getType());
//                if (null == type) {
//                    throw new BusinessException(ErrorMessage.NOT_EXISTS_FIELD_DESCRIPTION_ERROR);
//                }
//                switch (type) {
//                    case Text: {
//                        FieldTextBTO fieldTextBto = new FieldTextBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                bto.getValue());
//                        textList.add(fieldTextBto);
//                        continue;
//                    }
//                    case LongText: {
//                        FieldTextBTO fieldTextBto = new FieldTextBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                bto.getValue());
//                        textList.add(fieldTextBto);
//                        continue;
//                    }
//                    case SelectOne: {
//                        FieldTextBTO fieldTextBto = new FieldTextBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                bto.getValue());
//                        textList.add(fieldTextBto);
//                        continue;
//                    }
//                    case SelectMany: {
//                        FieldTextBTO fieldTextBto = new FieldTextBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                bto.getValue());
//                        textList.add(fieldTextBto);
//                        continue;
//                    }
//                    case MutiLevelSelectOne: {
//                        FieldTextBTO fieldTextBto = new FieldTextBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                bto.getValue());
//                        textList.add(fieldTextBto);
//                        continue;
//                    }
//                    case Boolean: {
//                        FieldTextBTO fieldTextBto = new FieldTextBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                bto.getValue());
//                        textList.add(fieldTextBto);
//                        continue;
//                    }
//                    case Currency: {
//                        FieldNumberBTO fieldNumberBto = new FieldNumberBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                new BigDecimal(bto.getValue()));
//                        numberList.add(fieldNumberBto);
//                        continue;
//                    }
//                    case Date: {
//                        FieldDatetimeBTO fieldDatetimeBto = new FieldDatetimeBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                DateUtils.parseDate(bto.getValue(), new String[]{MetaConstants.DATE_FORMAT}));
//                        dateList.add(fieldDatetimeBto);
//                        continue;
//                    }
//                    case Datetime: {
//                        FieldDatetimeBTO fieldDatetimeBto = new FieldDatetimeBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                DateUtils.parseDate(bto.getValue(), new String[]{MetaConstants.DATETIME_FORMAT}));
//                        dateList.add(fieldDatetimeBto);
//                        continue;
//                    }
//                    case Time: {
//                        FieldDatetimeBTO fieldDatetimeBto = new FieldDatetimeBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                DateUtils.parseDate(bto.getValue(), new String[]{MetaConstants.DATETIME_FORMAT}));
//                        dateList.add(fieldDatetimeBto);
//                        continue;
//                    }
//                    case Email: {
//                        FieldTextBTO fieldTextBto = new FieldTextBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                bto.getValue());
//                        textList.add(fieldTextBto);
//                        continue;
//                    }
//                    case File: {
//                        FieldTextBTO fieldTextBto = new FieldTextBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                bto.getValue());
//                        textList.add(fieldTextBto);
//                        continue;
//                    }
//                    case Image: {
//                        FieldTextBTO fieldTextBto = new FieldTextBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                bto.getValue());
//                        textList.add(fieldTextBto);
//                        continue;
//                    }
//                    case Number: {
//                        FieldNumberBTO fieldNumberBto = new FieldNumberBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                new BigDecimal(bto.getValue()));
//                        numberList.add(fieldNumberBto);
//                        continue;
//                    }
//                    case Percentage: {
//                        FieldNumberBTO fieldNumberBto = new FieldNumberBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                new BigDecimal(bto.getValue()));
//                        numberList.add(fieldNumberBto);
//                        continue;
//                    }
//                    case Long: {
//                        FieldLongBTO fieldLongBto = new FieldLongBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                new Long(bto.getValue()));
//                        longList.add(fieldLongBto);
//                        continue;
//                    }
//
//                    case Phone: {
//                        FieldTextBTO fieldTextBto = new FieldTextBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                bto.getValue());
//                        textList.add(fieldTextBto);
//                        continue;
//                    }
//                    case URL: {
//                        FieldTextBTO fieldTextBto = new FieldTextBTO(objectApiName,
//                                bto.getFieldDescribeBase().getFieldApiName(), objectId, IdGenerator.getInstance().nextId(),
//                                bto.getValue());
//                        textList.add(fieldTextBto);
//                        continue;
//                    }
//                    default:
//                        throw new BusinessException(ErrorMessage.NOT_EXISTS_FIELD_DATA_ERROR);
//                }
//            }
//        } catch (ParseException e) {
//            e.printStackTrace();
//            throw new BusinessException(ErrorMessage.NOT_EXISTS_FIELD_DESCRIPTION_ERROR);
//        }
//
//        fieldBatchBto.setDateList(dateList);
//        fieldBatchBto.setLongList(longList);
//        fieldBatchBto.setNumberList(numberList);
//        fieldBatchBto.setTextList(textList);
//
//        return fieldBatchBto;
//    }
//
//    /**
//     * 组装MQ返回信息
//     *
//     * @Description
//     * @author luffy
//     * @date 2017年6月27日 上午10:59:21
//     * @lastModifier
//     */
//    public static List<ObjectMQBTO> getMetadataMQBTO(List<FieldValueBTO> fieldValueList, Long objectId) {
//        List<ObjectMQBTO> objectLists = new LinkedList<ObjectMQBTO>();
//        ObjectMQBTO objectMQBTO = new ObjectMQBTO();
//        objectMQBTO.setObjectId(objectId);
//        Map<String, Object> fields = new HashMap<String, Object>();
//        for (FieldValueBTO fieldValue : fieldValueList) {
//            fields.put(fieldValue.getFieldApiName(), fieldValue.getValue());
//        }
//        objectMQBTO.setFields(fields);
//        objectLists.add(objectMQBTO);
//
//        return objectLists;
//    }

//    /**
//     * 数据转化
//     *
//     * @Description
//     * @author luffy
//     * @date 2017年6月27日 下午2:00:01
//     * @lastModifier
//     */
//    public static BatchAddField transfer2BatchField(FieldBatchBTO fieldBatchBto) {
//        BatchAddField batchField = new BatchAddField();
//        List<IrobjectDatetime> fieldDatelist = new ArrayList<>();
//        List<IrobjectLong> fieldLonglist = new ArrayList<>();
//        List<IrobjectNumber> fieldNumberlist = new ArrayList<>();
//        List<IrobjectText> fieldTextlist = new ArrayList<>();
//
//        // 数据转化
//        List<FieldDatetimeBTO> dateList = fieldBatchBto.getDateList();
//        if (!CollectionUtils.isEmpty(dateList)) {
//            for (FieldDatetimeBTO bto : dateList) {
//                fieldDatelist.add(BtoModelTransfer.INSTANCE.map(bto));
//            }
//        }
//
//        List<FieldLongBTO> longList = fieldBatchBto.getLongList();
//        if (!CollectionUtils.isEmpty(longList)) {
//            for (FieldLongBTO bto : longList) {
//                fieldLonglist.add(BtoModelTransfer.INSTANCE.map(bto));
//            }
//        }
//
//        List<FieldNumberBTO> numberList = fieldBatchBto.getNumberList();
//        if (!CollectionUtils.isEmpty(numberList)) {
//            for (FieldNumberBTO bto : numberList) {
//                fieldNumberlist.add(BtoModelTransfer.INSTANCE.map(bto));
//            }
//        }
//
//        List<FieldTextBTO> textList = fieldBatchBto.getTextList();
//        if (!CollectionUtils.isEmpty(textList)) {
//            for (FieldTextBTO bto : textList) {
//                fieldTextlist.add(BtoModelTransfer.INSTANCE.map(bto));
//            }
//        }
//        batchField.setFieldDatelist(fieldDatelist);
//        batchField.setFieldLonglist(fieldLonglist);
//        batchField.setFieldNumberlist(fieldNumberlist);
//        batchField.setFieldTextlist(fieldTextlist);
//        return batchField;
//    }

    /**
     * 校验字段是否重复
     *
     * @param fieldList 字段列表
     * @author lufeiwang
     * 2019/4/18
     */
    public static void checkFieldDulplication(List<FieldBase> fieldList) {
        if (CollectionUtils.isEmpty(fieldList)) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        for (FieldBase item : fieldList) {
            if (map.containsKey(item.getFieldApiName())) {
                throw new BusinessException(DefinitionConstants.FIELD_DUPLICATION);
            } else {
                map.put(item.getFieldApiName(), item);
            }
        }
    }
}
