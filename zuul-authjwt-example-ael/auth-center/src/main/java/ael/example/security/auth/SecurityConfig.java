package ael.example.security.auth;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ael.example.security.auth.configure.JwtAuthenticationEntryPoint;
import ael.example.security.common.JwtAuthenticationConfig;
import ael.example.security.common.JwtTokenUtil;
import ael.example.security.common.JwtUsernamePasswordAuthenticationFilter;

/**
 * Config login authentication.
 *
 * @author ael
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired 
    private JwtAuthenticationConfig config;
    
    @Autowired
	private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    
    @Autowired
	private UserDetailsService jwtUserDetailsService;

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }
    
    @Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
    
    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil();
    }
    
    @Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
    // 1er methode stockage des users en memory
//.withUser("john").password(passwordEncoder.encode("123")).roles("USER").and()
//    @Autowired
//    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
//        auth.inMemoryAuthentication()
//                .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN", "USER").and()
//                .withUser("ael").password(passwordEncoder.encode("ael")).roles("USER");
//    }

    
    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// configure AuthenticationManager so that it knows from where to load
		// user for matching credentials
		// Use BCryptPasswordEncoder
		auth.userDetailsService(jwtUserDetailsService).passwordEncoder(passwordEncoder());
	}
    
//    1er methode de générer le token on passe par spring configue
//    @Override
//    protected void configure(HttpSecurity httpSecurity) throws Exception {
//        httpSecurity
//     // We don't need CSRF for this example
//                .csrf().disable()
//                .logout().disable()
//                .formLogin().disable()
//                  // make sure we use stateless session; session won't be used to store user's state.
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                    .anonymous()
//                .and()
////                    .exceptionHandling().authenticationEntryPoint(
////                            (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
//                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
//                .and()
//                //1er methode de générer le token on passe par spring configue >>JwtUsernamePasswordAuthenticationFilter
//                    .addFilterAfter(new JwtUsernamePasswordAuthenticationFilter(config, authenticationManager()),
//                            UsernamePasswordAuthenticationFilter.class)
//                .authorizeRequests()
//                // dont authenticate this particular request
//                    .antMatchers("/login","/authenticate","/register").permitAll()
//        // all orther  requests need to be authenticated because we routing juste to authent by gateway juste request to be authentified
//                    .anyRequest().authenticated();
//    }
//    
    //2 methode
    @Override
	protected void configure(HttpSecurity httpSecurity) throws Exception {
		// We don't need CSRF for this example
		httpSecurity.csrf().disable()
				// dont authenticate this particular request
				.authorizeRequests().antMatchers("/login","/authenticate","/register").permitAll().
				// all other requests need to be authenticated
				anyRequest().authenticated().and().
				// make sure we use stateless session; session won't be used to
				// store user's state.
				exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint).and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS);

		// Add a filter to validate the tokens with every request used by ZUUL
		//httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
    
}

