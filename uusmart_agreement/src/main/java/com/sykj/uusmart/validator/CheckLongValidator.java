package com.sykj.uusmart.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created by Administrator on 2018/5/15 0015.
 */
public class CheckLongValidator implements ConstraintValidator<CheckLong, Long> {
    private int min;
    private int max;

    @Override
    public void initialize(CheckLong checkLong) {
        min = checkLong.min();
        max = checkLong.max();
    }

    @Override
    public boolean isValid(Long aLong, ConstraintValidatorContext constraintValidatorContext) {
        if(aLong == null){
            return true;
        }
        if(min <= aLong.toString().length() && max >= aLong.toString().length()){
            return true;
        }
        return false;
    }

}
