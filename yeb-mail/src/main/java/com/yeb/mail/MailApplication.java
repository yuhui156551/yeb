package com.yeb.mail;

import com.yeb.Utils.MailConstants;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

/**
 * @author yuhui
 * @date 2023/4/15 13:24
 */
// 报错： 'url' attribute is not specified and no embedded datasource could be configured.
// 说明需要配置数据源，是因为继承了 serve，于是把数据源相关配置类排除
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MailApplication {

    public static void main(String[] args) {
        SpringApplication.run(MailApplication.class, args);
    }

    /**
     * 绑定队列
     * @return
     */
    @Bean
    public Queue queue() {
        return new Queue("mail.welcome");
    }
}
