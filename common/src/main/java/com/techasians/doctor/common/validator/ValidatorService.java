package com.techasians.doctor.common.validator;


import com.techasians.doctor.common.validator.exception.FormatInvalidException;

public interface ValidatorService {
    ValidatorMessage process(ValidateObject obj) throws FormatInvalidException;
}
