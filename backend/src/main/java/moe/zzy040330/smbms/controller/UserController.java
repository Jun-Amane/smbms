/**
 * Package: moe.zzy040330.smbms.controller
 * File: UserController.java
 * Author: Ziyu ZHOU
 * Date: 04/01/2025
 * Time: 10:41
 * Description: RESTful API for user entity.
 */
package moe.zzy040330.smbms.controller;

import moe.zzy040330.smbms.dto.ErrorResponse;
import moe.zzy040330.smbms.dto.PasswordUpdateRequest;
import moe.zzy040330.smbms.dto.RoleDto;
import moe.zzy040330.smbms.dto.UserDto;
import moe.zzy040330.smbms.entity.Role;
import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.service.JwtService;
import moe.zzy040330.smbms.service.UserService;
import moe.zzy040330.smbms.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    private final UserService userService;
    private final JwtService jwtService;

    public UserController(UserService userService, JwtService jwtService) {
        this.userService = userService;
        this.jwtService = jwtService;
    }

    @GetMapping("/codeexists")
    public ResponseEntity<?> apiUserCodeexistsGet(@RequestParam("code") String code) {
        try {
            var exists = this.userService.findIfUserCodeExists(code);

            String result;
            if (exists) {
                result = "exists";
            } else {
                result = "not exists";
            }

            Map<String, Object> response = new HashMap<>();
            response.put("result", result);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: " + e.getMessage()));
        }

    }

    @GetMapping("")
    public ResponseEntity<?> apiUserGet(@RequestParam(value = "queryName", required = false) String queryName,
                                        @RequestParam(value = "queryRole", required = false) Long roleId,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("pageIndex") Integer pageIndex) {

        try {
            var pageInfo = this.userService.findByQuery(queryName, roleId, pageIndex, pageSize);

            Map<String, Object> response = new HashMap<>();
            response.put("totalItems", pageInfo.getTotal());
            response.put("curPage", pageInfo.getPageNum());
            response.put("totalPages", pageInfo.getPages());
            response.put("pageSize", pageInfo.getPageSize());
            response.put("users", pageInfo.getList().stream().map(UserController::user2userDTO));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: " + e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('SMBMS_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        try {
            Boolean isDeleted = this.userService.deleteById(id);
            if (isDeleted) {
                return ResponseEntity.ok("User deleted successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "User not found"));
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> apiUserGet(@PathVariable Long id) {
        try {

            var user = this.userService.findById(id);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "User not found"));
            }

            return ResponseEntity.ok(user2userDTO(user));
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: " + e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('SMBMS_ADMIN') or #id == authentication.principal.id")
    @PatchMapping("/{id}/password")
    public ResponseEntity<?> apiUserIdPasswordPatch(@PathVariable Long id,
                                                    @RequestBody PasswordUpdateRequest passwordUpdateRequest,
                                                    @RequestHeader("Authorization") String authHeader) {
        try {

            String token = authHeader.substring(7);

            String newPassword = passwordUpdateRequest.getNewPassword();
            if (newPassword == null || newPassword.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: newPassword cannot be null or empty"));
            }

            String oldPassword = passwordUpdateRequest.getOldPassword();
            if (oldPassword == null || oldPassword.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: oldPassword cannot be null or empty"));
            }

            var modifiedBy = new User();
            modifiedBy.setId(jwtService.extractUserId(token));

            try {
                boolean success = userService.changePassword(id, oldPassword, newPassword, modifiedBy, new Date());
                if (success) {
                    return ResponseEntity.ok("Password updated successfully");
                } else {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(new ErrorResponse(404, "User not found"));
                }
            } catch (IllegalArgumentException e) {
                logger.error(e.getMessage());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse(500, "Password not changed: " + e.getMessage()));
            }


        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: " + e.getMessage()));
        }
    }

    @PreAuthorize("hasAuthority('SMBMS_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<?> apiUserIdPut(@PathVariable Long id, @RequestBody UserDto user,
                                          @RequestHeader("Authorization") String authHeader) {
        try {
            String token = authHeader.substring(7);

            if (id == null || user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: cannot be null or empty"));
            }
            user.setId(id);

            boolean found = this.userService.findById(id) != null;

            if (found) {

                var userObj = userRequest2userObj(user);

                var modifiedBy = new User();
                modifiedBy.setId(jwtService.extractUserId(token));

                userObj.setModifiedBy(modifiedBy);
                userObj.setModificationDate(new Date());

                boolean success = this.userService.update(userObj);

                if (success) {
                    return ResponseEntity.ok(" User modified successfully ");
                } else {
                    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                            .body(new ErrorResponse(500, "Internal server error"));
                }
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "User not found"));
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: " + e.getMessage()));
        }
    }

    private static User userRequest2userObj(UserDto user) {
        var userObj = new User();
        userObj.setId(user.getId());
        userObj.setPassword(user.getPassword());
        userObj.setCode(user.getCode());
        userObj.setName(user.getName());
        userObj.setGender(user.getGender());
        userObj.setBirthday(DateUtils.parseDate(user.getBirthday()));
        userObj.setPhone(user.getPhone());
        userObj.setAddress(user.getAddress());
        var role = new Role();
        role.setId(user.getRoleId());
        userObj.setRole(role);
        return userObj;
    }

    private static UserDto user2userDTO(User user) {
        return new UserDto(
                user.getId(),
                user.getCode(),
                user.getName(),
                user.getPassword(),
                user.getGender(),
                DateUtils.formatDate(user.getBirthday()),
                user.getPhone(),
                user.getAddress(),
                user.getRole().getId(),
                user.getAge()
        );
    }

    private static RoleDto roleObj2roleResponse(Role role) {
        var roleResponse = new RoleDto();
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        roleResponse.setCode(role.getCode());
        return roleResponse;
    }

    @PreAuthorize("hasAuthority('SMBMS_ADMIN')")
    @PostMapping("")
    public ResponseEntity<?> apiUserPost(@RequestBody UserDto user,
                                         @RequestHeader("Authorization") String authHeader) {
        try {

            String token = authHeader.substring(7);

            if (user.getCode() == null || user.getCode().isBlank() ||
                    user.getPassword() == null || user.getPassword().isBlank() ||
                    user.getName() == null || user.getName().isBlank() ||
                    user.getGender() == null ||
                    user.getBirthday() == null ||
                    user.getPhone() == null || user.getPhone().isBlank() ||
                    user.getAddress() == null || user.getAddress().isBlank() ||
                    user.getRoleId() == null) {

                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: cannot be null or empty"));
            }

            var userObj = userRequest2userObj(user);

            var modifiedBy = new User();
            modifiedBy.setId(jwtService.extractUserId(token));

            userObj.setModifiedBy(modifiedBy);
            userObj.setCreatedBy(modifiedBy);
            userObj.setCreationDate(new Date());
            userObj.setModificationDate(new Date());

            boolean succeed = this.userService.insert(userObj);

            if (succeed) {
                return ResponseEntity.ok("User added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse(500, "Internal server error"));
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: ") + e.getMessage());
        }
    }

    @GetMapping("/rolelist")
    public ResponseEntity<?> apiUserRoleListGet() {
        try {
            var roleList = this.userService.getRoleList().stream().map(UserController::roleObj2roleResponse);

            return ResponseEntity.ok(roleList);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error: ") + e.getMessage());
        }
    }

}
