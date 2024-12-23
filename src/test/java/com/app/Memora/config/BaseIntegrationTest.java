//package com.app.Memora.config;
//
//import com.app.Memora.authentication.entities.User;
//import com.app.Memora.authentication.repositories.UserRepository;
//import com.app.Memora.authentication.services.JwtService;
//import com.app.Memora.authentication.services.UserService;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.ActiveProfiles;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@ActiveProfiles("test")
//@Transactional
//public abstract class BaseIntegrationTest {
//
//    @Autowired
//    protected MockMvc mockMvc;
//
//    @Autowired
//    protected ObjectMapper objectMapper;
//
//    @Autowired
//    protected JwtService jwtService;
//
//    @Autowired
//    protected UserService userService;
//
//    @Autowired
//    protected UserRepository userRepository;  // Added UserRepository
//
//    protected static final String BASE_URL = "/api";
//    protected String authToken;
//    protected User testUser;
//
//    /**
//     * Creates a test user and generates JWT token
//     */
//    protected void setupAuth() {
//        // Create test user
//        testUser = new User();
//        testUser.setFullName("testuser");
//        testUser.setEmail("test@example.com");
//        testUser.setPassword("password123");
//        testUser = userRepository.save(testUser);
//
//        // Generate token
//        authToken = jwtService.generateToken(testUser);
//    }
//
//    /**
//     * Performs a GET request with authentication
//     */
//    protected ResultActions performGet(String url) throws Exception {
//        return mockMvc.perform(get(url)
//                .header("Authorization", "Bearer " + authToken));
//    }
//
//    /**
//     * Performs a POST request with authentication and body
//     */
//    protected ResultActions performPost(String url, Object body) throws Exception {
//        return mockMvc.perform(post(url)
//                .header("Authorization", "Bearer " + authToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(body)));
//    }
//
//    /**
//     * Performs a PUT request with authentication and body
//     */
//    protected ResultActions performPut(String url, Object body) throws Exception {
//        return mockMvc.perform(put(url)
//                .header("Authorization", "Bearer " + authToken)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(objectMapper.writeValueAsString(body)));
//    }
//
//    /**
//     * Performs a DELETE request with authentication
//     */
//    protected ResultActions performDelete(String url) throws Exception {
//        return mockMvc.perform(delete(url)
//                .header("Authorization", "Bearer " + authToken));
//    }
//
//    /**
//     * Creates test data and returns the response
//     */
//    protected <T> T createTestEntity(String url, Object requestBody, Class<T> responseType) throws Exception {
//        String response = performPost(url, requestBody)
//                .andExpect(status().isOk())
//                .andReturn()
//                .getResponse()
//                .getContentAsString();
//
//        return objectMapper.readValue(response, responseType);
//    }
//
//    /**
//     * Converts object to JSON string
//     */
//    protected String toJson(Object obj) throws Exception {
//        return objectMapper.writeValueAsString(obj);
//    }
//
//    /**
//     * Converts JSON string to object
//     */
//    protected <T> T fromJson(String json, Class<T> clazz) throws Exception {
//        return objectMapper.readValue(json, clazz);
//    }
//
//    /**
//     * Creates test data map
//     */
//    protected Map<String, Object> createTestData(String... keyValuePairs) {
//        Map<String, Object> data = new HashMap<>();
//        for (int i = 0; i < keyValuePairs.length; i += 2) {
//            data.put(keyValuePairs[i], keyValuePairs[i + 1]);
//        }
//        return data;
//    }
//
//    /**
//     * Gets authenticated headers
//     */
//    protected Map<String, String> getAuthHeaders() {
//        Map<String, String> headers = new HashMap<>();
//        headers.put("Authorization", "Bearer " + authToken);
//        headers.put("Content-Type", MediaType.APPLICATION_JSON_VALUE);
//        return headers;
//    }
//
//    /**
//     * Validates common response fields
//     */
//    protected void validateCommonResponseFields(Map<String, Object> response) {
//        assert response.containsKey("id");
//        assert response.containsKey("createdAt");
//        assert response.containsKey("updatedAt");
//    }
//
//    /**
//     * Utility method to check if a string is valid JSON
//     */
//    protected boolean isValidJson(String json) {
//        try {
//            objectMapper.readTree(json);
//            return true;
//        } catch (Exception e) {
//            return false;
//        }
//    }
//
//    /**
//     * Utility method to generate random test data
//     */
//    protected String generateRandomString(int length) {
//        return java.util.UUID.randomUUID().toString().substring(0, length);
//    }
//
//    /**
//     * Utility method to get current timestamp
//     */
//    protected Date getCurrentTimestamp() {
//        return new Date();
//    }
//}