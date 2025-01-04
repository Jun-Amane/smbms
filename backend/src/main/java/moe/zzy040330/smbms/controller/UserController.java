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
import moe.zzy040330.smbms.dto.UserRequest;
import moe.zzy040330.smbms.entity.Role;
import moe.zzy040330.smbms.entity.User;
import moe.zzy040330.smbms.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/codeexists")
    public Map<String, Object> apiUserCodeexistsGet(@RequestParam("code") String code) {
        var exists = this.userService.findIfUserCodeExists(code);

        String result = "";
        if (exists) {
            result = "exists";
        } else {
            result = "not exists";
        }

        Map<String, Object> response = new HashMap<>();
        response.put("result", result);

        return response;
    }

    @GetMapping("/")
    public ResponseEntity<?> apiUserGet(@RequestParam("queryName") String queryName,
                                        @RequestParam("queryRole") Long roleId,
                                        @RequestParam("pageSize") Integer pageSize,
                                        @RequestParam("pageIndex") Integer pageIndex) {

        try {
            var pageInfo = this.userService.findByQuery(queryName, roleId, pageSize, pageIndex);

            // Building the response
            Map<String, Object> response = new HashMap<>();
            response.put("totalItems", pageInfo.getTotal());
            response.put("curPage", pageInfo.getPageNum());
            response.put("totalPages", pageInfo.getPages());
            response.put("pageSize", pageInfo.getPageSize());
            response.put("users", pageInfo.getList());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            // TODO: Log exception (consider using a logger rather than printing stack traces)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

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
            // TODO: Log exception (consider using a logger rather than printing stack traces)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> apiUserGet(@PathVariable Long id) {
        try {

            var user = this.userService.findById(id);

            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorResponse(404, "User not found"));
            }

            return ResponseEntity.ok(user);
        } catch (Exception e) {
            // TODO: Log exception (consider using a logger rather than printing stack traces)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @PatchMapping("/{id}/password")
    public ResponseEntity<?> apiUserIdPasswordPatch(@PathVariable Long id, @RequestBody PasswordUpdateRequest passwordUpdateRequest) {
        try {
            String newPassword = passwordUpdateRequest.getNewPassword();
            if (newPassword == null || newPassword.isBlank()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: newPassword cannot be null or empty"));
            }

            // TODO: add modified by here after implemented auth module.
            boolean success = userService.changePassword(id, newPassword, null, new Date());

            if (success) {
                return ResponseEntity.ok("Password updated successfully");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ErrorResponse(404, "User not found"));
            }
        } catch (Exception e) {
            // TODO: Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> apiUserIdPut(@PathVariable Long id, @RequestBody UserRequest user) {
        try {
            if (id == null || user == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(new ErrorResponse(400, "Invalid input: cannot be null or empty"));
            }
            user.setId(id);

            boolean found = this.userService.findById(id) != null;

            if (found) {

                var userObj = userRequest2userObj(user);

                // TODO: add modified by here after implemented auth module.
                boolean success = this.userService.update(userObj, null, new Date());

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
            // TODO: Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }

    }

    private static User userRequest2userObj(UserRequest user) {
        var userObj = new User();
        userObj.setId(user.getId());
        userObj.setPassword(user.getPassword());
        userObj.setCode(user.getCode());
        userObj.setName(user.getName());
        userObj.setGender(user.getGender());
        userObj.setBirthday(user.getBirthday());
        userObj.setPhone(user.getPhone());
        userObj.setAddress(user.getAddress());
        var role = new Role();
        role.setId(user.getRoleId());
        userObj.setRole(role);
        return userObj;
    }

    @PostMapping("/")
    public ResponseEntity<?> apiUserPost(@RequestBody UserRequest user) {
        try {

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

            // TODO: createdBy
            boolean succeed = this.userService.insert(userObj, null, new Date());

            if (succeed) {
                return ResponseEntity.ok("User added successfully");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body(new ErrorResponse(500, "Internal server error"));
            }

        } catch (Exception e) {
            // TODO: Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    @GetMapping("/rolelist")
    public ResponseEntity<?> apiUserRoleListGet() {
        try {
            var roleList = this.userService.getRoleList();

            return ResponseEntity.ok(roleList);
        } catch (Exception e) {
            // TODO: Log the exception
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse(500, "Internal server error"));
        }
    }

    // TODO: login & logout
}
