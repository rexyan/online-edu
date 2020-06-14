package com.yanrs.edu.teacher.handler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 自定义异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EduException extends RuntimeException{
    private Integer code;  // 状态码
    private String message;  // 错误描述信息
}
