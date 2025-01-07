/**
 * Package: moe.zzy040330.smbms.controller
 * File: BillController.java
 * Author: Mingxue Li
 * Date: 06/01/2025
 * Time: 17:25
 * Description: RESTful API for bill entity.
 */
package moe.zzy040330.smbms.controller;

import com.github.pagehelper.PageInfo;
import moe.zzy040330.smbms.dto.BillDto;
import moe.zzy040330.smbms.dto.ErrorResponse;
import moe.zzy040330.smbms.dto.ProviderDto;
import moe.zzy040330.smbms.entity.Bill;
import moe.zzy040330.smbms.entity.Provider;
import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.service.BillService;
import moe.zzy040330.smbms.service.JwtService;
import moe.zzy040330.smbms.service.ProviderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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
    private final JwtService jwtService;

    public BillController(BillService billService, ProviderService providerService, JwtService jwtService) {
        this.billService = billService;
        this.providerService = providerService;
        this.jwtService = jwtService;
    }

    @GetMapping("")
    public ResponseEntity<?> getBills(@RequestParam(value = "queryCode", required = false) String code,
                                      @RequestParam(value = "queryProductName", required = false) String productName,
                                      @RequestParam(value = "queryProductDesc", required = false) String productDesc,
                                      @RequestParam(value = "queryProviderCode", required = false) String providerCode,
                                      @RequestParam(value = "queryProviderName", required = false) String providerName,
                                      @RequestParam(value = "queryIsPaid", required = false) Integer isPaid,
                                      @RequestParam(value = "minQuantity",required = false) Integer minQuantity,
                                      @RequestParam(value = "maxQuantity",required = false) Integer maxQuantity,
                                      @RequestParam(value = "minPrice",required = false) Double minPrice,
                                      @RequestParam(value = "maxQuantity",required = false) Double maxPrice,
                                      @RequestParam(value = "pageIndex", defaultValue = "1") Integer pageIndex,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize

                                      )
    {
        try {

            PageInfo<Bill> pageInfo = billService.findBillByQuery(code, productName, productDesc, providerCode, providerName, isPaid, pageIndex, pageSize,minQuantity,maxQuantity,minPrice,maxPrice);
            Map<String, Object> response = new HashMap<>();
            response.put("totalItems", pageInfo.getTotal());
            response.put("curPage", pageInfo.getPageNum());
            response.put("totalPages", pageInfo.getPages());
            response.put("pageSize", pageInfo.getPageSize());
            response.put("bills", pageInfo.getList().stream().map(BillController::billObj2BillDto));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error("Error fetching bills", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBillById(@PathVariable Long id) {
        try {
            Bill bill = billService.findById(id);
            if (bill == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Bill not found"));
            }
            return ResponseEntity.ok(billObj2BillDto(bill));
        } catch (Exception e) {
            logger.error("Error fetching bill by ID", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    private static Bill billDto2BillObj(BillDto billDto) {
        Bill bill = new Bill();
        bill.setId(billDto.getId());
        bill.setCode(billDto.getCode());
        bill.setProductName(billDto.getProductName());
        bill.setProductDescription(billDto.getProductDescription());
        bill.setProductUnit(billDto.getProductUnit());
        bill.setProductCount(billDto.getProductCount());
        bill.setTotalPrice(billDto.getTotalPrice());
        bill.setIsPaid(billDto.getIsPaid());

        var provider = new Provider();
        provider.setId(billDto.getProviderId());
        bill.setProvider(provider);

        return bill;
    }

    //Long id, String billCode, String productName, String productDesc, String productUnit, BigDecimal totalPrice, Integer isPaid, Long providerId, String providerName,BigDecimal productCount
    // Long id, String code, String productName, String productDescription, String productUnit, String productCount, BigDecimal totalPrice, Integer isPaid, Long providerId
    private static BillDto billObj2BillDto(Bill Bill) {
        return new BillDto(
                Bill.getId(),
                Bill.getCode(),
                Bill.getProductName(),
                Bill.getProductDescription(),
                Bill.getProductUnit(),
                Bill.getProductCount(),
                Bill.getTotalPrice(),
                Bill.getIsPaid(),
                Bill.getProvider().getId()
        );
    }

    @PreAuthorize("hasAuthority('SMBMS_ADMIN') or hasAuthority('SMBMS_MANAGER')")
    @PutMapping("/{id}")
    public ResponseEntity<?> updateBill(@PathVariable Long id, @RequestBody BillDto billDto, @RequestHeader("Authorization") String authHeader) {
        try {
            if (id == null || billDto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: ID or Bill data is null"));
            }

            Bill existingBill = billService.findById(id);
            if (existingBill == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Bill not found"));
            }

            String token = authHeader.substring(7);

            Bill updatedBill = billDto2BillObj(billDto);

            User modifiedBy = new User();
            modifiedBy.setId(jwtService.extractUserId(token));
            updatedBill.setModifiedBy(modifiedBy);
            updatedBill.setCreatedBy(modifiedBy);
            updatedBill.setCreationDate(new Date());
            updatedBill.setModificationDate(new Date());

            boolean success = billService.update(updatedBill);
            if (success) {
                return ResponseEntity.noContent().build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse(500, "Failed to update bill"));
            }
        } catch (Exception e) {
            logger.error("Error updating bill", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @PreAuthorize("hasAuthority('SMBMS_ADMIN') or hasAuthority('SMBMS_MANAGER')")
    @PostMapping("")
    public ResponseEntity<?> createBill(@RequestBody BillDto billDto, @RequestHeader("Authorization") String authHeader) {
        try {
            if (billDto == null || billDto.getCode() == null || billDto.getProductName() == null
                    || billDto.getProductUnit() == null
                    || billDto.getTotalPrice() == null || billDto.getProductCount() == null
                    || billDto.getIsPaid() == null
                    || billDto.getProviderId() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: required fields are missing"));
            }

            String token = authHeader.substring(7);

            Bill newBill = billDto2BillObj(billDto);

            User modifiedBy = new User();
            modifiedBy.setId(jwtService.extractUserId(token));
            newBill.setModifiedBy(modifiedBy);
            newBill.setCreatedBy(modifiedBy);
            newBill.setCreationDate(new Date());
            newBill.setModificationDate(new Date());
            boolean success = billService.insert(newBill);
            if (success) {
                return ResponseEntity.status(HttpStatus.CREATED).build();
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse(500, "Failed to create bill"));
            }
        } catch (Exception e) {
            logger.error("Error creating bill", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @PreAuthorize("hasAuthority('SMBMS_ADMIN') or hasAuthority('SMBMS_MANAGER')")
    @DeleteMapping("{id}")
    public ResponseEntity<?> deleteBill(@PathVariable Long id) {
        try {
            Boolean deleted = billService.deleteById(id);
            if (!deleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Bill not found"));
            } else {
                return ResponseEntity.ok("Delete successful");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error" + e.getMessage()));
        }
    }

    @GetMapping("/providerlist")
    public ResponseEntity<?> getProviderList() {
        try {
            List<Provider> providers = providerService.findAll();
            return ResponseEntity.ok(providers);
        } catch (Exception e) {
            logger.error("Error fetching provider list", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }
}