package com.yeb.mail;

import com.yeb.domain.pojo.Employee;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Date;


/**
 * 消息接收
 *
 * @author yuhui
 * @date 2023/4/15 14:07
 */
@Component
public class MailConsumer {

    private static final Logger log = LoggerFactory.getLogger(MailConsumer.class);

    @Autowired
    private MailProperties mailProperties;
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private TemplateEngine templateEngine;

    @RabbitListener(queues = "mail.welcome") // 监听 队列
    public void handler(Employee employee) {
        // 创建消息
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg);
        try {
            helper.setFrom(mailProperties.getUsername()); // 发件人
            helper.setTo(employee.getEmail()); // 收件人
            helper.setSubject("入职欢迎邮件"); // 主题
            helper.setSentDate(new Date()); // 发送日期
            // 邮件内容 <--> thymeleaf
            Context context = new Context();
            context.setVariable("name", employee.getName());
            context.setVariable("posName", employee.getPosition().getName());
            context.setVariable("joblevelName", employee.getJoblevel().getName());
            context.setVariable("departmentName", employee.getDepartment().getName());
            String mail = templateEngine.process("mail", context);
            // 处理 html 格式
            helper.setText(mail, true);
            // 发送邮件
            javaMailSender.send(msg);
            log.info("邮件发送成功！");
        } catch (Exception ex) {
            log.error("邮件发送失败 ======> {}", ex.getMessage());
        }
    }   
}
