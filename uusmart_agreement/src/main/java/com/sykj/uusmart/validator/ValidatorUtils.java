package com.sykj.uusmart.validator;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2018/5/29 0029.
 */
public class ValidatorUtils {
    private static ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    /**
     * 校验 请求hG参数是否有问题
     * @param
     */
    public static <T>  void validata(T t) throws CustomRunTimeException{
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(t);
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            throw new CustomRunTimeException(Constants.resultCode.PARAM_VALUE_INVALID, constraintViolation.getMessage(), new Object[]{constraintViolation.getPropertyPath().toString()});
        }
    }


    public static <T>  void validata(T t, Object object) throws CustomRunTimeException{
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Object>> constraintViolations = validator.validate(t);
        for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
            throw new CustomRunTimeException(Constants.resultCode.PARAM_VALUE_INVALID, constraintViolation.getMessage(), new Object[]{constraintViolation.getPropertyPath().toString()}, object);
        }
    }

    public static <T>  void validata(List<T> list) throws CustomRunTimeException{
        Validator validator = factory.getValidator();
        for(T t : list){
            Set<ConstraintViolation<Object>> constraintViolations = validator.validate(t);
            for (ConstraintViolation<Object> constraintViolation : constraintViolations) {
                throw new CustomRunTimeException(Constants.resultCode.PARAM_VALUE_INVALID, constraintViolation.getMessage(), new Object[]{constraintViolation.getPropertyPath().toString()});
            }
        }
    }
}
