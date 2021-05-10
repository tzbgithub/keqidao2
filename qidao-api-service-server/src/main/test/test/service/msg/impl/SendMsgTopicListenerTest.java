package test.service.msg.impl;

import com.qidao.application.App;
import com.qidao.application.service.redis.mq.impl.SendMsgTopicListener;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class SendMsgTopicListenerTest {
    @Autowired
    private SendMsgTopicListener sendMsgTopicListener;

    @Test
    public void industry() {
        long l = System.currentTimeMillis();
        sendMsgTopicListener.onMessage("test", 153906680430593L);
        System.out.println(System.currentTimeMillis() - l);
    }
}
