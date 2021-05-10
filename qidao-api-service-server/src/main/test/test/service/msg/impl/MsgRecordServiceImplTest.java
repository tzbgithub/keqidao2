package test.service.msg.impl;


import com.qidao.application.App;
import com.qidao.application.service.msg.impl.MsgRecordServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class MsgRecordServiceImplTest {

    @Autowired
    private MsgRecordServiceImpl msgRecordService;


    @Test
    public void test(){
        List<Long> longs = msgRecordService.insertMsgRecordByReceiveType(153675580571649L, 0);
        System.out.println(longs.size());
    }

}
