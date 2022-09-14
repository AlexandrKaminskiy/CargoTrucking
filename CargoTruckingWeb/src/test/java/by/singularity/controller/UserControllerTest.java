package by.singularity.controller;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserControllerTest {

    @Autowired
    UserController userController;


    @Test
    void getAll() {
        Assertions.assertNotNull(userController);

    }

    @Test
    void getById() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void register() {
    }

    @Test
    void deleteUsers() {
    }
}