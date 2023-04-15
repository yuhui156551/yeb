package com.yeb.exception;

import com.yeb.domain.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 * 
 * @author yuhui
 * @date 2023/4/7 17:15
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 数据库异常
     */
    @ExceptionHandler(SQLException.class)
    public RespBean mySqlException(SQLException e) {
        if (e instanceof SQLIntegrityConstraintViolationException) {
            return RespBean.error("该数据有关联数据，操作失败！");
        }
        return RespBean.error("数据库异常，操作失败！");
    }

    /**
     * 其他异常
     */
    @ExceptionHandler(RuntimeException.class)
    public RespBean exceptionHandler(RuntimeException e) {
        // 打印异常信息
        log.error("出现了异常！", e);
        // 从异常对象中获取提示信息封装返回
        return RespBean.error(e.getMessage());
    }
}
