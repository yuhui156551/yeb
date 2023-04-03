package com.yeb.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yeb.domain.pojo.MailLog;
import com.yeb.mapper.MailLogMapper;
import com.yeb.service.IMailLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yuhui
 * @since 2023-04-02
 */
@Service
public class MailLogServiceImpl extends ServiceImpl<MailLogMapper, MailLog> implements IMailLogService {

}
