package org.example.tests;

import org.example.pages.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class LoginTest extends BaseTest {

    private LoginPage loginPage;

    @BeforeEach
    void init() {
        page.navigate("https://www.saucedemo.com/");
        loginPage = new LoginPage(page);
    }

    public static Stream<Arguments> loginDataProvider() {
        return Stream.of(
                Arguments.of("standard_user", "secret_sauce", true),
                Arguments.of("locked_out_user", "secret_sauce", false),
                Arguments.of("problem_user", "secret_sauce", true),
                Arguments.of("performance_glitch_user", "secret_sauce", true),
                Arguments.of("error_user", "secret_sauce", true),
                Arguments.of("visual_user", "secret_sauce", true),
                Arguments.of("standard_user", "wrong_password", false),
                Arguments.of("invalid_user", "secret_sauce", false),
                Arguments.of("", "", false),
                Arguments.of("standard_user", "", false),
                Arguments.of("", "secret_sauce", false)
        );
    }

    @ParameterizedTest
    @MethodSource("loginDataProvider")
    @DisplayName("Login Test with Multiple User Credentials")
    public void loginTest(String username, String password, boolean shouldLogin) {
        loginPage.login(username, password);

        if (shouldLogin) {
            assertTrue(loginPage.isLoggedIn());
        } else {
            assertTrue(loginPage.isErrorDisplayed(), "Expected an error message but none was displayed.");
        }
    }
}
