package test.service.msg.impl;


import com.qidao.application.App;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.service.msg.MsgSendService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
class EmailMsgSendImplTest {
    @Autowired
    @Qualifier("EmailMsgSendImpl")
    private MsgSendService emailMsgSend;


    @Test
    public void sendAttachmentsMail(){
        MsgSendDTO param = MsgSendDTO.builder()
                .contentType(2)
                .title(Arrays.asList("附件标题"))
                .contents(Arrays.asList("附件内容"))
                .receivers(Arrays.asList("autuan.yu@gmail.com"))
                .build();
        emailMsgSend.send(param);
        param.setContentType(0);
        param.setTitle(Arrays.asList("简单内容标题"));
        emailMsgSend.send(param);
    }

    @Test
    public void sendTestSimple(){
        emailMsgSend.send(MsgSendDTO.builder()
                .contentType(0)
                .title(Arrays.asList("这是标题"))
                .contents(Arrays.asList("这是简单内容"))
                .receivers(Arrays.asList("autuan.yu@gmail.com"))
                .build());
    }
    @Test
    public void sendTestNullMsg() {
        Assert.assertThrows(BusinessException.class,()->{
            emailMsgSend.send(MsgSendDTO.builder()
                    .contentType(-1)
                    .contents(null)
                    .receivers(null)
                    .build());
        });
    }
    @SneakyThrows
    @Test
    public void sendTestNullTimer() {
        LocalDateTime sendTime = LocalDateTime.now().plusSeconds(20);
        log.info("EmailMsgSendImplTest -> sendTestNullTimer -> sendTime -> {}",sendTime);
        emailMsgSend.send(MsgSendDTO.builder()
                .contentType(0)
                .title(Arrays.asList("定时器发送测试： " + sendTime))
                .contents(Arrays.asList("这是由定时器设置的定时改善 简单内容 " + sendTime))
                .receivers(Arrays.asList("autuan.yu@gmail.com"))
                .sendTime(sendTime)
                .build());
//        Thread.sleep(1000 * 25);
        log.info("EmailMsgSendImplTest -> sendTestNullTimer -> sleep over -> 请检查邮件");

    }
}