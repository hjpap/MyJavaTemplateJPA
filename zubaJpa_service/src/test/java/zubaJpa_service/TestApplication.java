package zubaJpa_service;



import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.WebIntegrationTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.wei.zuba.ZubaServiceApplication;
import com.wei.zuba.entity.User;
import com.wei.zuba.test.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ZubaServiceApplication.class)
@WebIntegrationTest({"server.port=9000", "management.port=0"})
public class TestApplication {
	
	@Autowired
	UserService userService;
	
    @SuppressWarnings("unused")
	@Test
    public void find() throws Exception {
    	List<User> list = userService.findUser();
    	System.out.println(list.size());
    }
    
    @Test
    public void sava() throws Exception {
    	User user = new User();
    	user.setEmail("xxx@ax.com");
    	userService.saveUser(user);
    }
}
