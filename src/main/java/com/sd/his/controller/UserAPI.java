package com.sd.his.controller;

import com.sd.his.enums.ResponseEnum;
import com.sd.his.enums.UserEnum;
import com.sd.his.model.Profile;
import com.sd.his.model.User;
import com.sd.his.response.UserResponseWrapper;
import com.sd.his.wrapper.AdminWrapper;
import com.sd.his.response.GenericAPIResponse;
import com.sd.his.service.HISUserService;
import com.sd.his.utill.APIUtil;
import com.sd.his.utill.HISCoreUtil;
import com.sd.his.wrapper.UserCreateRequest;
import com.sd.his.wrapper.UserWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.sql.Delete;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.*;
import java.util.stream.IntStream;

@RestController
@RequestMapping("/user")
public class UserAPI {

    @Autowired
    private TokenStore tokenStore;
    @Autowired
    private HISUserService userService;

    private final Logger logger = LoggerFactory.getLogger(UserAPI.class);
    private ResourceBundle messageBundle = ResourceBundle.getBundle("messages");

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
    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request, Principal principal) {
        logger.error("getLoggedInUser API initiated.");
        String name = principal.getName();

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("admin.login.error"));
        response.setResponseCode(ResponseEnum.ADMIN_LOGGEDIN_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (HISCoreUtil.isValidObject(name)) {
                User user = userService.findByUserName(name);
                logger.info("Checking loggedInUser ..." + user);
                UserWrapper userWrapper = userService.buildUserWrapper(user);

                response.setResponseMessage(messageBundle.getString("admin.login.success"));
                response.setResponseCode(ResponseEnum.ADMIN_LOGGEDIN_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(userWrapper);

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("getLoggedInUser failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ApiOperation(httpMethod = "GET", value = "Admin Loggout ",
            notes = "This method will Log out the User",
            produces = "application/json", nickname = "Logging Out ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User Logout success", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<?> logOutUser(HttpServletRequest request) {
        logger.info("LogoutUser API initiated...");

        String authHeader = request.getHeader("Authorization");
        logger.info("Checking Request Header...:" + authHeader);

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.logout.error"));
        response.setResponseCode(ResponseEnum.USER_LOGGED_OUT_FAILED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(authHeader);

        try {
            if (!HISCoreUtil.isNull(authHeader)) {
                String tokenValue = authHeader.replace("Bearer", "").trim();
                OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
                tokenStore.removeAccessToken(accessToken);

                response.setResponseMessage(messageBundle.getString("user.logout.success"));
                response.setResponseCode(ResponseEnum.USER_LOGGED_OUT_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(null);

                logger.info("User logging out ...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        } catch (Exception ex) {
            logger.error("logOutUser failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    //Create User

    @ApiOperation(httpMethod = "POST", value = "Create User ",
            notes = "This method will Create User",
            produces = "application/json", nickname = "Create User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User successfully created", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseEntity<?> createUser(HttpServletRequest request,
                                                  @RequestBody UserCreateRequest createRequest) {


        long date = System.currentTimeMillis();
        logger.info("Create User API called..."+ createRequest.getUserType());
        logger.info("Create User API called..."+ createRequest.getUserName());
        createRequest.setCreatedOn(date);
        createRequest.setUpdatedOn(date);
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.add.error"));
        response.setResponseCode(ResponseEnum.USER_ADD_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {

            if (!HISCoreUtil.isNull(createRequest.getUserType())) {
                User alreadyExist = userService.findByUserName(createRequest.getUserName());

                if (HISCoreUtil.isValidObject(alreadyExist)) {
                    response.setResponseMessage(messageBundle.getString("user.add.already-found.error"));
                    response.setResponseCode(ResponseEnum.USER_ALREADY_EXIST_ERROR.getValue());
                    response.setResponseStatus(ResponseEnum.ERROR.getValue());
                    response.setResponseData(null);
                    logger.error("User already exist with the same name...");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

                User savedUser = userService.saveUser(createRequest);

                    if (HISCoreUtil.isValidObject(savedUser)) {
                        response.setResponseData(savedUser);
                        response.setResponseMessage(messageBundle.getString("user.add.success"));
                        response.setResponseCode(ResponseEnum.USER_ADD_SUCCESS.getValue());
                        response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                        logger.info("User created successfully...");

                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }
                }
            else {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Create User insufficient params");
            }

        } catch (Exception ex) {
            logger.error("Create User Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
            return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ApiOperation(httpMethod = "GET", value = "Paginated Users",
            notes = "This method will return Paginated Users",
            produces = "application/json", nickname = "Get Paginated Users ",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Paginated Users fetched successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/all/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> versions(HttpServletRequest request,
                                      @PathVariable("page") int page,
                                      @RequestParam(value = "pageSize",
                                              required = false, defaultValue = "10") int pageSize) {
        logger.info("getAllUsers paginated..");

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.not.found"));
        response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            List<UserResponseWrapper> userWrappers = userService.findAllUsers(page, pageSize);

            int countUser = userService.totalUser();

            if (!HISCoreUtil.isListEmpty(userWrappers)) {
                Integer nextPage, prePage, currPage;
                int[] pages;

                if (countUser > pageSize) {
                     int remainder = countUser % pageSize;
                     int  totalPages = countUser / pageSize;
                    if (remainder > 0) {
                        totalPages = totalPages + 1;
                    }
                    pages = new int[totalPages];
                    pages = IntStream.range(0, totalPages).toArray();
                    currPage = page;
                    nextPage = (currPage + 1) != totalPages ? currPage + 1 : null;
                    prePage = currPage > 0 ? currPage : null;
                } else {
                    pages = new int[1];
                    pages[0] = 0;
                    currPage = 0;
                    nextPage = null;
                    prePage = null;
                }

                Map<String, Object> returnValues = new LinkedHashMap<>();
                returnValues.put("nextPage", nextPage);
                returnValues.put("prePage", prePage);
                returnValues.put("currPage", currPage);
                returnValues.put("pages", pages);
                returnValues.put("data", userWrappers);

                response.setResponseMessage(messageBundle.getString("user.fetched.success"));
                response.setResponseCode(ResponseEnum.USER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                response.setResponseData(returnValues);
                logger.info("getAllPaginatedUser Fetched successfully...");
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            logger.error("get all paginated getAllPaginatedUser failed.", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(httpMethod = "PUT", value = "Update User ",
            notes = "This method will Update User",
            produces = "application/json", nickname = "Update User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User successfully updated", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @PutMapping(value = "/edit/{id}")
    public ResponseEntity<?> updateUser(HttpServletRequest request,
                                        @PathVariable("id") int id,
                                        @RequestBody UserCreateRequest createRequest) {


        long date = System.currentTimeMillis();
        logger.info("update User API called..."+ createRequest.getUserType());
        logger.info("update User API called..."+ createRequest.getUserName());
        //createRequest.setCreatedOn(date);
        createRequest.setUpdatedOn(date);
        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.update.error"));
        response.setResponseCode(ResponseEnum.USER_UPDATE_ERROR.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {
            if (!HISCoreUtil.isNull(createRequest.getUserType())) {
               User alreadyExistUser = userService.findById(id);

                if (HISCoreUtil.isValidObject(alreadyExistUser)) {
                    logger.info("User founded...");


                    User userUpdated = userService.updateUser(createRequest,alreadyExistUser);
                   //todo

                    if(HISCoreUtil.isValidObject(userUpdated)){
                        logger.info("User saved...");
                        response.setResponseData(userUpdated);
                        response.setResponseMessage(messageBundle.getString("user.update.success"));
                        response.setResponseCode(ResponseEnum.USER_UPDATE_SUCCESS.getValue());
                        response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                        logger.info("User updated successfully...");

                        return new ResponseEntity<>(response, HttpStatus.OK);
                    }
                    logger.info("User not found...");
                    response.setResponseMessage(messageBundle.getString("user.not.found"));
                    response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
                    response.setResponseStatus(ResponseEnum.ERROR.getValue());
                    response.setResponseData(null);
                    logger.error("User not updated...");
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }

            }
            else {
                response.setResponseMessage(messageBundle.getString("insufficient.parameter"));
                response.setResponseCode(ResponseEnum.INSUFFICIENT_PARAMETERS.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                response.setResponseData(null);
                logger.error("Update User insufficient params");
            }

        } catch (Exception ex) {
            logger.error("Update User Failed.", ex.fillInStackTrace());
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @ApiOperation(httpMethod = "GET", value = "User",
            notes = "This method will return User on base of id",
            produces = "application/json", nickname = "Get Single User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> findByID(HttpServletRequest request,
                                        @PathVariable("id") long id
                                      ) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.not.found"));
        response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {

            UserResponseWrapper userFound = this.userService.findByIdAndResponse(id);

            if (HISCoreUtil.isValidObject(userFound)) {
                response.setResponseData(userFound);
                response.setResponseMessage(messageBundle.getString("user.found"));
                response.setResponseCode(ResponseEnum.USER_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("User Found successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }else {
              response.setResponseData(null);
              response.setResponseMessage(messageBundle.getString("user.not.found"));
              response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
              response.setResponseStatus(ResponseEnum.ERROR.getValue());
              logger.info("User Not Found ...");
                }
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception ex) {
            logger.error("User Not Found", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation(httpMethod = "Delete", value = "User",
            notes = "This method will Delete User on base of id",
            produces = "application/json", nickname = "Delete Single User",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User Deleted successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/delete/{id}",method = RequestMethod.DELETE)
    public @ResponseBody ResponseEntity<?> deleteByID(HttpServletRequest request,
                                      @PathVariable("id") long id
    ) {

        GenericAPIResponse response = new GenericAPIResponse();
        response.setResponseMessage(messageBundle.getString("user.delete.error"));
        response.setResponseCode(ResponseEnum.USER_NOT_DELETED.getValue());
        response.setResponseStatus(ResponseEnum.ERROR.getValue());
        response.setResponseData(null);

        try {

            User userFound = this.userService.findById(id);

            if (HISCoreUtil.isValidObject(userFound)) {

                userService.deleteUser(userFound);

                response.setResponseData(userFound);
                response.setResponseMessage(messageBundle.getString("user.delete.success"));
                response.setResponseCode(ResponseEnum.USER_DELETED_SUCCESS.getValue());
                response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                logger.info("User Deleted successfully...");

                return new ResponseEntity<>(response, HttpStatus.OK);
            }else {
                response.setResponseData(null);
                response.setResponseMessage(messageBundle.getString("user.not.found"));
                response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
                response.setResponseStatus(ResponseEnum.ERROR.getValue());
                logger.info("User Not Found ...");
            }
            return new ResponseEntity<>(response, HttpStatus.OK);
            }catch (Exception ex) {
            logger.error("User Not Deleted", ex.fillInStackTrace());
            response.setResponseData("");
            response.setResponseStatus(ResponseEnum.ERROR.getValue());
            response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
            response.setResponseMessage(messageBundle.getString("exception.occurs"));

            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    @ApiOperation(httpMethod = "GET", value = "Search User",
            notes = "This method will return User on base of search",
            produces = "application/json", nickname = "Search Users",
            response = GenericAPIResponse.class, protocols = "https")
    @ApiResponses({
            @ApiResponse(code = 200, message = "User found successfully", response = GenericAPIResponse.class),
            @ApiResponse(code = 401, message = "Oops, your fault. You are not authorized to access.", response = GenericAPIResponse.class),
            @ApiResponse(code = 403, message = "Oops, your fault. You are forbidden.", response = GenericAPIResponse.class),
            @ApiResponse(code = 404, message = "Oops, my fault System did not find your desire resource.", response = GenericAPIResponse.class),
            @ApiResponse(code = 500, message = "Oops, my fault. Something went wrong on the server side.", response = GenericAPIResponse.class)})
    @RequestMapping(value = "/search/{page}", method = RequestMethod.GET)
    public ResponseEntity<?> searchAdmins(HttpServletRequest request,
                                          @PathVariable("page") int page,
                                          @RequestParam(value = "pageSize", required = false, defaultValue = "10") int pageSize,
                                          @RequestParam(value = "name") String name,
                                          @RequestParam(value = "role") String role,
                                          @RequestParam(value = "email") String email)
                                           {
logger.info("search:" + role);
                                               GenericAPIResponse response = new GenericAPIResponse();
                                               response.setResponseMessage(messageBundle.getString("user.not.found"));
                                               response.setResponseCode(ResponseEnum.USER_NOT_FOUND.getValue());
                                               response.setResponseStatus(ResponseEnum.ERROR.getValue());
                                               response.setResponseData(null);

                                               try {
                                                   //
       List<UserWrapper> userWrappers = userService.searchByNameOrEmailOrRole(name, email,role,page,pageSize);

                                                   int countUser = userService.totalUser();

                                                   if (!HISCoreUtil.isListEmpty(userWrappers)) {
                                                       Integer nextPage, prePage, currPage;
                                                       int[] pages;

                                                       if (countUser > pageSize) {
                                                           int remainder = countUser % pageSize;
                                                           int totalPages = countUser / pageSize;
                                                           if (remainder > 0) {
                                                               totalPages = totalPages + 1;
                                                           }
                                                           pages = new int[totalPages];
                                                           pages = IntStream.range(0, totalPages).toArray();
                                                           currPage = page;
                                                           nextPage = (currPage + 1) != totalPages ? currPage + 1 : null;
                                                           prePage = currPage > 0 ? currPage : null;
                                                       } else {
                                                           pages = new int[1];
                                                           pages[0] = 0;
                                                           currPage = 0;
                                                           nextPage = null;
                                                           prePage = null;
                                                       }

                                                       Map<String, Object> returnValues = new LinkedHashMap<>();
                                                       returnValues.put("nextPage", nextPage);
                                                       returnValues.put("prePage", prePage);
                                                       returnValues.put("currPage", currPage);
                                                       returnValues.put("pages", pages);
                                                       returnValues.put("data", userWrappers);

                                                       response.setResponseMessage(messageBundle.getString("user.fetched.success"));
                                                       response.setResponseCode(ResponseEnum.USER_FOUND.getValue());
                                                       response.setResponseStatus(ResponseEnum.SUCCESS.getValue());
                                                       response.setResponseData(returnValues);
                                                       logger.info("searched User Fetched successfully...");
                                                       return new ResponseEntity<>(response, HttpStatus.OK);
                                                   }
                                                   return new ResponseEntity<>(response, HttpStatus.OK);
                                               } catch (Exception ex) {
                                                   logger.error("searched User failed.", ex.fillInStackTrace());
                                                   response.setResponseData("");
                                                   response.setResponseStatus(ResponseEnum.ERROR.getValue());
                                                   response.setResponseCode(ResponseEnum.EXCEPTION.getValue());
                                                   response.setResponseMessage(messageBundle.getString("exception.occurs"));

                                                   return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
                                               }
                                           }

}

