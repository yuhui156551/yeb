package com.yeb.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页公共返回对象
 * 
 * @author yuhui
 * @date 2023/4/14 17:33
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespPage {

    /**
     * 总条数
     */
    private Long total;
    /**
     * 数据
     */
    private List<?> data;
}
