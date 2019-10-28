package ael.example.security.gateway;

import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.DatabasePopulator;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ael.example.security.common.JwtAuthenticationConfig;
import ael.example.security.common.JwtTokenUtil;
import ael.example.security.gateway.config.JwtAuthenticationEntryPoint;
import ael.example.security.gateway.config.JwtTokenAuthenticationFilter;
import ael.example.security.gateway.service.JwtUserDetailsService;

/**
 * Config role-based auth.
 *
 * @author ael 2019/10/18
 */
//@PropertySource({ "classpath:persistence.properties" })
//@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationConfig config;

    @Autowired
   	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
	private JwtUserDetailsService jwtUserDetailsService;
    @Autowired
	private JwtTokenUtil jwtTokenUtil;
    
//    @Autowired
//    private Environment env;
//    
//    @Value("classpath:schema.sql")
//    private Resource schemaScript;
//    @Value("classpath:data.sql")
//    private Resource dataScript;
    
    //déclarer le bean pour utiliser l encodage des PassWord
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }

    @Bean
    public JwtUserDetailsService userService() {
        return new JwtUserDetailsService();
    }
    
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }
    
   
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
     // We don't need CSRF for this example
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
             // make sure we use stateless session; session won't be used to store user's state.
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                    .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                    .exceptionHandling().authenticationEntryPoint(
//                            (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                	// Add a filter to validate the tokens with every request
                    .addFilterAfter(new JwtTokenAuthenticationFilter(config,jwtUserDetailsService,jwtTokenUtil ),
                            UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
             // dont authenticate this particular request
                    .antMatchers("/login","/authenticate","/register","/backend/guest","/microservice-commandes","/microservice-paiement","/microservice-produits").permitAll()
                    .antMatchers("/backend/admin").hasRole("ADMIN")
                    .antMatchers("/backend/user").hasRole("USER");
    }
}

