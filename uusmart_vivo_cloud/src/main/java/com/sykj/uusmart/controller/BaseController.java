package com.sykj.uusmart.controller;

import com.sykj.uusmart.Constants;
import com.sykj.uusmart.exception.CustomRunTimeException;
import com.sykj.uusmart.validator.ValidatorUtils;
import org.springframework.validation.BindingResult;


public class BaseController extends ValidatorUtils{

    public static void validataBind(BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new CustomRunTimeException(Constants.resultCode.PARAM_VALUE_INVALID, bindingResult.getFieldError().getDefaultMessage(), new Object[]{bindingResult.getFieldError().getField()});
        }
    }

    public static <T> void validataBind(BindingResult bindingResult, T t){
        if (bindingResult.hasErrors()) {
            throw new CustomRunTimeException(Constants.resultCode.PARAM_VALUE_INVALID, bindingResult.getFieldError().getDefaultMessage(), new Object[]{bindingResult.getFieldError().getField()});
        }
        CustomRunTimeException.checkNull(t,"hG");
    }


}
