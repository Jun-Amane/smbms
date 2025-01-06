/**
 * Package: moe.zzy040330.smbms.controller
 * File: BillController.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 10:41
 * Description: RESTful API for bill and provider entities.
 */
package moe.zzy040330.smbms.controller;

import moe.zzy040330.smbms.dto.ErrorResponse;
import moe.zzy040330.smbms.entity.Bill;
import moe.zzy040330.smbms.entity.Provider;
import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.service.BillService;
import moe.zzy040330.smbms.service.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/bill")
public class BillController {

    private static final Logger logger = LoggerFactory.getLogger(BillController.class);

    private final BillService billService;
    private final ProviderService providerService;

    public BillController(BillService billService, ProviderService providerService) {
        this.billService = billService;
        this.providerService = providerService;
    }

    @GetMapping("")
    @PreAuthorize("hasRole('SMBMS_ADMIN')")
    public ResponseEntity<?> apiBillGet(@RequestParam(value = "queryProductName") String productName,
                                        @RequestParam(value = "queryProviderId") Long providerId,
                                        @RequestParam(value = "queryIsPaid") Boolean isPaid,
                                        @RequestParam(value = "pageIndex") Integer pageIndex,
                                        @RequestParam(value = "pageSize") Integer pageSize) {
        try {
            var pageInfo = this.billService.getBillList(new Bill() , pageIndex, pageSize);

            Map<String, Object> response = new HashMap<>();
            response.put("totalItems", pageInfo.getTotal());
            response.put("curPage", pageInfo.getPageNum());
            response.put("totalPages", pageInfo.getPages());
            response.put("pageSize", pageInfo.getPageSize());
            response.put("bills", pageInfo.getList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('SMBMS_ADMIN')")
    public ResponseEntity<?> apiBillIdGet(@PathVariable Long id) {
        try {
            Bill bill = billService.findById(id);
            if (bill == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "Bill not found"));
            }
            return ResponseEntity.ok(bill);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('SMBMS_ADMIN')")
    public ResponseEntity<?> apiBillIdDelete(@PathVariable Long id) {
        try {
            boolean deleted = billService.deleteById(id);
            if (deleted) {
                return ResponseEntity.ok("Bill deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "Bill not found"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SMBMS_ADMIN')")
    public ResponseEntity<?> apiBillIdPut(@PathVariable Long id, @RequestBody Bill bill) {
        try {
            if (id == null || bill == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: cannot be null"));
            }

            bill.setId(id);
            boolean updated = billService.update(new Bill(),new User(),new Date());

            if (updated) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body("Bill modified successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "Bill not found"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @PostMapping("")
    @PreAuthorize("hasRole('SMBMS_ADMIN')")
    public ResponseEntity<?> apiBillPost(@RequestBody Bill bill) {
        try {
            if (bill == null || bill.getProductName() == null || bill.getTotalPrice() == null || bill.getProductName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: required fields are missing"));
            }

            boolean created = billService.insert(new Bill(),new User(),new Date());
            if (created) {
                return ResponseEntity.status(HttpStatus.CREATED).body("Bill added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse(500, "Internal server error"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @GetMapping("/providerlist")
    @PreAuthorize("hasRole('SMBMS_ADMIN')")
    public ResponseEntity<?> apiBillProviderListGet() {
        try {
            List<Provider> providerList = (List<Provider>) providerService.getProviderList("L","a",2,12);
            return ResponseEntity.ok(providerList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }
}
