package org.example.finalproject;

import org.example.finalproject.constants.EndPointPaths;
import org.example.finalproject.constants.ExceptionConstants;
import org.example.finalproject.dto.user.UserLoginDto;
import org.example.finalproject.dto.user.UserRegistrationDto;
import org.example.finalproject.model.entity.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

public class UserRestControllerComponentTests extends BaseForComponentTests {

    @Test
    void registerUserWithEmptyStrings() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("", "", "");
        String s = objectToJsonString(userRegistrationDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_USER_REGISTER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void registerUserWithNullStrings() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("", null, null);
        String s = objectToJsonString(userRegistrationDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_USER_REGISTER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void registerUserWithNotValidEmail() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("al", "1234", "dasfewr1");
        String s = objectToJsonString(userRegistrationDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_USER_REGISTER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    void registerUserWithNotValidPassword() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("al", "123", "al@gmail.com");
        String s = objectToJsonString(userRegistrationDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_USER_REGISTER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }


    //Tests first time user registration and second time with same email
    @Test
    void registerUserWithExistingEmail() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("al", "1234", "al@gmail.com");
        String s = objectToJsonString(userRegistrationDto);
        Optional<User> userByEmail = userRepository.getUserByEmail("al@gmail.com");
        if(!userByEmail.isPresent()) {
            mockMvc.perform(
                            MockMvcRequestBuilders.post(EndPointPaths.API_USER_REGISTER)
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .content(s))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("al"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("al@gmail.com"))
                    .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("1234"));
        }

        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_USER_REGISTER)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(s))
                .andExpect(MockMvcResultMatchers.status().isConflict())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ExceptionConstants.USER_WITH_EMAIL_ALREADY_EXISTS));
    }

    @Test
    void loginUserWithCorrectCredentials() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("al", "1234", "al@gmail.com");
        String registeringUser = objectToJsonString(userRegistrationDto);
        mockMvc.perform(
                MockMvcRequestBuilders.post(EndPointPaths.API_USER_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registeringUser));

        UserLoginDto userLoginDto = new UserLoginDto("al@gmail.com", "1234");
        String loginUser = objectToJsonString(userLoginDto);
        mockMvc.perform(
                MockMvcRequestBuilders.post(EndPointPaths.API_USER_LOGIN)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(loginUser))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.token").exists());
    }

    @Test
    void loginUserWithWrongCredentials() throws Exception {
        UserRegistrationDto userRegistrationDto = new UserRegistrationDto("al", "1234", "al@gmail.com");
        String registeringUser = objectToJsonString(userRegistrationDto);
        mockMvc.perform(
                MockMvcRequestBuilders.post(EndPointPaths.API_USER_REGISTER)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(registeringUser));
        UserLoginDto userLoginDto = new UserLoginDto("alfasd@gmail.com", "12fdsa34");
        String loginUser = objectToJsonString(userLoginDto);
        mockMvc.perform(
                        MockMvcRequestBuilders.post(EndPointPaths.API_USER_LOGIN)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(loginUser))
                .andExpect(MockMvcResultMatchers.status().isUnauthorized())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value(ExceptionConstants.EMAIL_OR_PASSWORD_NOT_VALID));
    }


}
