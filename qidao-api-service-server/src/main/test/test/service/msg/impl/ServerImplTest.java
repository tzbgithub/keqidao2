package service.msg.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.qidao.application.App;
import com.qidao.application.model.server.ServerListReq;
import com.qidao.application.model.server.ServerPageRes;
import com.qidao.application.service.server.ServerService;
import com.qidao.framework.service.ServicePage;
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
public class ServerImplTest {

    @Autowired
    private ServerService serverService;

    @Test
    public void serverList(){
        ServerListReq req = new ServerListReq();
        req.setIndustryId(null);
        req.setSpecAreaId(null);
        req.setType(0);
        req.setOffset(1);
        req.setLimit(1000);
        req.setMemberId(123L);
        ServicePage<List<ServerPageRes>> serverPage = serverService.getServerPage(req);
        String s = JSONUtil.toJsonStr(serverPage);
        System.out.println(s);
    }
}
