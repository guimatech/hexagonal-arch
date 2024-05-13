package io.github.guimatech.infrastructure.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.guimatech.application.partner.CreatePartnerUseCase;
import io.github.guimatech.application.partner.GetPartnerByIdUseCase;
import io.github.guimatech.infrastructure.dtos.NewPartnerDTO;
import io.github.guimatech.infrastructure.repositories.PartnerDatabaseRepository;
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
class PartnerControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private PartnerDatabaseRepository partnerDatabaseRepository;

    @BeforeEach
    void setUP() {
        partnerDatabaseRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um parceiro")
    void testCreate() throws Exception {

        var partner = new NewPartnerDTO("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, NewPartnerDTO.class);
        Assertions.assertEquals(partner.name(), actualResponse.name());
        Assertions.assertEquals(partner.cnpj(), actualResponse.cnpj());
        Assertions.assertEquals(partner.email(), actualResponse.email());
    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com CNPJ duplicado")
    void testCreateWithDuplicatedCPFShouldFail() throws Exception {

        var partner = new NewPartnerDTO("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");

        // Cria o primeiro parceiro
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn().getResponse().getContentAsByteArray();

        partner = new NewPartnerDTO("John Doe", "41.536.538/0001-00", "john2@gmail.com");

        // Tenta criar o segundo parceiro com o mesmo CPF
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Partner already exists"));
    }

    @Test
    @DisplayName("Não deve cadastrar um parceiro com e-mail duplicado")
    void testCreateWithDuplicatedEmailShouldFail() throws Exception {

        var partner = new NewPartnerDTO("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");

        // Cria o primeiro parceiro
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.header().exists("Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isString())
                .andReturn().getResponse().getContentAsByteArray();

        partner = new NewPartnerDTO("John Doe", "66.666.538/0001-00", "john.doe@gmail.com");

        // Tenta criar o segundo parceiro com o mesmo CNPJ
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andExpect(MockMvcResultMatchers.status().isUnprocessableEntity())
                .andExpect(MockMvcResultMatchers.content().string("Partner already exists"));
    }

    @Test
    @DisplayName("Deve obter um parceiro por id")
    void testGet() throws Exception {

        var partner = new NewPartnerDTO("John Doe", "41.536.538/0001-00", "john.doe@gmail.com");

        final var createResult = this.mvc.perform(
                        MockMvcRequestBuilders.post("/partners")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(partner))
                )
                .andReturn().getResponse().getContentAsByteArray();

        var partnerId = mapper.readValue(createResult, CreatePartnerUseCase.Output.class).id();

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.get("/partners/{id}", partnerId)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, GetPartnerByIdUseCase.Output.class);
        Assertions.assertEquals(partnerId, actualResponse.id());
        Assertions.assertEquals(partner.name(), actualResponse.name());
        Assertions.assertEquals(partner.cnpj(), actualResponse.cnpj());
        Assertions.assertEquals(partner.email(), actualResponse.email());
    }
}