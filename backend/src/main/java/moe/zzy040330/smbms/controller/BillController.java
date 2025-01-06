/**
 * Package: moe.zzy040330.smbms.controller
 * File: BillController.java
 * Author: Mingxue Li & Xiaoteng Ma
 * Date: 05/01/2025
 * Description: RESTful API for bill entity.
 */
package moe.zzy040330.smbms.controller;

import moe.zzy040330.smbms.dto.ErrorResponse;
import moe.zzy040330.smbms.entity.Bill;
import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.service.BillService;
import moe.zzy040330.smbms.service.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bill")
public class BillController {

    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }
//
//    @PreAuthorize("hasRole('SMBMS_ADMIN') or hasRole('SMBMS_USER')")
//    @GetMapping("")
//    public ResponseEntity<?> getBillList(@RequestParam(value = "productName", required = false) String productName,
//                                         @RequestParam(value = "providerId", required = false) Long providerId,
//                                         @RequestParam(value = "isPaid", required = false) Boolean isPaid,
//                                         @RequestParam("pageSize") Integer pageSize,
//                                         @RequestParam("pageIndex") Integer pageIndex) {
//        try {
//            Bill condition = new Bill();
//            condition.setProductName(productName);
//            if (providerId != null) {
//                condition.setProvideId(providerId);
//            }
//            condition.setIsPaid(isPaid);
//
//            var pageInfo = billService.getBillList(condition, pageIndex, pageSize);
//
//            Map<String, Object> response = new HashMap<>();
//            response.put("totalItems", pageInfo.getTotal());
//            response.put("curPage", pageInfo.getPageNum());
//            response.put("totalPages", pageInfo.getPages());
//            response.put("pageSize", pageInfo.getPageSize());
//            response.put("bills", pageInfo.getList());
//
//            return ResponseEntity.ok(response);
//        } catch (Exception e) {
//            logger.error("Error fetching bills: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ErrorResponse(500, "Internal server error"));
//        }
//    }
//
//    @PreAuthorize("hasRole('SMBMS_ADMIN')")
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getBillById(@PathVariable Long id) {
//        try {
//            var bill = billService.findById(id);
//
//            if (bill == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(new ErrorResponse(404, "Bill not found"));
//            }
//
//            return ResponseEntity.ok(bill);
//        } catch (Exception e) {
//            logger.error("Error fetching bill: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ErrorResponse(500, "Internal server error"));
//        }
//    }
//
//    @PreAuthorize("hasRole('SMBMS_ADMIN')")
//    @PostMapping("")
//    public ResponseEntity<?> addBill(@RequestBody Bill bill, @RequestHeader("Authorization") String authHeader) {
//        try {
//            String token = authHeader.substring(7);
//
//            if (bill.getProductName() == null || bill.getProductName().isBlank() ||
//                    bill.getProvideId() == null ||
//                    bill.getTotalPrice() == null) {
//                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
//                        .body(new ErrorResponse(400, "Invalid input: cannot be null or empty"));
//            }
//
//            var createdBy = new User();
//            createdBy.setId(jwtService.extractUserId(token));
//            boolean success = billService.insert(bill, createdBy, new Date());
//
//            if (success) {
//                return ResponseEntity.ok("Bill added successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(new ErrorResponse(500, "Internal server error"));
//            }
//        } catch (Exception e) {
//            logger.error("Error adding bill: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ErrorResponse(500, "Internal server error"));
//        }
//    }
//
//    @PreAuthorize("hasRole('SMBMS_ADMIN')")
//    @PutMapping("/{id}")
//    public ResponseEntity<?> updateBill(@PathVariable Long id, @RequestBody Bill bill, @RequestHeader("Authorization") String authHeader) {
//        try {
//            String token = authHeader.substring(7);
//
//            bill.setId(id);
//            var existingBill = billService.findById(id);
//
//            if (existingBill == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(new ErrorResponse(404, "Bill not found"));
//            }
//
//            var modifiedBy = new User();
//            modifiedBy.setId(jwtService.extractUserId(token));
//            boolean success = billService.update(bill, modifiedBy, new Date());
//
//            if (success) {
//                return ResponseEntity.ok("Bill updated successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                        .body(new ErrorResponse(500, "Internal server error"));
//            }
//        } catch (Exception e) {
//            logger.error("Error updating bill: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ErrorResponse(500, "Internal server error"));
//        }
//    }
//
//    @PreAuthorize("hasRole('SMBMS_ADMIN')")
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteBill(@PathVariable Long id) {
//        try {
//            boolean success = billService.deleteById(id);
//
//            if (success) {
//                return ResponseEntity.ok("Bill deleted successfully");
//            } else {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND)
//                        .body(new ErrorResponse(404, "Bill not found"));
//            }
//        } catch (Exception e) {
//            logger.error("Error deleting bill: {}", e.getMessage());
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body(new ErrorResponse(500, "Internal server error"));
//        }
//    }
}
