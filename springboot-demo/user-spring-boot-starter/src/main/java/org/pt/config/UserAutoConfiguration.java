package org.pt.config;

import org.pt.service.UserService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;



@Configuration
@ConditionalOnClass({User.class})
@EnableConfigurationProperties({User.class})
public class UserAutoConfiguration {

    private final User user;

    public UserAutoConfiguration(User user) {
        this.user = user;
    }

    @Bean
    @ConditionalOnMissingBean
    public UserService userService(){
        UserService userService = new UserService();
        userService.setEmail(user.getEmail());
        userService.setAge(user.getAge());
        userService.setName(user.getName());
        return  userService;
    }

    @Bean
    @ConditionalOnMissingBean
    public User user(){
        return new User();
    }


}
