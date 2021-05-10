package service.msg.impl;

import com.qidao.application.App;
import com.qidao.application.model.config.DynamicIndustryRes;
import com.qidao.application.service.config.SelectConfigService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = App.class)
@Slf4j
public class SelectImplTest {

    @Autowired
    private SelectConfigService selectConfigService;

    @Test
    public void industry(){
        DynamicIndustryRes dynamicIndustry = selectConfigService.getDynamicIndustry();
        System.out.println(dynamicIndustry);
    }
}
