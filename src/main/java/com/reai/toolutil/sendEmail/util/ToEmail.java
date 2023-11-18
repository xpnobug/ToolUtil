package com.reai.toolutil.sendEmail.util;

import com.reai.toolutil.Reais;
import com.reai.toolutil.sendEmail.config.MailConfig;
import com.reai.toolutil.sendEmail.constant.EmailContentTypeEnum;
import com.reai.toolutil.sendEmail.constant.SmtpHostEnum;
import com.reai.toolutil.sendEmail.core.MiniEmail;
import com.reai.toolutil.sendEmail.core.MiniEmailFactory;
import com.reai.toolutil.sendEmail.core.MiniEmailFactoryBuilder;
import com.reai.toolutil.sendEmail.service.FreemarkerView;
import java.util.List;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 86136
 */
@Service
public class ToEmail {

    /**
     * 该邮箱修改为你需要测试的收件邮箱地址
     */
    private static final String TO_EMAIL = "2877406366@qq.com";
    /**
     * 发送邮件给多个收件人
     */
    private static final String[] TO_EMAILS = new String[]{"669307582@qq.com", "1919101871@qq.com",
        "3325622872@qq.com"};

    MiniEmailFactory miniEmailFactory;

    @Autowired
    public FreemarkerView freemarkerView;

    @Test
    public void sendEmail() throws Exception {
        String html = freemarkerView.createHtml(1);
        // 创建工厂类
        miniEmailFactory = new MiniEmailFactoryBuilder().build(
            MailConfig.config(Reais.userName, Reais.emailPwd)
                .setMailDebug(Boolean.TRUE)
                .setSenderNickname("自动备份")
                .setMailSmtpHost(SmtpHostEnum.SMTP_QQ)
        );
        MiniEmail miniEmail = miniEmailFactory.init();
        List<String> sendSuccessToList = miniEmail
            .addBlindCarbonCopy(new String[]{TO_EMAIL})
            .send(TO_EMAILS, "自动备份通知", EmailContentTypeEnum.HTML, html);
//        System.out.println(sendSuccessToList);
    }
}
