import com.example.springsecurity.SpringSecurityApplication;
import com.example.springsecurity.mapper.MenuMapper;
import com.example.springsecurity.model.Menu;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;


@SpringBootTest(classes = SpringSecurityApplication.class)
public class SpringSecurityTest {
    @Resource(name = "MenuMapper")
    private MenuMapper mapper;

    @Test
    void test(){
        List<Menu> menuByRole = mapper.getMenuByRole();
        System.out.println(menuByRole);
    }
}
