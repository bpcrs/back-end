package fpt.capstone.bpcrs.util;

import fpt.capstone.bpcrs.model.Car;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ValidateUtils {

    public static <T> List<String> validateObject(Class<T> classType,Object object){
        List<String> errors = new ArrayList<>();
        Set<ConstraintViolation<Object>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(object,classType);
        for (ConstraintViolation<Object> violation : violations) {
            errors.add(violation.getMessage());
        }
//        violations.toArray()
        return errors;
    }
}
