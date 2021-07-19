package com.example.demo.player;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WebMvcTest(PlayerController.class)
class PlayerControllerTest2 {

    @MockBean
    PlayerService playerService;
    ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mapper.registerModule(new JavaTimeModule())
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void getPlayers() throws Exception {

        PlayerResponseDTO responseDTO1 = new PlayerResponseDTO();
        responseDTO1.setAge(20);
        responseDTO1.setEmail("dargos@mee.com");
        responseDTO1.setRank(RankEnum.PRO);
        responseDTO1.setName("Dragoshell");
        responseDTO1.setId(40L);

        PlayerResponseDTO responseDTO2 = new PlayerResponseDTO();
        responseDTO2.setAge(24);
        responseDTO2.setEmail("dargdadas@mee.com");
        responseDTO2.setRank(RankEnum.PRO);
        responseDTO2.setName("Nicu");
        responseDTO2.setId(47L);

        List<PlayerResponseDTO> listDeDto = Arrays.asList(responseDTO1, responseDTO2);
        when(playerService.getPlayers()).thenReturn(listDeDto);
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("http://localhost:8080/api/v1/player"))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andReturn();

        List<PlayerResponseDTO> playerResponseDTOList = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<PlayerResponseDTO>>() {
        });
        assertEquals(listDeDto.size(), playerResponseDTOList.size());
        assertEquals(listDeDto.get(0).getId(),playerResponseDTOList.get(0).getId());
    }

    @Test
    void registerNewPlayer() throws Exception {

        PlayerRequestDTO playerRequestDTO = new PlayerRequestDTO
                ("Dragos", LocalDate.of(1987, Month.JUNE, 22), "dragosawfawfh@me.com", "PRO");


        RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/api/v1/player/")
                .content(mapper.writeValueAsString(playerRequestDTO))
                .contentType(
                        MediaType.APPLICATION_JSON
                );
        mockMvc.perform(requestBuilder)
                .andExpect( status().isOk());
        verify(playerService, times(1)).addNewPlayer(any(PlayerRequestDTO.class));

    }

    @Test
    void deletePlayer() throws Exception {

//        RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(
//                "http://localhost:8080/api/v1/player/{externalId}"
//        )
//                .contentType(
//                        MediaType.APPLICATION_JSON
//                );
//        mockMvc.perform(requestBuilder)
//                .andExpect( status().isOk());
//        verify(playerService).deletePlayer("externalId");


        String extid = "extid";
        mockMvc.perform(MockMvcRequestBuilders.delete("http://localhost:8080/api/v1/player/{externalId}",extid))
                .andExpect(status().isOk());
        verify(playerService, times(1)).deletePlayer(extid);

    }

    @Test
    void updatePlayerWithId() throws Exception {
        PlayerRequestDTO playerRequestDTO1 = new PlayerRequestDTO
                ("Dragos", LocalDate.of(1987, Month.JUNE, 22), "dragosawfawfh@me.com", "PRO");

       // when(playerService.updatePlayerWithId(1L,playerRequestDTO1)).thenReturn(Optional.empty());

        RequestBuilder requestBuilder = MockMvcRequestBuilders.put(
                "http://localhost:8080/api/v1/player/{id}",1L
        )
                .content(mapper.writeValueAsString(playerRequestDTO1))
                .contentType(
                        MediaType.APPLICATION_JSON
                );
        mockMvc.perform(requestBuilder)
                .andExpect(status().isOk());

        verify(playerService, times(1)).updatePlayerWithId(eq(1L),any(PlayerRequestDTO.class));

    }

    @Test
    void getPlayer() throws Exception {

        Long id =1L;
        PlayerResponseDTO responseDTO1 = new PlayerResponseDTO();
        responseDTO1.setName("Dragos");
        responseDTO1.setId(1L);
        responseDTO1.setEmail("dargos@mee.com");

        Mockito.when(playerService.findById(id)).thenReturn(responseDTO1);


        mockMvc.perform((MockMvcRequestBuilders.get("http://localhost:8080/api/v1/player/1")))
            .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(responseDTO1), true));

        verify(playerService, times(1)).findById(responseDTO1.getId());



    }
}
