package io.github.guimatech.hexagonal.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.guimatech.hexagonal.application.usecases.CreateCustomerUseCase;
import io.github.guimatech.hexagonal.infraestructure.dtos.NewEventDTO;
import io.github.guimatech.hexagonal.infraestructure.dtos.SubscribeDTO;
import io.github.guimatech.hexagonal.infraestructure.models.Customer;
import io.github.guimatech.hexagonal.infraestructure.models.Partner;
import io.github.guimatech.hexagonal.infraestructure.repositories.CustomerRepository;
import io.github.guimatech.hexagonal.infraestructure.repositories.EventRepository;
import io.github.guimatech.hexagonal.infraestructure.repositories.PartnerRepository;
import org.junit.jupiter.api.AfterEach;
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
import org.springframework.transaction.annotation.Transactional;

@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest
class EventControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PartnerRepository partnerRepository;

    @Autowired
    private EventRepository eventRepository;

    private Customer johnDoe;
    private Partner disney;

    @BeforeEach
    void setUp() {
        johnDoe = customerRepository.save(new Customer(null, "John Doe", "123", "john@gmail.com"));
        disney = partnerRepository.save(new Partner(null, "Disney", "456", "disney@gmail.com"));
    }

    @AfterEach
    void tearDown() {
        eventRepository.deleteAll();
        customerRepository.deleteAll();
        partnerRepository.deleteAll();
    }

    @Test
    @DisplayName("Deve criar um evento")
    void testCreate() throws Exception {

        var event = new NewEventDTO(
                "Disney on Ice",
                "2021-01-01",
                100,
                disney.getId());

        final var result = this.mvc.perform(
                        MockMvcRequestBuilders.post("/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(event))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var actualResponse = mapper.readValue(result, NewEventDTO.class);
        Assertions.assertEquals(event.date(), actualResponse.date());
        Assertions.assertEquals(event.totalSpots(), actualResponse.totalSpots());
        Assertions.assertEquals(event.name(), actualResponse.name());
    }

    @Test
    @Transactional
    @DisplayName("Deve comprar um ticket de um evento")
    void testReserveTicket() throws Exception {

        var event = new NewEventDTO(
                "Disney on Ice",
                "2021-01-01",
                100,
                disney.getId());

        final var createResult = this.mvc.perform(
                        MockMvcRequestBuilders.post("/events")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(event))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").isNumber())
                .andReturn().getResponse().getContentAsByteArray();

        var eventId = mapper.readValue(createResult, CreateCustomerUseCase.Output.class).id();

        var sub = new SubscribeDTO(johnDoe.getId(), null);

        this.mvc.perform(
                        MockMvcRequestBuilders.post("/events/{id}/subscribe", eventId)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(mapper.writeValueAsString(sub))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn().getResponse().getContentAsByteArray();

        var actualEvent = eventRepository.findById(eventId).get();
        Assertions.assertEquals(1, actualEvent.getTickets().size());
    }
}