package com.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {

    @Test
    void greetShouldReturnCorrectMessage() {
        assertEquals("Hello, Jenkins!", App.greet("Jenkins"));
    }
}
