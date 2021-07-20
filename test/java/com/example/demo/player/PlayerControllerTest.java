package com.example.demo.player;
import com.example.demo.controller.PlayerController;
import com.example.demo.player.Dto.PlayerRequestDTO;
import com.example.demo.player.Dto.PlayerResponseDTO;
import com.example.demo.player.model.RankEnum;
import com.example.demo.player.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringJUnit4ClassRunner.class)
class PlayerControllerTest {

    @Mock
    PlayerService playerService;

    @InjectMocks
    PlayerController playerController;

    @BeforeEach
    void setUp() {

    }

    @Test
    void getPlayers() {

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

        final List<PlayerResponseDTO> players = playerController.getPlayers();

        assertEquals(listDeDto, players);

    }

    @Test
    void registerNewPlayer() {
        PlayerRequestDTO playerRequestDTO = new PlayerRequestDTO
                ("Dragos", LocalDate.of(1987, Month.JUNE, 22), "dragosawfawfh@me.com", "PRO");

        ArgumentCaptor<PlayerRequestDTO> playerRequestDTOArgumentCaptor = ArgumentCaptor.forClass(PlayerRequestDTO.class);

        playerController.registerNewPlayer(playerRequestDTO);
        verify(playerService).addNewPlayer(playerRequestDTOArgumentCaptor.capture());

        assertEquals(playerRequestDTO,playerRequestDTOArgumentCaptor.getValue());

    }

    @Test
    void deletePlayer() {

           String externalId="extId";
           ArgumentCaptor<String> stringCapture = ArgumentCaptor.forClass(String.class);
           playerController.deletePlayer(externalId);
           verify(playerService).deletePlayer(stringCapture.capture());
           assertEquals(externalId, stringCapture.getValue());                                                   
    }

    @Test
    void updatePlayerWithId() {
        PlayerRequestDTO playerRequestDTO1 = new PlayerRequestDTO
                ("Dragos", LocalDate.of(1987, Month.JUNE, 22), "dragosawfawfh@me.com", "PRO");
        Long id = 1L;
        ArgumentCaptor<PlayerRequestDTO> playerArgumentCaptor = ArgumentCaptor.forClass(PlayerRequestDTO.class);
        ArgumentCaptor<Long> longCapture = ArgumentCaptor.forClass(Long.class);
        playerController.updatePlayerWithId(id, playerRequestDTO1);
        verify(playerService).updatePlayerWithId(longCapture.capture(), playerArgumentCaptor.capture());
        assertEquals(id, longCapture.getValue());
        assertEquals(playerRequestDTO1, playerArgumentCaptor.getValue());

    }

    @Test
    void getPlayer() {
        Long id =8L;
        PlayerResponseDTO responseDTO1 = new PlayerResponseDTO();
        responseDTO1.setAge(20);
        responseDTO1.setEmail("dargos@mee.com");
        responseDTO1.setRank(RankEnum.PRO);
        when(playerService.findById(id)).thenReturn(responseDTO1);
        PlayerResponseDTO responseExpected = playerController.getPlayer(id);
        assertEquals(responseDTO1,responseExpected);

    }
}
