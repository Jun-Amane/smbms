/**
 * Package: moe.zzy040330.smbms.controller
 * File: ProviderController.java
 * Author: Wenqiang Chen
 * Date: 05/01/2025
 * Time: 10:49
 * Description: RESTful API for provider entity.
 */
package moe.zzy040330.smbms.controller;

import moe.zzy040330.smbms.dto.ErrorResponse;
import moe.zzy040330.smbms.dto.ProviderDto;
import moe.zzy040330.smbms.entity.Provider;
import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.service.JwtService;
import moe.zzy040330.smbms.service.ProviderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/provider")
public class ProviderController {

    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    private final ProviderService providerService;
    private final JwtService jwtService;

    public ProviderController(ProviderService providerService, JwtService jwtService) {
        this.providerService = providerService;
        this.jwtService = jwtService;
    }

    /**
     * Fetch all providers
     *
     */
    @GetMapping("")
    public ResponseEntity<?> apiprovidergetall(
            @RequestParam("queryName") String queryName,
            @RequestParam("queryCode") String queryCode,
            @RequestParam("pageIndex") Integer pageIndex,
            @RequestParam("pageSize") Integer pageSize) {
        try {

            var pageInfo = this.providerService.getProviderList(queryName, queryCode, pageIndex, pageSize);

            Map<String, Object> response = new HashMap<>();
            response.put("totalItems", pageInfo.getTotal());
            response.put("curPage", pageInfo.getPageNum());
            response.put("totalPages", pageInfo.getPages());
            response.put("pageSize", pageInfo.getPageSize());
            response.put("providers", pageInfo.getList().stream().map(ProviderController::providerObj2ProviderDto));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error(e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: " + e.getMessage()));
        }
    }

    /**
     * Delete provider
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> apiproviderdelete(@PathVariable Long id) {
        try {
            Boolean deleted = providerService.deleteProviderById(id);
            if (!deleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Provider not found"));
            } else {
                return ResponseEntity.ok("Delete successful");
            }
        } catch (Exception e) {
            logger.error(e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error" + e.getMessage()));
        }
    }

    /**
     * Get provider by id
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> apiproviderget(@PathVariable Long id) {
        try {
            Provider provider = providerService.findById(id);
            if (provider == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "Provider not found"));
            }
            return ResponseEntity.ok(providerObj2ProviderDto(provider));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: " + e.getMessage()));
        }
    }

    private static Provider providerDto2ProviderObj(ProviderDto providerDto) {
        Provider provider = new Provider();

        provider.setId(providerDto.getId());
        provider.setCode(providerDto.getCode());
        provider.setName(providerDto.getName());
        provider.setDescription(providerDto.getDescription());
        provider.setContact(providerDto.getContact());
        provider.setPhone(providerDto.getPhone());
        provider.setAddress(providerDto.getAddress());
        provider.setFax(providerDto.getFax());

        return provider;
    }

    private static ProviderDto providerObj2ProviderDto(Provider provider) {
        return new ProviderDto(
                provider.getId(),
                provider.getCode(),
                provider.getName(),
                provider.getDescription(),
                provider.getAddress(),
                provider.getPhone(),
                provider.getFax(),
                provider.getContact()
        );
    }

    /**
     * Update provider by id
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> apiproviderput(
            @PathVariable Long id,
            @RequestBody ProviderDto providerDto,
            @RequestHeader("Authorization") String authHeader) {
        try {
            // Extract token from Authorization header
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid Authorization header format"));
            }
            String token = authHeader.substring(7);

            // Validate inputs
            if (id == null || providerDto == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: ID or providerRequest cannot be null"));
            }

            boolean found = this.providerService.findById(id) != null;

            if (found) {

                // Convert ProviderRequest to Provider entity
                Provider providerObj = providerDto2ProviderObj(providerDto);
                providerObj.setId(id);

                // Get the modifiedBy user from the token
                User modifiedBy = new User();
                modifiedBy.setId(jwtService.extractUserId(token));

                providerObj.setModifiedBy(modifiedBy);
                providerObj.setModificationDate(new Date());

                // Update the provider
                boolean success = providerService.update(providerObj);

                if (success) {
                    return ResponseEntity.ok("Provider modified succeed");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ErrorResponse(500, "Internal server error"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "User not found"));
            }
        } catch (Exception e) {
            logger.error("Error occurred while modifying provider", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: " + e.getMessage()));
        }
    }

    /**
     * Add new provider
     */
    @PostMapping
    public ResponseEntity<?> apiProviderPost(@RequestBody ProviderDto providerDto,
                                             @RequestHeader("Authorization") String authHeader) {
        try {

            String token = authHeader.substring(7);

            // Validate the input data
            if (providerDto.getCode() == null || providerDto.getCode().isBlank() ||
                    providerDto.getName() == null || providerDto.getName().isBlank() ||
                    providerDto.getContact() == null || providerDto.getContact().isBlank() ||
                    providerDto.getPhone() == null || providerDto.getPhone().isBlank() ||
                    providerDto.getAddress() == null || providerDto.getAddress().isBlank() ||
                    providerDto.getFax() == null || providerDto.getFax().isBlank() ||
                    providerDto.getDescription() == null || providerDto.getDescription().isBlank()) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: cannot be null or empty"));
            }

            // Convert ProviderRequest to Provider entity
            var providerObj = providerDto2ProviderObj(providerDto);

            // Get the modifiedBy user from the token
            User modifiedBy = new User();
            modifiedBy.setId(jwtService.extractUserId(token));

            providerObj.setModifiedBy(modifiedBy);
            providerObj.setCreatedBy(modifiedBy);
            providerObj.setCreationDate(new Date());
            providerObj.setModificationDate(new Date());

            // Insert the new provider into the database
            boolean succeed = providerService.insert(providerObj);

            if (succeed) {
                return ResponseEntity.ok("Provider added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse(500, "Internal server error"));
            }

        } catch (Exception e) {
            logger.error("Error occurred while adding provider", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: " + e.getMessage()));
        }
    }
}