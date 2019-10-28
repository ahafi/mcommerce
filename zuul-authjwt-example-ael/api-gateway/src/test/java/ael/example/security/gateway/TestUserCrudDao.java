package ael.example.security.gateway;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import ael.example.security.gateway.dao.UserDao;
import ael.example.security.gateway.model.DAOUser;
import ael.example.security.gateway.service.JwtUserDetailsService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestUserCrudDao {
	
    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;
    
    @Autowired
	private UserDao userDao;

    @Test
    public void testHello1() {
        List<DAOUser> users = (List<DAOUser>) userDao.findAll();
        assertTrue(users!=null&&users.size()>0);
    }


}