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
import java.util.List;

@RestController
@RequestMapping("/api/providers")
public class ProviderController {

    private static final Logger logger = LoggerFactory.getLogger(ProviderController.class);

    private final ProviderService providerService;
    private final JwtService jwtService;

    public ProviderController(ProviderService providerService,JwtService jwtService) {
        this.providerService = providerService;
        this.jwtService = jwtService;
    }

    /**
     * 获取所有供应商
     *
     * @return 返回所有供应商的信息
     */
    @GetMapping("")
    public ResponseEntity<?> apiprovidergetall() {
        try {
            List<Provider> providers = providerService.findAll();
            return ResponseEntity.ok(providers);
        } catch (Exception e) {
            logger.error(e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "获取供应商列表时发生错误"));
        }
    }

    /**
     * 删除供应商
     *
     * @param id 供应商 ID
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> apiproviderdelete(@PathVariable Long id) {
        try {
            boolean deleted = providerService.deleteProviderById(id);
            if (!deleted) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "供应商未找到"));
            }
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            logger.error(e.getMessage());

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "删除供应商时发生错误"));
        }
    }

    /**
     * 获取单个供应商
     *
     * @param id 供应商 ID
     * @return 供应商详细信息
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> apiproviderget(@PathVariable Long id) {
        try {
            Provider provider = providerService.findById(id);
            if (provider == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "供应商未找到"));
            }
            return ResponseEntity.ok(provider);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "获取供应商信息时发生错误"));
        }
    }



    private static Provider providerRequest2ProviderObj(ProviderDto providerDto) {
        // 创建新的 Provider 对象
        Provider provider = new Provider();

        // 设置 Provider 的基本属性
        provider.setId(providerDto.getId());
        provider.setCode(providerDto.getCode());
        provider.setName(providerDto.getName());
        provider.setDescription(providerDto.getDescription());
        provider.setContact(providerDto.getContact());
        provider.setPhone(providerDto.getPhone());
        provider.setAddress(providerDto.getAddress());
        provider.setFax(providerDto.getFax());

        // 处理创建者和修改者字段，这里假设传入的是 User 对象的 id
        // 假设 ProviderRequest 中创建者和修改者是通过 UserRequest 对象传递的
        User createdBy = new User();
        createdBy.setId(providerDto.getCreatedBy());
        provider.setCreatedBy(createdBy);

        User modifiedBy = new User();
        modifiedBy.setId(providerDto.getModifiedBy());
        provider.setModifiedBy(modifiedBy);


        provider.setCreationDate(providerDto.getCreationDate());
        provider.setModificationDate(providerDto.getModificationDate());

        return provider;
    }


    /**
     * 更新供应商信息
     *
     * @param id          供应商 ID
     * @param providerDto 供应商详细信息
     * @return 更新结果
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
                Provider providerObj = providerRequest2ProviderObj(providerDto);

                // Get the modifiedBy user from the token
                User modifiedBy = new User();
                modifiedBy.setId(jwtService.extractUserId(token));

                providerObj.setModifiedBy(modifiedBy);
                providerObj.setModificationDate(new Date());

                // Update the provider
                boolean success = providerService.update(providerObj);

                if (success) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
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
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    /**
     * 新增供应商
     */
    @PostMapping
    public ResponseEntity<?> apiProviderPost(@RequestBody ProviderDto providerDto,
                                             @RequestHeader("Authorization") String authHeader) {
        try {

            // Extract the token from the Authorization header
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid Authorization header format"));
            }
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
            var providerObj = providerRequest2ProviderObj(providerDto);

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
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }
}