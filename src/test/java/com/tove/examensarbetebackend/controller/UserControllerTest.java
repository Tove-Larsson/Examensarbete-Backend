package com.tove.examensarbetebackend.controller;

import com.tove.examensarbetebackend.model.AppUser;
import com.tove.examensarbetebackend.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.tove.examensarbetebackend.authorities.UserRole.USER;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserControllerTest {

    private final MockMvc mockMvc;

    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Autowired
    UserControllerTest(MockMvc mockMvc, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.mockMvc = mockMvc;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @BeforeEach
    public void setUpTestUser() throws Exception {
        AppUser testUser = new AppUser();
        testUser.setUsername("TestUser");
        testUser.setPassword(passwordEncoder.encode("TestPassword"));
        testUser.setUserRole(USER);
        testUser.setAccountNonExpired(true);
        testUser.setAccountNonLocked(true);
        testUser.setCredentialsNonExpired(true);
        testUser.setEnabled(true);
        userRepository.save(testUser);
    }


    @Test
    @DisplayName("Test if registering a user with existing username works")
    void testRegisterUserWithExistingUsername() throws Exception {
        mockMvc.perform(post("/user/register")
                        .contentType("application/json")
                        .content("""
                        {
                        "username": "TestUser",
                        "password": "1234567"
                        }
                        
                        """))
                .andExpect(status().isConflict());

    }

    @Test
    @WithMockUser(username = "TestUser", roles = "USER")
    @DisplayName("Testing if a user can delete themselves")
    public void testAdminDeleteUserAsAdmin() throws Exception {
        mockMvc.perform(delete("/user/delete-user")
                        .param("username", "TestUser"))
                .andExpect(status().isNoContent());

        boolean userExists = userRepository.findByUsernameIgnoreCase("TestUser").isPresent();
        assertThat(userExists).isFalse();
    }



}