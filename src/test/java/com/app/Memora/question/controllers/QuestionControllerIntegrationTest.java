//package com.app.Memora.question.controllers;
//
//import com.app.Memora.config.BaseIntegrationTest;
//import com.app.Memora.question.dtos.QuestionDTO;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.http.MediaType;
//import org.springframework.test.web.servlet.ResultActions;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//import static org.hamcrest.Matchers.*;
//
//class QuestionControllerIntegrationTest extends BaseIntegrationTest {
//
//    private static final String QUESTIONS_API = "/api/questions";
//
//    @BeforeEach
//    void setUp() {
//        setupAuth();
//    }
//
//    @Test
//    void createQuestion_ValidData_Success() throws Exception {
//        // Arrange
//        QuestionDTO questionDTO = new QuestionDTO();
//        questionDTO.setContent("Test Question Content");
//
//        // Act & Assert
//        performPost(QUESTIONS_API, questionDTO)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").value("Test Question Content"))
//                .andExpect(jsonPath("$.id").exists());
//    }
//
//    @Test
//    void createQuestion_EmptyContent_BadRequest() throws Exception {
//        // Arrange
//        QuestionDTO questionDTO = new QuestionDTO();
//        questionDTO.setContent("");
//
//        // Act & Assert
//        performPost(QUESTIONS_API, questionDTO)
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void createQuestion_NullContent_BadRequest() throws Exception {
//        // Arrange
//        QuestionDTO questionDTO = new QuestionDTO();
//        questionDTO.setContent(null);
//
//        // Act & Assert
//        performPost(QUESTIONS_API, questionDTO)
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void getQuestion_ExistingId_Success() throws Exception {
//        // Arrange
//        QuestionDTO questionDTO = new QuestionDTO();
//        questionDTO.setContent("Test Question");
//
//        ResultActions createResult = performPost(QUESTIONS_API, questionDTO);
//        String responseJson = createResult.andReturn().getResponse().getContentAsString();
//        QuestionDTO createdQuestion = fromJson(responseJson, QuestionDTO.class);
//
//        // Act & Assert
//        performGet(QUESTIONS_API + "/" + createdQuestion.getId())
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").value(createdQuestion.getId()))
//                .andExpect(jsonPath("$.content").value("Test Question"));
//    }
//
//    @Test
//    void getQuestion_NonExistingId_NotFound() throws Exception {
//        // Act & Assert
//        performGet(QUESTIONS_API + "/999")
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void getAllQuestions_ReturnsQuestions() throws Exception {
//        // Arrange
//        QuestionDTO question1 = new QuestionDTO();
//        question1.setContent("Question 1");
//        QuestionDTO question2 = new QuestionDTO();
//        question2.setContent("Question 2");
//
//        performPost(QUESTIONS_API, question1);
//        performPost(QUESTIONS_API, question2);
//
//        // Act & Assert
//        performGet(QUESTIONS_API)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(2))))
//                .andExpect(jsonPath("$[*].content", hasItems("Question 1", "Question 2")));
//    }
//
//    @Test
//    void updateQuestion_ValidData_Success() throws Exception {
//        // Arrange
//        QuestionDTO originalQuestion = new QuestionDTO();
//        originalQuestion.setContent("Original Content");
//
//        ResultActions createResult = performPost(QUESTIONS_API, originalQuestion);
//        String responseJson = createResult.andReturn().getResponse().getContentAsString();
//        QuestionDTO createdQuestion = fromJson(responseJson, QuestionDTO.class);
//
//        QuestionDTO updateDTO = new QuestionDTO();
//        updateDTO.setContent("Updated Content");
//
//        // Act & Assert
//        performPut(QUESTIONS_API + "/" + createdQuestion.getId(), updateDTO)
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.content").value("Updated Content"))
//                .andExpect(jsonPath("$.id").value(createdQuestion.getId()));
//    }
//
//    @Test
//    void updateQuestion_NonExistingId_NotFound() throws Exception {
//        // Arrange
//        QuestionDTO updateDTO = new QuestionDTO();
//        updateDTO.setContent("Updated Content");
//
//        // Act & Assert
//        performPut(QUESTIONS_API + "/999", updateDTO)
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void deleteQuestion_ExistingId_Success() throws Exception {
//        // Arrange
//        QuestionDTO questionDTO = new QuestionDTO();
//        questionDTO.setContent("To Be Deleted");
//
//        ResultActions createResult = performPost(QUESTIONS_API, questionDTO);
//        String responseJson = createResult.andReturn().getResponse().getContentAsString();
//        QuestionDTO createdQuestion = fromJson(responseJson, QuestionDTO.class);
//
//        // Act & Assert
//        // Delete the question
//        performDelete(QUESTIONS_API + "/" + createdQuestion.getId())
//                .andExpect(status().isNoContent());
//
//        // Verify it's deleted
//        performGet(QUESTIONS_API + "/" + createdQuestion.getId())
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void deleteQuestion_NonExistingId_NotFound() throws Exception {
//        // Act & Assert
//        performDelete(QUESTIONS_API + "/999")
//                .andExpect(status().isNotFound());
//    }
//
//    @Test
//    void createQuestion_InvalidData_ValidationError() throws Exception {
//        // Arrange
//        QuestionDTO questionDTO = new QuestionDTO();
//        questionDTO.setContent("a".repeat(1001)); // Assuming max length is 1000
//
//        // Act & Assert
//        performPost(QUESTIONS_API, questionDTO)
//                .andExpect(status().isBadRequest());
//    }
//
//    @Test
//    void updateQuestion_InvalidData_ValidationError() throws Exception {
//        // Arrange
//        QuestionDTO originalQuestion = new QuestionDTO();
//        originalQuestion.setContent("Original Content");
//
//        ResultActions createResult = performPost(QUESTIONS_API, originalQuestion);
//        String responseJson = createResult.andReturn().getResponse().getContentAsString();
//        QuestionDTO createdQuestion = fromJson(responseJson, QuestionDTO.class);
//
//        QuestionDTO updateDTO = new QuestionDTO();
//        updateDTO.setContent("a".repeat(1001)); // Assuming max length is 1000
//
//        // Act & Assert
//        performPut(QUESTIONS_API + "/" + createdQuestion.getId(), updateDTO)
//                .andExpect(status().isBadRequest());
//    }
//}