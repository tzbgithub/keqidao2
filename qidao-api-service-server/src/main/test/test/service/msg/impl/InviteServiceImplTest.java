package service.msg.impl;


import com.qidao.application.App;
import com.qidao.application.service.invite.InviteService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class InviteServiceImplTest {

    @Autowired
    private InviteService inviteService;

    @Resource
    private RedissonClient redissonClient;

    @Test
    public void shortUrlTest(){

    }

    @Test
    public void redisTest(){

    }

}
