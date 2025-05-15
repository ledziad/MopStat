package com.mopstat.mopstat.controller;

import java.util.List;
import com.mopstat.mopstat.service.CsvExportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mopstat.mopstat.dto.DogDTO;
import com.mopstat.mopstat.service.DogService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ExtendWith(SpringExtension.class)
@WebMvcTest(DogController.class)
@AutoConfigureMockMvc(addFilters = false)
class DogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // nowa adnotacja zamiast @MockBean
    @MockitoBean
    private DogService dogService;

    @MockitoBean
    private CsvExportService csvExportService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("GET /api/dogs zwraca listę psów")
    void shouldReturnListOfDogs() throws Exception {
        var dogs = List.of(
                new DogDTO(1, "Tadzio", "Wesoły berserker", "/images/tadzio.png"),
                new DogDTO(2, "Fawor", "Leniwy goblin", "/images/fawor.png")
        );
        given(dogService.getAll()).willReturn(dogs);

        mockMvc.perform(get("/api/dogs"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(dogs.size()))
                .andExpect(jsonPath("$[0].name").value("Tadzio"))
                .andExpect(jsonPath("$[1].personality").value("Leniwy goblin"));
    }

    @Test
    @DisplayName("POST /api/dogs tworzy nowego psa")
    void shouldCreateDog() throws Exception {
        var input = new DogDTO(null, "Nowy", "Pilny pies", "/images/nowy.png");
        var saved = new DogDTO(99, "Nowy", "Pilny pies", "/images/nowy.png");
        given(dogService.create(input)).willReturn(saved);

        mockMvc.perform(post("/api/dogs")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/dogs/99"))
                .andExpect(jsonPath("$.id").value(99))
                .andExpect(jsonPath("$.name").value("Nowy"));
    }
}
