package uttugseuja.lucklotteryserver.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import uttugseuja.lucklotteryserver.global.property.JwtProperties;
import uttugseuja.lucklotteryserver.global.property.OauthProperties;

@EnableConfigurationProperties({OauthProperties.class, JwtProperties.class})
@Configuration
public class ConfigurationPropertiesConfig {}