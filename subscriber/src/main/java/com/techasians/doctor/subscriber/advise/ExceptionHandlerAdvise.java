package com.techasians.doctor.subscriber.advise;

import com.restfb.exception.FacebookOAuthException;
import com.techasians.doctor.common.respond.ResponseData;
import com.techasians.doctor.common.utils.CommonUtils;
import com.techasians.doctor.subscriber.exception.CodeUnConfirmException;
import com.techasians.doctor.subscriber.exception.ResourceNotFoundException;
import com.techasians.doctor.subscriber.exception.ValidateException;
import com.techasians.doctor.subscriber.utils.Constants;
import com.techasians.doctor.subscriber.web.rest.errors.DuplicateTokenException;
import com.techasians.doctor.subscriber.web.rest.errors.Google0AuthException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.stream.Collectors;

/**
 * ExceptionHandlerAdvise class.
 * Custom the handling Exception
 *
 * @author ngocquang
 * @since 22/1/2021
 */

@ControllerAdvice
public class ExceptionHandlerAdvise extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CodeUnConfirmException.class)
    public ResponseEntity<ResponseData> handleValidateException(CodeUnConfirmException ex) {
        ResponseData responseDTO = new ResponseData();
        responseDTO.setErrorCode("3");
        responseDTO.setMessage(translate(ex.getMessage()));
        return ResponseEntity.ok(responseDTO);
    }


    /**
     * Handle Exception validate exception data custom.
     *
     * @param ex
     * @return
     */

    @ExceptionHandler(ValidateException.class)
    public ResponseEntity<ResponseData> handleValidateException(ValidateException ex) {
        ResponseData responseDTO = new ResponseData();
        if(!ex.getMap().isEmpty()){
            responseDTO.setMessage(translate(Constants.MESSAGE.VALIDATE_FAILURE));
            ex.getMap().entrySet().stream().map(stringStringEntry -> {
                String value=stringStringEntry.getValue();
                stringStringEntry.setValue(translate(value));
                return stringStringEntry;
            }).collect(Collectors.toSet());
            responseDTO.setData(ex.getMap());
        }else {
            responseDTO.setMessage(translate(ex.getMessage()));
        }
        responseDTO.setErrorCode(com.techasians.doctor.common.utils.Constants.ERROR_CODE.VALIDATE_FAIL);
        return ResponseEntity.ok(responseDTO);
    }

    /**
     * Handle BadCredentialsException  data custom
     *
     * @param ex
     * @return
     */

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ResponseData> handleBadCredentialsException(BadCredentialsException ex) {
        ResponseData responseDTO = new ResponseData();
        responseDTO.setErrorCode(com.techasians.doctor.common.utils.Constants.ERROR_CODE.VALIDATE_FAIL);
        responseDTO.setMessage(translate(Constants.MESSAGE_LOGIN_FAILURE));
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(DisabledException.class)
    public ResponseEntity<ResponseData> handleDisabledException(DisabledException ex) {
        ResponseData responseDTO = new ResponseData();
        responseDTO.setErrorCode(com.techasians.doctor.common.utils.Constants.ERROR_CODE.VALIDATE_FAIL);
        responseDTO.setMessage(translate(Constants.MESSAGE_LOGIN_USER_DISABLE));
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(AccountExpiredException.class)
    public ResponseEntity<ResponseData> handleAccountExpiredException(AccountExpiredException ex) {
        ResponseData responseDTO = new ResponseData();
        responseDTO.setErrorCode(com.techasians.doctor.common.utils.Constants.ERROR_CODE.VALIDATE_FAIL);
        responseDTO.setMessage(translate(Constants.MESSAGE_LOGIN_TOKEN_EXPIRED));
        return ResponseEntity.ok(responseDTO);
    }


    /**
     * Handle valid argument on request. HTTP_CODE  = 400.
     *
     * @param ex
     * @param headers
     * @param status
     * @param request
     * @return
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status,
                                                                  WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        HashMap<String, String> fieldErrors = new HashMap<>();
        bindingResult.getFieldErrors().forEach(fieldError -> {
            fieldErrors.put(fieldError.getField(), translate(fieldError.getDefaultMessage()));
        });

        ResponseData responseData = new ResponseData();
        responseData.setErrorCode(com.techasians.doctor.common.utils.Constants.ERROR_CODE.VALIDATE_FAIL);
        responseData.setMessage(translate(Constants.COMMON_MESSAGE_FAIL));
        responseData.setData(fieldErrors);
        return ResponseEntity.ok(responseData);
    }

    @Autowired
    private MessageSource messageSource;

    public String translate(String msgCode) {
        Locale locale = LocaleContextHolder.getLocale();
        if (CommonUtils.isNullOrEmpty(msgCode)) return null;
        try {
            return messageSource.getMessage(msgCode, null, locale);
        } catch (NoSuchMessageException ex) {
            return msgCode;
        }

    }

    /**
     * Handle Exception and SQLException
     *
     * @return
     */

    @ExceptionHandler({SQLException.class, Exception.class})
    public ResponseEntity<Object> handleException() {
        ResponseData responseDTO = new ResponseData();
        responseDTO.setErrorCode(com.techasians.doctor.common.utils.Constants.ERROR_CODE.SYSTEM_ERROR);
        responseDTO.setMessage(translate(Constants.MESSAGE_SYSTEM_ERROR));
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ResponseData> handleResourceNotFoundException(ResourceNotFoundException ex) {
        ResponseData responseDTO = new ResponseData();
        responseDTO.setErrorCode(com.techasians.doctor.common.utils.Constants.ERROR_CODE.VALIDATE_FAIL);
        responseDTO.setMessage(translate(ex.getMessage()));
        return ResponseEntity.ok(responseDTO);
    }


    @ExceptionHandler(FacebookOAuthException.class)
    public ResponseEntity<ResponseData> handleFacebookOAuthException(FacebookOAuthException FacebookOAuthException) {
        ResponseData responseDTO = new ResponseData();
        responseDTO.setErrorCode(com.techasians.doctor.common.utils.Constants.ERROR_CODE.VALIDATE_FAIL);
        responseDTO.setMessage(translate(Constants.MESSAGE_INVALID_ACCESS_TOKEN_FACEBOOK));
        return ResponseEntity.ok(responseDTO);
    }

    @ExceptionHandler(Google0AuthException.class)
    public ResponseEntity<ResponseData> handleGoogle0AuthException(Google0AuthException google0AuthException) {
        ResponseData responseDTO = new ResponseData();
        responseDTO.setErrorCode(com.techasians.doctor.common.utils.Constants.ERROR_CODE.VALIDATE_FAIL);
        responseDTO.setMessage(translate(Constants.MESSAGE_INVALID_ACCESS_TOKEN_GOOGLE));
        return ResponseEntity.ok(responseDTO);
    }
    @ExceptionHandler(DuplicateTokenException.class)
    public ResponseEntity<ResponseData> handleGoogle0AuthException(DuplicateTokenException duplicateTokenException) {
        ResponseData responseDTO = new ResponseData();
        responseDTO.setErrorCode(com.techasians.doctor.common.utils.Constants.ERROR_CODE.VALIDATE_FAIL);
        responseDTO.setMessage(translate(duplicateTokenException.getMessage()));
        return ResponseEntity.ok(responseDTO);
    }
    @ExceptionHandler(InsufficientAuthenticationException.class)
    public ResponseEntity<ResponseData> handleInsufficientAuthenticationException(InsufficientAuthenticationException ex) {
        ResponseData responseDTO = new ResponseData();
        responseDTO.setErrorCode("4");
        responseDTO.setMessage("Bạn không có quyền truy cập");
        return ResponseEntity.ok(responseDTO);
    }
}
