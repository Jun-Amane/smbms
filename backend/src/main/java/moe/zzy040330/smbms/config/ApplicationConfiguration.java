/**
 * Package: moe.zzy040330.smbms.config
 * File: ApplicationConfiguration.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 14:57
 * Description: Configuration class for Spring Security authentication and core application settings.
 * Provides beans for user authentication, password encoding, and security management.
 */
package moe.zzy040330.smbms.config;

import moe.zzy040330.smbms.entity.SecurityUser;
import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration class that sets up core application components for authentication and security.
 * This class configures the authentication provider, user details service, password encoder,
 * and authentication manager for the application's security framework.
 */
@Configuration
public class ApplicationConfiguration {
    private final UserMapper userMapper;

    public ApplicationConfiguration(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    /**
     * Creates a UserDetailsService bean that loads user-specific data.
     * This service retrieves user information from the database using the user code
     * and converts it into a SecurityUser object for Spring Security.
     *
     * @return UserDetailsService implementation that loads user-specific data
     */
    @Bean
    public UserDetailsService userDetailsService() {
        return code -> {
            User user = userMapper.findByCode(code);
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }
            return new SecurityUser(user);
        };
    }

    /**
     * Creates a BCryptPasswordEncoder bean for password hashing.
     * This encoder is used to hash passwords before storing them in the database
     * and to verify passwords during authentication.
     *
     * @return BCryptPasswordEncoder instance for password encryption
     */
    @Bean
    BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Creates an AuthenticationManager bean using the provided configuration.
     * The AuthenticationManager is responsible for processing authentication requests.
     *
     * @param config the authentication configuration to use
     * @return configured AuthenticationManager instance
     * @throws Exception if there's an error creating the AuthenticationManager
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    /**
     * Creates an AuthenticationProvider bean that handles authentication processes.
     * This provider uses the UserDetailsService for retrieving user data and
     * BCryptPasswordEncoder for password verification.
     *
     * @return configured DaoAuthenticationProvider instance
     */
    @Bean
    AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}