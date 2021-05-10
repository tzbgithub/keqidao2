package test.service.msg.impl;

import com.qidao.application.App;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.service.msg.MsgSendService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
public class QidaoNoticeMsgSendImplTest {
    @Autowired
    @Qualifier("QidaoNoticeMsgSendImpl")
    private MsgSendService emailMsgSend;

    @SneakyThrows
    @Test
    public void sendTestNullReceiverSimple(){
        int loopNum = 1000;
        int waitSecond = 5;
        LocalDateTime sendTime = LocalDateTime.now().plusSeconds(waitSecond);
        for(int i=0;i<loopNum;i++) {
            emailMsgSend.send(MsgSendDTO.builder()
                    .contentType(0)
                    .sendTime(sendTime)
                    .contents(Arrays.asList("这是简单内容"))
                    .receivers(Arrays.asList("autuan.yu@gmail.com"))
                    .build());
        }
//        Thread.sleep(( waitSecond + 2) * 1000);
        log.info("test -> done");
    }
}
