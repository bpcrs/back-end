package fpt.capstone.bpcrs.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;

public class ValidateUtils {

    public static <T> List<String> validateObject(Class<T> classType,Object object){
        List<String> errors = new ArrayList<>();
        Set<ConstraintViolation<Object>> violations = Validation.buildDefaultValidatorFactory().getValidator().validate(object,classType);
        for (ConstraintViolation<Object> violation : violations) {
            errors.add(violation.getMessage());
        }
        return errors;
    }
}
