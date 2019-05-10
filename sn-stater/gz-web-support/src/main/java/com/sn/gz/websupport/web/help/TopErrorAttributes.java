/*
 * Copyright (C), 2018-2019, 深圳点积科技有限公司
 * FileName: TopErrorAttributes
 * Author:   lufeiwang
 * Date:   2019/4/9
 */
package com.sn.gz.websupport.web.help;

import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.sn.gz.core.BusinessException;
import com.sn.gz.core.StatusCode;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.context.MessageSource;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

/**
 * 顶层异常处理
 *
 * @author lufeiwang
 * 2019/4/9
 */
@Slf4j
public class TopErrorAttributes extends DefaultErrorAttributes {

    private static final int BUSINESS_HTTP_STATUS = HttpStatus.EXPECTATION_FAILED.value();
    private static final String ERROR_ATTRIBUTE = TopErrorAttributes.class.getName() + ".ERROR";

    private final MessageSource messageSource;

    private static final String CONTROLLER_CLASS_ATTRIBUTE =
            TopErrorAttributes.class.getName() + ".CONTROLLER_CLASS";
    private static final String CONTROLLER_METHOD_ATTRIBUTE =
            TopErrorAttributes.class.getName() + ".CONTROLLER_METHOD";

    public TopErrorAttributes(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public Map<String, Object> getErrorAttributes(WebRequest webRequest,
                                                  boolean includeStackTrace) {
        Map<String, Object> errorAttributes = new LinkedHashMap<>();
        errorAttributes.put("timestamp", System.currentTimeMillis());
        addCode(errorAttributes, webRequest);
        addStatus(webRequest);
        addExceptionClassName(errorAttributes, webRequest);
        addErrorDetails(errorAttributes, webRequest, includeStackTrace);
        addPath(errorAttributes, webRequest);
        addTraceTags(webRequest);
        return errorAttributes;
    }

    @Override
    public ModelAndView resolveException(HttpServletRequest request,
                                         HttpServletResponse response, Object handler, Exception ex) {
        storeErrorAttributes(request, handler, ex);
        Throwable cause = getCause(ex);
        ModelAndView modelAndView = null;
        if (cause instanceof BusinessException) {
            BusinessException businessException = (BusinessException) cause;
            log.warn("resolveException handler BusinessException:", cause);
            modelAndView = new ModelAndView();
            FastJsonJsonView view = new FastJsonJsonView();
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("code", businessException.getCode());
            attributes.put("message", businessException.getMessage());
            attributes.put("data", "");
            view.setAttributesMap(attributes);
            modelAndView.setView(view);
        } else if (isIllegalException(cause)) {
            log.warn("resolveException illegal parameters!:", cause);
            modelAndView = new ModelAndView();
            FastJsonJsonView view = new FastJsonJsonView();
            Map<String, Object> attributes = new HashMap<>();

            if (cause instanceof BindException) {
                BindException bindException = (BindException) cause;
                List<ObjectError> objectErrorList = bindException.getAllErrors();
                Map<String, String> errorMap = new HashMap<>();
                StringBuilder sb = new StringBuilder();
                for (ObjectError objectError : objectErrorList) {
                    String key = ((FieldError) objectError).getField();
                    String value = objectError.getDefaultMessage();
                    sb.append(value);
                    sb.append(",");
                    errorMap.put(key, value);
                }
                attributes.put("code", StatusCode.BUSINESS_EXCEPTION.getCode());
                attributes.put("message", sb.substring(0, sb.length() - 1).replaceAll("[!！]", ""));
                attributes.put("data", "");
            } else if (cause instanceof MethodArgumentNotValidException) {
                MethodArgumentNotValidException methodArgumentNotValidException = (MethodArgumentNotValidException) cause;
                BindingResult bindingResult = methodArgumentNotValidException.getBindingResult();
                List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
                Map<String, String> errorMap = new HashMap<>();
                StringBuilder sb = new StringBuilder();
                for (FieldError fieldError : fieldErrorList) {
                    String key = fieldError.getField();
                    String value = fieldError.getDefaultMessage();
                    sb.append(value);
                    sb.append(",");
                    errorMap.put(key, value);
                }
                attributes.put("code", StatusCode.BUSINESS_EXCEPTION.getCode());
                attributes.put("message", sb.substring(0, sb.length() - 1).replaceAll("[!！]", ""));
                attributes.put("data", "");
            }

            view.setAttributesMap(attributes);
            modelAndView.setView(view);
        } else {
            log.error("resolveException handler Exception:", ex);
            modelAndView = new ModelAndView();
            FastJsonJsonView view = new FastJsonJsonView();
            Map<String, Object> attributes = new HashMap<String, Object>();
            attributes.put("code", StatusCode.SERVER_EXCEPTION.getCode());
            attributes.put("message", StatusCode.SERVER_EXCEPTION.getMessage());
            attributes.put("data", "");
            view.setAttributesMap(attributes);
            modelAndView.setView(view);
        }
        return modelAndView;
    }

    /**
     * 保存code信息,从BusinessException取code,如果没有统一500
     *
     * @param errorAttributes 错误信息
     * @param webRequest      request
     */
    private void addCode(Map<String, Object> errorAttributes, WebRequest webRequest) {
        Throwable error = getError(webRequest);
        if (error != null) {
            Throwable cause = getBusinessExceptionRecursive(error);
            Integer code;
            if (cause != null) {
                code = ((BusinessException) cause).getCode();
            } else if (isIllegalException(error)) {
                code = StatusCode.PARAM_ILLEGAL.getCode();
            } else {
                code = StatusCode.SERVER_EXCEPTION.getCode();
            }
            errorAttributes.put("code", code);
        } else {
            errorAttributes.put("code", getAttribute(webRequest, "javax.servlet.error.status_code"));
        }
    }

    /**
     * 保存状态值
     *
     * @param webRequest 请求
     * @author lufeiwang
     * 2019/4/9
     */
    private void addStatus(WebRequest webRequest) {
        Throwable error = getError(webRequest);
        if (error == null) {
            return;
        }
        Throwable cause = getBusinessExceptionRecursive(error);
        Integer status;
        //如果为空则不是BusinessException
        if (cause == null) {
            if (isIllegalException(error)) {
                status = StatusCode.PARAM_ILLEGAL.getCode();
            } else {
                //空指针等
                status = StatusCode.SERVER_EXCEPTION.getCode();
            }
        } else {
            ResponseStatus responseStatus = AnnotatedElementUtils
                    .findMergedAnnotation(cause.getClass(), ResponseStatus.class);
            if (responseStatus != null) {
                status = responseStatus.code().value();
            } else {
                status = BUSINESS_HTTP_STATUS;
            }
        }
        //保存status信息 requestAttributes在Controller上会吧status放到response里面
        webRequest.setAttribute("javax.servlet.error.status_code", status, RequestAttributes.SCOPE_REQUEST);
    }

    /**
     * 递归查找业务异常
     *
     * @param error 异常
     * @return java.lang.Throwable
     * @author lufeiwang
     * 2019/4/9
     */
    private Throwable getBusinessExceptionRecursive(Throwable error) {
        while (error != null) {
            if (error instanceof BusinessException) {
                break;
            }
            error = error.getCause();
        }
        return error;
    }

    /**
     * @param error 错误
     * @return boolean
     * @author lufeiwang
     * 2019/4/9
     */
    private static boolean isIllegalException(Throwable error) {
        return error instanceof BindException || error instanceof MethodArgumentNotValidException ||
                error instanceof HttpRequestMethodNotSupportedException || error instanceof ConstraintViolationException;
    }

    /**
     * 添加异常类名
     *
     * @param errorAttributes 错误信息
     * @param webRequest      异常
     */
    private void addExceptionClassName(Map<String, Object> errorAttributes, WebRequest webRequest) {
        Throwable error = getError(webRequest);
        error = getBusinessExceptionRecursive(error);
        if (error != null) {
            errorAttributes.put("exceptionClass", error.getClass().getName());
        }
    }

    /**
     * 添加错误详情
     *
     * @param errorAttributes   错误信息
     * @param webRequest        请求
     * @param includeStackTrace 是否包含错误栈
     * @author lufeiwang
     * 2019/4/9
     */
    private void addErrorDetails(Map<String, Object> errorAttributes, WebRequest webRequest,
                                 boolean includeStackTrace) {
        Throwable error = getError(webRequest);
        if (error != null) {
            while (error instanceof ServletException && error.getCause() != null) {
                error = error.getCause();
            }
            addErrorMessage(errorAttributes, error);
            if (includeStackTrace) {
                errorAttributes.put("exception", error.getClass().getName());
                addStackTrace(errorAttributes, error);
            }
        }
    }

    /**
     * 添加错误信息
     *
     * @param errorAttributes 错误
     * @param error           异常
     * @author lufeiwang
     * 2019/4/9
     */
    private void addErrorMessage(Map<String, Object> errorAttributes, Throwable error) {
        if (error instanceof BusinessException) {
            BusinessException exception = (BusinessException) error;
            String msg = null;
            if ((!StringUtils.isEmpty(exception.getCode())) && (messageSource != null)) {
                msg = messageSource
                        .getMessage(exception.getMessage(), new String[0], exception.getMessage(), null);
            }
            if (StringUtils.isEmpty(msg)) {
                msg = exception.getMessage();
            }
            errorAttributes.put("message", msg);
        } else if (error instanceof BindingResult || error instanceof MethodArgumentNotValidException) {
            BindingResult result = extractBindingResult(error);
            if (result != null && result.getErrorCount() > 0) {
                errorAttributes.put("data", result.getFieldErrors());
                errorAttributes.put("message", String.join(",", getErrorMsg(result.getFieldErrors())));
            } else {
                errorAttributes.put("message", "No errors");
            }
        } else if (error instanceof ConstraintViolationException) {
            Set<ConstraintViolation<?>> constraintViolations = ((ConstraintViolationException) error)
                    .getConstraintViolations();
            List<FieldError> fieldErrors = new ArrayList<>(constraintViolations.size());
            List<String> errorMessages = new ArrayList<>(constraintViolations.size());
            for (ConstraintViolation<?> constraintViolation : constraintViolations) {
                String objectName = ((PathImpl) constraintViolation.getPropertyPath()).getPathWithoutLeafNode()
                        .asString();
                String field = ((PathImpl) constraintViolation.getPropertyPath()).getLeafNode().toString();
                fieldErrors.add(
                        new FieldError(objectName, field, constraintViolation.getInvalidValue(), false, null, null,
                                constraintViolation.getMessage()));
                errorMessages.add(field + ":" + constraintViolation.getMessage());
            }
            errorAttributes.put("data", fieldErrors);
            errorAttributes.put("message", String.join(",", errorMessages));
        } else {
            //拿到最根本的错误信息
            Throwable cause = getCause(error);
            errorAttributes.put("message", cause.getMessage());
        }
    }

    /**
     * 获取错误信息
     *
     * @param errors 错误信息
     * @return java.lang.String[]
     * @author lufeiwang
     * 2019/4/9
     */
    private String[] getErrorMsg(List<FieldError> errors) {
        return errors.stream()
                .map(e -> e.getField() + ":" + e.getDefaultMessage())
                .toArray(String[]::new);
    }

    /**
     * 获取异常
     *
     * @param error 错误信息
     * @return java.lang.Throwable
     * @author lufeiwang
     * 2019/4/9
     */
    private Throwable getCause(Throwable error) {
        Throwable cause = error;
        while (cause.getCause() != null) {
            cause = cause.getCause();
        }
        return cause;
    }

    /**
     * 解析绑定结果
     *
     * @param error 错误信息
     * @return org.springframework.validation.BindingResult
     * @author lufeiwang
     * 2019/4/9
     */
    private BindingResult extractBindingResult(Throwable error) {
        if (error instanceof BindingResult) {
            return (BindingResult) error;
        } else if (error instanceof MethodArgumentNotValidException) {
            return ((MethodArgumentNotValidException) error).getBindingResult();
        } else {
            return null;
        }
    }

    /**
     * 添加异常栈信息
     *
     * @param errorAttributes 错误
     * @param error           异常
     */
    private void addStackTrace(Map<String, Object> errorAttributes, Throwable error) {
        StringWriter stackTrace = new StringWriter();
        error.printStackTrace(new PrintWriter(stackTrace));
        stackTrace.flush();
        errorAttributes.put("trace", stackTrace.toString());
    }

    /**
     * 添加访问路径
     *
     * @param errorAttributes 错误
     * @param webRequest      请求
     * @author lufeiwang
     * 2019/4/9
     */
    private void addPath(Map<String, Object> errorAttributes,
                         WebRequest webRequest) {
        String path = getAttribute(webRequest, "javax.servlet.error.request_uri");
        if (path != null) {
            errorAttributes.put("path", path);
        }
    }

    /**
     * @param request   请求
     * @param handler   处理对象
     * @param exception 异常
     * @author lufeiwang
     * 2019/4/9
     */
    private void storeErrorAttributes(HttpServletRequest request, Object handler, Exception exception) {
        if (handler != null) {
            String className;
            String methodName;
            if (handler instanceof HandlerMethod) {
                methodName = ((HandlerMethod) handler).getMethod().getName();
                className = ((HandlerMethod) handler).getBeanType().getSimpleName();
            } else {
                className = handler.getClass().getSimpleName();
                methodName = null;
            }
            request.setAttribute(CONTROLLER_CLASS_ATTRIBUTE, className);
            request.setAttribute(CONTROLLER_METHOD_ATTRIBUTE, methodName);
        }

        request.setAttribute(ERROR_ATTRIBUTE, exception);
    }

    /**
     * 收集请求基本信息
     *
     * @param webRequest 请求
     * @author lufeiwang
     * 2019/4/9
     */
    private void addTraceTags(WebRequest webRequest) {
    }

    @SuppressWarnings("unchecked")
    private <T> T getAttribute(WebRequest webRequest, String name) {
        return (T) webRequest.getAttribute(name, RequestAttributes.SCOPE_REQUEST);
    }
}
