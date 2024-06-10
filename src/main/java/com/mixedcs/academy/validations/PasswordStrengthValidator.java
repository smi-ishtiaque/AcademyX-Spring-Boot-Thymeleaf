package com.mixedcs.academy.validations;

import com.mixedcs.academy.annotation.PasswordValidator;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;
import java.util.List;

public class PasswordStrengthValidator implements ConstraintValidator<PasswordValidator, String> {

    List<String> weakPasswords;

    @Override
    public void initialize(PasswordValidator passwordValidator) {
        weakPasswords = Arrays.asList("012345", "1234", "password", "admin", "hello", "ok");
    }

    @Override
    public boolean isValid(String passwordField,
                           ConstraintValidatorContext cxt) {
        return passwordField != null && (!weakPasswords.contains(passwordField));
    }
}
