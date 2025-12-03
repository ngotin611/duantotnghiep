package com.example.foodbe.annotation;

import com.example.foodbe.annotation_handler.PasswordMatchesValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

@Target({ElementType.TYPE}) // Đúng chuẩn: TYPE cho DTO/class
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {

    String message() default "Passwords do not match";

}
