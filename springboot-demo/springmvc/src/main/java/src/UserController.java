package src;

/**
 * @ClassName UserController
 * @Author pt
 * @Description
 * @Date 2025/1/2 15:38
 **/
/* ------ UserController类 ------- */
@Controller
@RequestMapping("/user")
public class UserController {

    // 测试@ResponseBody的功效
    @RequestMapping("/get")
    @ResponseBody
    public User get(){
        return new User(1,"竹子爱熊猫","男",18);
    }

    // 跳转首页的方法
    @RequestMapping("/")
    public String test(){
        return "index";
    }

    // 测试重定向的功效
    @RequestMapping("/edit")
    public String toEdit(){
        return "redirect:edit";
    }

    public String TEST(){
        return null;
    }
}

