/**
 * Package: moe.zzy040330.smbms.entity
 * File: SecurityUser.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 15:04
 * Description: The SecurityUser class is a Spring Security utility that adapts the custom
 * User entity to the UserDetails interface. It facilitates integration with
 * Spring Security by providing user credentials and roles for authentication
 * and authorization processes.
 */
package moe.zzy040330.smbms.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * A record that implements UserDetails to provide necessary user information
 * to Spring Security. It wraps a User entity and exposes its details in a
 * manner that is consumable by Spring Security's authentication mechanisms.
 */
public record SecurityUser(User user) implements UserDetails {

    /**
     * Returns the authorities granted to the user. The authorities are
     * represented as a collection of GrantedAuthority objects, generally
     * representing user roles.
     *
     * @return a collection of GrantedAuthority representing the user's roles
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getCode()));
    }

    /**
     * Retrieves the ID used to identify the user.
     *
     * @return the userId
     */
    public Long getId() {
        return user.getId();
    }

    /**
     * Retrieves the username used to authenticate the user. In this context,
     * it returns the user's unique code.
     *
     * @return the username (code) of the user
     */
    @Override
    public String getUsername() {
        return user.getCode();
    }

    /**
     * Retrieves the password used to authenticate the user.
     *
     * @return the password of the user
     */
    @Override
    public String getPassword() {
        return user.getPassword();
    }

    // Additional methods required by UserDetails but not implemented in this record
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
