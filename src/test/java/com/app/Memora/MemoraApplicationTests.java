package com.app.Memora;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MemoraApplicationTests {

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    void contextLoads() {
        // This test will pass if the application context is loaded successfully
    }

    @Test
    void testSettingsControllerBeanPresence() {
        // Check if SettingsController bean is present in the application context
        assertThat(applicationContext.containsBean("settingsController")).isTrue();
    }

    @Test
    void testUserServiceBeanPresence() {
        // Check if UserService bean is present in the application context
        assertThat(applicationContext.containsBean("userService")).isTrue();
    }

    @Test
    void testCardRepositoryBeanPresence() {
        // Check if CardRepository bean is present in the application context
        assertThat(applicationContext.containsBean("cardRepository")).isTrue();
    }

    @Test
    void testDeckRepositoryBeanPresence() {
        // Check if DeckRepository bean is present in the application context
        assertThat(applicationContext.containsBean("deckRepository")).isTrue();
    }

    @Test
    void testEnrollRepositoryBeanPresence() {
        // Check if EnrollRepository bean is present in the application context
        assertThat(applicationContext.containsBean("enrollRepository")).isTrue();
    }
}