package by.singularity.repository.impl;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.context.config.Profiles;
import org.springframework.boot.test.autoconfigure.data.elasticsearch.DataElasticsearchTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.*;

//@RunWith(SpringRunner.class)
@ActiveProfiles(profiles = {Profiles.INCLUDE_PROFILES_PROPERTY_NAME})
@DataElasticsearchTest
@DirtiesContext
@RequiredArgsConstructor
class ClientRepositoryTest {

    private final ClientRepository clientRepository;

    @Test
    void findByName() {
        var clients = clientRepository.findByName("sanya");
        Assertions.assertNotNull(clients);
    }
}