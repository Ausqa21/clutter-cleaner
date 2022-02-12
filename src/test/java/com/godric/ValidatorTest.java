package com.godric;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidatorTest {

    @Test
    @DisplayName("Test to check if provided path is valid/exists")
    void shouldValidatePath() {
        assertTrue(Validator.validatePath("."));
        assertFalse(Validator.validatePath("null"));
    }
}