package test.service.msg.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.qidao.application.App;
import com.qidao.application.model.config.exception.BusinessException;
import com.qidao.application.model.msg.MsgSendDTO;
import com.qidao.application.model.msg.enums.MsgExceptionEnum;
import com.qidao.application.service.msg.MsgSendService;
import com.qidao.application.service.msg.impl.QidaoNoticeMsgSendImpl;
import com.qidao.application.service.official.WxchatOfficalService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class AuroraMsgSendImplTest {
    @Autowired
    @Qualifier("QidaoNoticeMsgSendImpl")
    private MsgSendService noticeService;
    @Autowired
    WxchatOfficalService wxchatOfficalService;
    @Autowired
    QidaoNoticeMsgSendImpl qidaoNoticeMsgSend;


    @Test
    public void sendTestSimple(){
        noticeService.send(MsgSendDTO.builder()
                .contentType(0)
                .id(135047919239169L)
                .receivers(Arrays.asList("143168909672449"))
//                .receivers(Arrays.asList("17630933478"))
//                .receivers(null)
                .contents(Arrays.asList("来自服务器的通知测试 2 -》内容"))
                .title(Arrays.asList("来自服务器的通知测试 2 -标题"))
                .build());
    }
    @Test
    public void sendNullMsgTest() {
//        Assert.assertThrows(BusinessException.class,()->{
//            noticeService.send(MsgSendDTO.builder()
//                    .contentType(-1)
//                    .contents(null)
//                    .receivers(null)
//                    .build());
//        });
    }
    @SneakyThrows
    @Test
    public void sendTestTimer() {
//        LocalDateTime sendTime = LocalDateTime.now().plusSeconds(20);
//        log.info("EmailMsgSendImplTest -> sendTestNullTimer -> sendTime -> {}",sendTime);
//        phoneMsgSend.send(MsgSendDTO.builder()
//                .contentType(1)
////                .title(Arrays.asList("定时器发送测试： " + sendTime))
////                .contents(Arrays.asList("这是由定时器设置的定时改善 简单内容 " + sendTime))
//                .receivers(Arrays.asList("15551385182","18721204790"))
//                .sendTime(sendTime)
//                .build());
//        Thread.sleep(1000 * 30);
//        log.info("EmailMsgSendImplTest -> sendTestNullTimer -> sleep over -> 请检查短信");

    }

    @Test
    public void  test(){
       /* MsgSendDTO msgSendDTO = new MsgSendDTO();
        ArrayList<String> stringArraysList = new ArrayList<String>();
        stringArraysList.add("2342342323r3");
        stringArraysList.add("23242342223");
        stringArraysList.add("234432342223");
        stringArraysList.add("234423234423");
        stringArraysList.add("23432243423");
        msgSendDTO.setReceivers(stringArraysList);
        qidaoNoticeMsgSend.send(msgSendDTO);*/
    }



}
