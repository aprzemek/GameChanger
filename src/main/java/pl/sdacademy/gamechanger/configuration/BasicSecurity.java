package pl.sdacademy.gamechanger.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pl.sdacademy.gamechanger.service.AppUserDetailsService;

@Configuration
@EnableWebSecurity
public class BasicSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private AppUserDetailsService appUserDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers(
                "/",
                "/home**"
                ).permitAll()
                .anyRequest()
                .authenticated()
                .and().formLogin()
                .loginPage("/user/login")
                .defaultSuccessUrl("/home",true)
                .permitAll()
                .and().logout()
                .clearAuthentication(true)
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/home")
                .invalidateHttpSession(true);





    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();

            daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());
            daoAuthenticationProvider.setUserDetailsPasswordService(appUserDetailsService);


                 return daoAuthenticationProvider;



    }

        @Override
        protected void configure (AuthenticationManagerBuilder auth) throws Exception{
            auth.authenticationProvider(daoAuthenticationProvider());


    }

}
