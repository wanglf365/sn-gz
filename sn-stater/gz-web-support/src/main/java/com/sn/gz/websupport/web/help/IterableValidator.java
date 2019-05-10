/*
 * Copyright (C), 2018-2018, 深圳点积科技有限公司
 * FileName: SwaggerConfigurer
 * Author:   luffy
 * Date:     2018/3/21 20:42
 *
 * @since 1.0.0
 */
package com.sn.gz.websupport.web.help;

import com.google.common.collect.ImmutableList;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.validation.*;

import java.lang.reflect.Array;
import java.util.List;

/**
 *  扩展spring对validator的支持
 * @since 1.0.0
 * @author luffy
 * Date: 2018/3/22 9:25
 */
public class IterableValidator implements SmartValidator {

    private static final String DOT = ".";
    private static final String FORMATTER = "%s[%d]";

    private final List<Validator> delegates;

    public IterableValidator(List<Validator> delegates) {
        this.delegates = ImmutableList.copyOf(delegates);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (CollectionUtils.isEmpty(delegates)) {
            return;
        }

        if (target instanceof Iterable) {
            int index = 0;
            for (Object obj : (Iterable) target) {
                validateSingle(index++, obj, errors);
            }
        } else if (target.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(target); i++) {
                Object obj = Array.get(target, i);
                validateSingle(i, obj, errors);
            }
        } else {
            throw new IllegalStateException("validate error, the class must be iterable or array");
        }
    }

    @Override
    public void validate(Object target, Errors errors, Object... validationHints) {
        if (CollectionUtils.isEmpty(delegates)) {
            return;
        }

        if (target instanceof Iterable) {
            int index = 0;
            for (Object obj : (Iterable) target) {
                validateSingle(index++, obj, errors, validationHints);
            }
        } else if (target.getClass().isArray()) {
            for (int i = 0; i < Array.getLength(target); i++) {
                Object obj = Array.get(target, i);
                validateSingle(i, obj, errors, validationHints);
            }
        } else {
            throw new IllegalStateException("validate error, the class must be iterable or array");
        }
    }

    private void validateSingle(int index, Object obj, Errors errors) {
        if (obj == null) {
            return;
        }
        String objectName = String.format(FORMATTER, errors.getObjectName(), index);
        for (Validator validator : delegates) {
            if (validator.supports(obj.getClass())) {
                Errors fieldErrors = new BeanPropertyBindingResult(obj, objectName);
                validator.validate(obj, fieldErrors);
                mergeFieldErrorToRoot(errors, fieldErrors, objectName);
            }
        }
    }

    private void validateSingle(int index, Object obj, Errors errors, Object... validationHints) {
        if (obj == null) {
            return;
        }
        String objectName = String.format(FORMATTER, errors.getObjectName(), index);
        for (Validator validator : delegates) {
            if (validator instanceof SmartValidator && validator.supports(obj.getClass())) {
                Errors fieldErrors = new BeanPropertyBindingResult(obj, objectName);
                ((SmartValidator) validator).validate(obj, fieldErrors, validationHints);
                mergeFieldErrorToRoot(errors, fieldErrors, objectName);
            }
        }
    }

    private void mergeFieldErrorToRoot(Errors rootErrors, Errors fieldErrors, String fieldPref) {
        if (!fieldErrors.hasFieldErrors()) {
            return;
        }

        if (rootErrors instanceof BindingResult) {
            BindingResult result = (BindingResult) rootErrors;
            for (FieldError fieldError : fieldErrors.getFieldErrors()) {
                String field = fieldPref + DOT + fieldError.getField();
                FieldError fe = new FieldError(
                    rootErrors.getObjectName(), field, fieldError.getRejectedValue(), false, fieldError.getCodes(),
                    fieldError.getArguments(), fieldError.getDefaultMessage());
                result.addError(fe);
            }
        } else {
            for (FieldError fieldError : fieldErrors.getFieldErrors()) {
                // api限制，只能暂时注册为全局错误
                rootErrors.rejectValue(null, fieldError.getCode(), fieldError.getArguments(),
                    fieldError.getDefaultMessage());
            }
        }
    }
}
