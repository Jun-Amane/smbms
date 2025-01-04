/**
 * Package: moe.zzy040330.smbms.service
 * File: AuthenticationServiceTest.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 15:42
 * Description: Testing login logic
 */
package moe.zzy040330.smbms.service;

import moe.zzy040330.smbms.dto.LoginRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;



    @Test
    public void testLogin() {
        var req = new LoginRequest("admin", "1234567");
        var resp = authenticationService.authenticate(req);

        assertNotNull(resp);
        assertEquals("admin", resp.getUsername());

    }


}