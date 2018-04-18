package com.sd.his.controller;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.model.Contact;
import com.sd.his.model.Permission;
import com.sd.his.model.Role;
import com.sd.his.model.User;
import com.sd.his.model.wrapper.AdminWrapper;
import com.sd.his.model.wrapper.UserWrapper;
import com.sd.his.response.GenericAPIResponse;
import com.sd.his.service.CustomConfigService;
import com.sd.his.service.HISUserService;
import com.sd.his.utill.APIUtil;
import com.sd.his.utill.HISCoreUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.List;
import java.util.ResourceBundle;

@RestController
public class UserController {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private HISUserService userService;
    @Autowired
    private CustomConfigService customConfigService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    private SimpMessagingTemplate template;

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public List<User> listUser() {
        return userService.findAll();
    }

    @RequestMapping(value = "/user", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String create(@RequestBody UserWrapper user) {
        User usermodel = new User();
        usermodel.setEmail(user.getEmail());
        usermodel.setUsername("waqas");
        usermodel.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        usermodel.setRole(userService.findRoleById(1));
        long timeMillis = System.currentTimeMillis();
        usermodel.setContact(new Contact(user.getFirstName(), user.getLastName(), user.getPhoneNumber(), true, false, user.getGender(),
                user.getAddress(), user.getCity(), user.getState(), user.getCountry(), timeMillis, timeMillis, usermodel, usermodel
        ));
        usermodel.setActive(true);
        userService.save(usermodel);
        return user.getEmail();
    }


    @DeleteMapping(path = {"user/{id}"})
    public User delete(@PathVariable("id") long id) {
        return userService.DeleteProduct(id);
    }


    @RequestMapping(value = "roles/all", method = RequestMethod.GET)
    public List<Role> listRoles() {
        return userService.findAllRoles();
    }

    @RequestMapping(value = "/addroles", method = RequestMethod.POST)
    public void saveRole(@RequestBody Role role) {
        List<Permission> obj = userService.findPermissionById(1);
        role.setPermissions(obj);
        role.setCreatedOn(System.currentTimeMillis());
        role.setDeleted(false);
        userService.saveRole(role);
    }

    @RequestMapping(value = "/addpermissions", method = RequestMethod.POST)
    public void savePersmission(@RequestBody Permission permission) {
        permission.setActive(true);
        permission.setCreatedOn(System.currentTimeMillis());
        permission.setDeleted(false);
        userService.savePermissions(permission);
    }

    @RequestMapping(value = "/allpermissions", method = RequestMethod.GET)
    public List<Permission> findAllPermission() {
        return userService.findAllPermissions();
    }


    @ApiOperation(httpMethod = "GET", value = "Admin LoggedIn",
            notes = "This method will return logged in User",
            produces = "application/json", nickname = "Logging In ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Logged in Admin fetched", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/loggedInUser", method = RequestMethod.GET)
    public ResponseEntity<?> currentUserName(HttpServletRequest request, Principal principal) {
        String name = principal.getName();

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("admin.login.error"));
        response.setResponseCode(ResponseEnum.ADMIN_LOGGEDIN_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        if (HISCoreUtil.isValidObject(name)) {
            User user = userService.findByUserName(name);
            logger.info("Checking loggedInUser ..." + user);
            AdminWrapper admin = APIUtil.buildAdminWrapper(user);

            response.setResponseMessage(messageBundle.getString("admin.login.error"));
            response.setResponseCode(ResponseEnum.ADMIN_LOGGEDIN_FAILED.getValue());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseData(admin);

            return new ResponseEntity<AdminWrapper>(admin, HttpStatus.OK);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ApiOperation(httpMethod = "GET", value = "Admin LoggedIn",
            notes = "This method will return logged in User",
            produces = "application/json", nickname = "Logging In ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Logged in Admin fetched", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/userlogout", method = RequestMethod.GET)
    public ResponseEntity<?> logOut(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        logger.info("Checking Header...:" + authHeader);
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("admin.login.error"));
        response.setResponseCode(ResponseEnum.ADMIN_LOGGEDIN_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(authHeader);

        if (!HISCoreUtil.isNull(authHeader)) {
            String tokenValue = authHeader.replace("Bearer", "").trim();
            OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
            tokenStore.removeAccessToken(accessToken);
            logger.info("logging out ...");
            return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.TEXT_PLAIN).body("you have Successfully logged Out");
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);

    }


}

