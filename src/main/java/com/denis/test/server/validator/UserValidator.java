package com.denis.test.server.validator;

import com.denis.test.server.entity.UserEntity;
import com.denis.test.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {

    @Autowired
    private UserService userService;
    @Override
    public boolean supports(Class<?> aClass) {
        return UserEntity.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
//        UserEntity userEntity = (UserEntity)o;
//
//        ValidationUtils.rejectIfEmptyOrWhitespace(errors,"username","Required");

    }
}
