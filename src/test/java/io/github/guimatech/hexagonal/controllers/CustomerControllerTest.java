package io.github.guimatech.hexagonal.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.guimatech.hexagonal.dtos.CustomerDTO;
import io.github.guimatech.hexagonal.repositories.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class CustomerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CustomerRepository customerRepository;

    @AfterEach
    void tearDown() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um cliente")
    void testCreate() throws Exception {

        var customer = new CustomerDTO();
        customer.setCpf("12345678901");
        customer.setEmail("john.doe@gmail.com");
        customer.setName("John Doe");

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, CustomerDTO.class);
        Assertions.assertEquals(customer.getName(), actualResponse.getName());
        Assertions.assertEquals(customer.getCpf(), actualResponse.getCpf());
        Assertions.assertEquals(customer.getEmail(), actualResponse.getEmail());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com CPF duplicado")
    void testCreateWithDuplicatedCPFShouldFail() throws Exception {

        var customer = new CustomerDTO();
        customer.setCpf("12345678901");
        customer.setEmail("john.doe@gmail.com");
        customer.setName("John Doe");

        // Cria o primeiro cliente
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        customer.setEmail("john2@gmail.com");

        // Tenta criar o segundo cliente com o mesmo CPF
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Customer already exists"));
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com e-mail duplicado")
    void testCreateWithDuplicatedEmailShouldFail() throws Exception {

        var customer = new CustomerDTO();
        customer.setCpf("12345618901");
        customer.setEmail("john.doe@gmail.com");
        customer.setName("John Doe");

        // Cria o primeiro cliente
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        customer.setCpf("99999918901");

        // Tenta criar o segundo cliente com o mesmo CPF
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Customer already exists"));
    }

    @Test
    @DisplayName("Deve obter um cliente por id")
    void testGet() throws Exception {

        var customer = new CustomerDTO();
        customer.setCpf("12345678901");
        customer.setEmail("john.doe@gmail.com");
        customer.setName("John Doe");

        final var createResult = this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andReturn().getResponse().getContentAsByteArray();

        var customerId = mapper.readValue(createResult, CustomerDTO.class).getId();

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.get("/customers/{id}", customerId)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, CustomerDTO.class);
        Assertions.assertEquals(customerId, actualResponse.getId());
        Assertions.assertEquals(customer.getName(), actualResponse.getName());
        Assertions.assertEquals(customer.getCpf(), actualResponse.getCpf());
        Assertions.assertEquals(customer.getEmail(), actualResponse.getEmail());
    }
}
