package io.github.guimatech.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.guimatech.application.customer.CreateCustomerUseCase;
import io.github.guimatech.application.customer.GetCustomerByIdUseCase;
import io.github.guimatech.infrastructure.dtos.NewCustomerDTO;
import io.github.guimatech.infrastructure.jpa.repositories.CustomerJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
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
    private CustomerJpaRepository customerRepository;

    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um cliente")
    void testCreate() throws Exception {

        var customer = new NewCustomerDTO("John Doe", "123.456.789-01", "john.doe@gmail.com");

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, NewCustomerDTO.class);
        Assertions.assertEquals(customer.name(), actualResponse.name());
        Assertions.assertEquals(customer.cpf(), actualResponse.cpf());
        Assertions.assertEquals(customer.email(), actualResponse.email());
    }

    @Test
    @DisplayName("Não deve cadastrar um cliente com CPF duplicado")
    void testCreateWithDuplicatedCPFShouldFail() throws Exception {

        var customer = new NewCustomerDTO("John Doe", "123.456.789-01", "john.doe@gmail.com");

        // Cria o primeiro cliente
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn().getResponse().getContentAsByteArray();

        customer = new NewCustomerDTO("John Doe", "123.456.789-01", "john2@gmail.com");

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

        var customer = new NewCustomerDTO("John Doe", "123.456.789-01", "john.doe@gmail.com");

        // Cria o primeiro cliente
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn().getResponse().getContentAsByteArray();

        customer = new NewCustomerDTO("John Doe", "999.999.189-01", "john.doe@gmail.com");

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

        var customer = new NewCustomerDTO("John Doe", "123.456.789-01", "john.doe@gmail.com");

        final var createResult = this.mvc.perform(
                        MockMvcRequestBuilders.post("/customers")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(customer))
                )
                .andReturn().getResponse().getContentAsByteArray();

        var customerId = mapper.readValue(createResult, CreateCustomerUseCase.Output.class).id();

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.get("/customers/{id}", customerId)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, GetCustomerByIdUseCase.Output.class);
        Assertions.assertEquals(customerId, actualResponse.id());
        Assertions.assertEquals(customer.name(), actualResponse.name());
        Assertions.assertEquals(customer.cpf(), actualResponse.cpf());
        Assertions.assertEquals(customer.email(), actualResponse.email());
    }
}