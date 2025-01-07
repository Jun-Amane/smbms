/**
 * Package: moe.zzy040330.smbms.controller
 * File: DashboardController.java
 * Author: Ziyu ZHOU
 * Date: 07/01/2025
 * Time: 23:43
 * Description: RESTful API for Dashboard main page.
 */
package moe.zzy040330.smbms.controller;

import moe.zzy040330.smbms.dto.ErrorResponse;
import moe.zzy040330.smbms.service.DashboardService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    private static final Logger logger = LoggerFactory.getLogger(DashboardController.class);

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getDashboardStats() {
        try {
            var dto = dashboardService.getStats();

            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            logger.error("Error fetching provider list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }
}
