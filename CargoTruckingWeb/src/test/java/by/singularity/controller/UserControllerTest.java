package by.singularity.controller;

import com.mysema.commons.lang.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mapstruct.control.MappingControl;
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