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
public class PhoneMsgSendImplTest {
    @Autowired
    @Qualifier("PhoneMsgSendImpl")
    private MsgSendService phoneMsgSend;


    @Test
    public void sendTestSimple(){
        phoneMsgSend.send(MsgSendDTO.builder()
                .contentType(1)
                .receivers(Arrays.asList("15551385182"))
                .build());
    }
    @Test
    public void sendNullMsgTest() {
        Assert.assertThrows(BusinessException.class,()->{
            phoneMsgSend.send(MsgSendDTO.builder()
                    .contentType(-1)
                    .contents(null)
                    .receivers(null)
                    .build());
        });
    }
    @SneakyThrows
    @Test
    public void sendTestTimer() {
        LocalDateTime sendTime = LocalDateTime.now().plusSeconds(20);
        log.info("EmailMsgSendImplTest -> sendTestNullTimer -> sendTime -> {}",sendTime);
        phoneMsgSend.send(MsgSendDTO.builder()
                .contentType(1)
//                .title(Arrays.asList("定时器发送测试： " + sendTime))
//                .contents(Arrays.asList("这是由定时器设置的定时改善 简单内容 " + sendTime))
                .receivers(Arrays.asList("15551385182","18721204790"))
                .sendTime(sendTime)
                .build());
//        Thread.sleep(1000 * 30);
        log.info("EmailMsgSendImplTest -> sendTestNullTimer -> sleep over -> 请检查短信");

    }
}
