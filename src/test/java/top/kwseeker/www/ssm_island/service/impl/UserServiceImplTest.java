package top.kwseeker.www.ssm_island.service.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import top.kwseeker.www.ssm_island.service.IUserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml","classpath:applicationContext-datasource.xml"})
@WebAppConfiguration
public class UserServiceImplTest {

    @Autowired
    private IUserService iUserService;

    @Test
    public void updatePasswordByEmail() throws Exception {
        String email = "alice@gmail.com";
        String password = "1234561";
        iUserService.updatePasswordByEmail(email, password);
    }

}