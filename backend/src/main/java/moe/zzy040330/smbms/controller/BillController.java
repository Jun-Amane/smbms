/**
 * Package: moe.zzy040330.smbms.controller
 * File: BillController.java
 * Author: Xiaoteng Ma & Mingxue Li
 * Date: 06/01/2025
 * Time: 10:37
 * Description: RESTful API for bill entity.
 */
package moe.zzy040330.smbms.controller;

// FIXME: Modify the head copyright comments above with the REAL INFORMATION, ACCORDINGLY!


/* TODO: Please read all Services', all Mappers' source codes,
    just ensure that YOU UNDERSTAND WHAT YOU ARE DOING when CALLING SUCH FUNCTIONS! */


import moe.zzy040330.smbms.dto.ErrorResponse;
import moe.zzy040330.smbms.entity.Bill;
import moe.zzy040330.smbms.entity.Provider;
import moe.zzy040330.smbms.service.BillService;
import moe.zzy040330.smbms.service.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    /* FIXME: The parameters here SHOULD BE:
        public ResponseEntity<?> apiBillGet(@RequestParam(value = "queryCode") String code,
                                        @RequestParam(value = "queryProductName") String productName,
                                        @RequestParam(value = "queryProductDesc") String productDesc,
                                        @RequestParam(value = "queryProviderName") String providerName,
                                        @RequestParam(value = "queryProviderCode") String providerCode,
                                        @RequestParam(value = "queryIsPaid") String isPaid,
                                        @RequestParam(value = "pageIndex") Integer pageIndex,
                                        @RequestParam(value = "pageSize") Integer pageSize) {
        Turn to `BillService interface` to find the parameters. */
    @GetMapping("")
    /* FIXME: Remove all @PreAuthorize annotations in this class, since there is no need to check user authority in Bill Services,
     *        All users have access to operate the bills. */
    // @PreAuthorize("hasRole('SMBMS_ADMIN')")
    public ResponseEntity<?> apiBillGet(@RequestParam(value = "queryProductName") String productName,
                                        @RequestParam(value = "queryProviderId") Long providerId,
                                        @RequestParam(value = "queryIsPaid") Boolean isPaid,
                                        @RequestParam(value = "pageIndex") Integer pageIndex,
                                        @RequestParam(value = "pageSize") Integer pageSize) {
        try {

            // FIXME: DO NOT USE HARD-CODE! PASS THE ARGUMENTS TO BillService!!!
            var pageInfo = this.billService.queryBills("A","L","Desc","12","LL",1,10,12);

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
    @PreAuthorize("hasRole('SMBMS_ADMIN')") // FIXME: remove this annotation as above.
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


    // FIXME: The parameters should be: id, Bill Dto, and authHeader, Turn to ProviderController for example.
    // WARN: DO NOT use vanilla Bill entity class here AS PARAMETER! USE CUSTOMIZED DTO!!!
    // NOTE: DON'T FORGOT the parameters' Annotations.
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('SMBMS_ADMIN')")
    public ResponseEntity<?> apiBillIdPut(@PathVariable Long id, @RequestBody Bill bill) {
        try {

            /* FIXME: CRITICAL logical errors here!
             *   The procedures should be:
             *     1. Extract the token from the header "Authorization"
             *     2. Validate the inputs, check whether the id or the DTO is null
             *     3. use billService.findById() to find out if the Bill entity exists.
             *     4.1 If the entity not found, return 404
             *     4.2 If found the Bill entity to be modified,
             *       4.2.1 Create a new Bill entity, and set the attributes with BillDto
             *           NOTE: consider to add a helper function that can convert objects between Bill DTO and Bill Entity
             *       4.2.2 Extract the User ID from token with `jwtService.extractUserId()`
             *       4.2.3 Set the ModifiedBy to the new User object with that ID
             *       4.2.4 Set the ModificationDate to current Date.
             *       4.2.5 Invoke `billService.update()` to do update operation.
             *       4.2.6 Check the return value to find out if the operation succeed.
             *
             *   NOTE: Don't forget to do try-catch
             * */


            if (id == null || bill == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: cannot be null"));
            }

            bill.setId(id);
            boolean updated = billService.update(new Bill());

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
    // FIXME: Also need the authHeader here!
    // WARN: DO NOT use vanilla Bill entity class here AS PARAMETER! USE CUSTOMIZED DTO!!! (Consider to reuse the dto above)
    public ResponseEntity<?> apiBillPost(@RequestBody Bill bill) {
        try {



            /* FIXME: CRITICAL logical errors here!
             *   The procedures should be:
             *     1. Extract the token from the header "Authorization"
             *     2. Validate the inputs, check if the required fields is null.
             *     3. Create a new Bill entity, and set the attributes with BillDto
             *           NOTE: consider to add a helper function that can convert objects between Bill DTO and Bill Entity
             *     4. Extract the User ID from token with `jwtService.extractUserId()`
             *     5. Set the ModifiedBy and CreatedBy to the new User object with that ID
             *     6. Set the ModificationDate and CreationDate to current Date.
             *     7. Invoke `billService.insert()` to do insert operation.
             *     8. Check the return value to find out if the operation succeed.
             *
             *   NOTE: Don't forget to do try-catch
             * */


            // FIXME: Duplicate getProductName() here
            if (bill == null || bill.getProductName() == null || bill.getTotalPrice() == null || bill.getProductName() == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: required fields are missing"));
            }

            boolean created = billService.insert(new Bill());
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

            /* FIXME: Why casting `getProviderList` returning to List?
             *   The `queryProviders()` function will return a paginate results (PageInfo) provided by page helper plugin.
             *   Please use `findAll()` function inherited from `GenericCrudService` to get the List.
             *   Btw, Please read all Services', all Mappers' source codes,
             *   Ensure that you fully understand their implementations before calling them! */
            List<Provider> providerList = (List<Provider>) providerService.queryProviders("L","a",2,12);
            return ResponseEntity.ok(providerList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }
}
