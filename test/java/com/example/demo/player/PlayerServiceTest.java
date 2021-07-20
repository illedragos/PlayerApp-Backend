package com.example.demo.player;

import com.example.demo.player.Dto.PlayerRequestDTO;
import com.example.demo.player.Dto.PlayerResponseDTO;
import com.example.demo.player.model.Player;
import com.example.demo.player.model.RankEnum;
import com.example.demo.player.repository.PlayerRepository;
import com.example.demo.player.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
class PlayerServiceTest {

    @Mock
    PlayerRepository playerRepository;

    PlayerService playerService;

    @BeforeEach
    void setUp() {
        playerService = new PlayerService(playerRepository);

    }


    @Test
    public void addNewPlayerWhenEmailExists() {
        PlayerRequestDTO playerRequestDTOExpected = new PlayerRequestDTO
                ("Dragos", LocalDate.of(1987, Month.JUNE, 22), "dragosawfawfh@me.com", "PRO");

        Player testPlayer = new Player
                ("Dragos", LocalDate.of(1987, Month.JUNE, 22), "dragosawfawfh@me.com", RankEnum.PRO);
        when(playerRepository.findPlayerByEmail(Mockito.any(String.class))).thenReturn(Optional.of(testPlayer));

        assertThrows(
                IllegalStateException.class,
                () ->playerService.addNewPlayer(playerRequestDTOExpected));

    }

    @Test
    public void addNewPlayerWhenEmailDoesntExists() {

        PlayerRequestDTO playerRequestDTOExpected = new PlayerRequestDTO
                ("Dragos", LocalDate.of(1987, Month.JUNE, 22), "dragosawfawfh@me.com", "PRO");


        Player testPlayer = new Player
                ("Dragos", LocalDate.of(1987, Month.JUNE, 22), "dragosawfawfh@me.com", RankEnum.PRO);
        when(playerRepository.save(Mockito.any(Player.class))).thenReturn(testPlayer);


        ArgumentCaptor<Player> playerArgumentCaptor = ArgumentCaptor.forClass(Player.class);


        playerService.addNewPlayer(playerRequestDTOExpected);
        verify(playerRepository).save(playerArgumentCaptor.capture());
        Player savedPlayer = playerArgumentCaptor.getValue();

        assertEquals("Dragos", savedPlayer.getName());

    }

    @Test
    void getAllPlayers() {

        List<Player> expectedPlayers = new ArrayList<>();
        Player player1 = new Player();
        player1.setExternalId("extId1");
        player1.setName("player1");
        player1.setEmail("email1");
        player1.setDateOfBirth(LocalDate.of(1991, 1, 1));
        player1.setRank(RankEnum.PRO);

        Player player2 = new Player();
        player2.setExternalId("extId2");
        player2.setName("player2");
        player2.setEmail("email2");
        player2.setDateOfBirth(LocalDate.of(1992, 2, 2));
        player2.setRank(RankEnum.AMATEUR);

        expectedPlayers.add(player1);
        expectedPlayers.add(player2);

        when(playerRepository.findAll()).thenReturn(expectedPlayers);

        List<PlayerResponseDTO> result = playerService.getPlayers();

        assertEquals(2, result.size());

    }

    @Test
    public void deletePlayer_whenRepositoryFindPlayer_expectToDeletePlayer() {

        Player player2 = new Player();
        player2.setExternalId("extId2");
        player2.setName("player2");
        player2.setEmail("email2");
        player2.setDateOfBirth(LocalDate.of(1992, 2, 2));
        player2.setRank(RankEnum.AMATEUR);
        when(playerRepository.findPlayerByExternalId("extId")).thenReturn(Optional.of(player2));
        playerService.deletePlayer("extId");
        verify(playerRepository).delete(player2);
    }

    @Test
    public void deletePlayer_whenRepositoryNotFindPlayer_expectException() {
        when(playerRepository.findPlayerByExternalId("extId")).thenReturn(Optional.empty());

        assertThrows(
                IllegalStateException.class,
                () -> playerService.deletePlayer("extId"));
    }

    @Test
    public void updatePlayerWithId() {

        PlayerRequestDTO playerRequestDTOExpected = new PlayerRequestDTO
                ("Dragos", LocalDate.of(1987, Month.JUNE, 22), "dragosawfawfh@me.com", "PRO");


        Player player = new Player();
        player.setExternalId("extId1");
        player.setName("player1");
        player.setEmail("email1");
        player.setDateOfBirth(LocalDate.of(1991, 1, 1));
        player.setRank(RankEnum.PRO);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        playerService.updatePlayerWithId(1L, playerRequestDTOExpected);
        verify(playerRepository, times(1)).save(player);
        assertEquals("Dragos", player.getName());

    }

    @Test
    public void updatePlayerWithInvalidIdException() {

        PlayerRequestDTO playerRequestDTOExpected = new PlayerRequestDTO
                ("Dragos", LocalDate.of(1987, Month.JUNE, 22), "dragosawfawfh@me.com", "PRO");


        long id = 1L;
        when(playerRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(
                IllegalStateException.class,
                () -> playerService.updatePlayerWithId(id, playerRequestDTOExpected));

    }

    @Test
    public void forGivenId_shouldReturnPlayerResponseWithCorrectFields() {

        Player player = new Player();
        player.setExternalId("extId1");
        player.setName("player1");
        player.setEmail("email1");
        player.setDateOfBirth(LocalDate.of(1991, 1, 1));
        player.setRank(RankEnum.PRO);

        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        PlayerResponseDTO result = playerService.findById(1L);

        assertEquals("extId1", result.getExternalId());
        assertEquals("player1", result.getName());
        assertEquals("email1", result.getEmail());
        assertEquals(LocalDate.of(1991, 1, 1), result.getDateOfBirth());
        assertEquals(RankEnum.PRO, result.getRank());

    }

    @Test
    public void FindPlayerResponseByIdInvalid() {

        when(playerRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class,()->playerService.findById(1L));

    }



}
