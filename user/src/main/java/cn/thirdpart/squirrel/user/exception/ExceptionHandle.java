package cn.thirdpart.squirrel.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 该类主要是对异常做统一处理
 */
//@ControllerAdvice
//@ResponseBody
public class ExceptionHandle {

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleException(Exception e){
        return new ResponseEntity (e.getMessage () , HttpStatus.OK) ;
    }

}

