package com.example.demo.contreoller;
import com.example.demo.player.Dto.PlayerRequestDTO;
import com.example.demo.player.Dto.PlayerResponseDTO;
import com.example.demo.player.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RestController
@RequestMapping("api/v1/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public List<PlayerResponseDTO> getPlayers() {
        return playerService.getPlayers();
    }

    @PostMapping
    public void registerNewPlayer(@RequestBody PlayerRequestDTO playerRequestDTO) {
        playerService.addNewPlayer(playerRequestDTO);
    }

    @DeleteMapping("/{externalId}")
    public void deletePlayer(@PathVariable("externalId") String externalId) {
        playerService.deletePlayer(externalId);
    }

    @PutMapping(path = "/{id}")
    public void updatePlayerWithId(
            @PathVariable("id") Long id,
            @RequestBody PlayerRequestDTO playerRequestDTO){

        playerService.updatePlayerWithId(id, playerRequestDTO);
    }

    @GetMapping("{id}")
    PlayerResponseDTO getPlayer(@PathVariable Long id) {
        PlayerResponseDTO playerRequestDTO = playerService.findById(id);
        return playerRequestDTO;
    }

}
