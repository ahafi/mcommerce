package ael.example.security.common;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;

/**
 * Config JWT.
 * Only one property 'shuaicj.security.jwt.secret' is mandatory.
 *
 * @author ael 2019/10/18
 */
@Getter
@ToString
public class JwtAuthenticationConfig {

    @Value("${ael.security.jwt.url:/login}")
    private String url;

    @Value("${ael.security.jwt.header:Authorization}")
    private String header;

    @Value("${ael.security.jwt.prefix:Bearer}")
    private String prefix;

    @Value("${ael.security.jwt.expiration:#{24*60*60}}")
    private int expiration; // default 24 hours

    @Value("${ael.security.jwt.secret}")
    private String secret;
}
