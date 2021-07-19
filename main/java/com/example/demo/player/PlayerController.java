package com.example.demo.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

//request from client are gonna come in through the controller
//we shouldn't have any logic here we only need to accept request and send the, back to the caller

@CrossOrigin(origins = "*", exposedHeaders = "Access-Control-Allow-Origin")
@RestController
@RequestMapping("api/v1/player")
public class PlayerController {
    //API LAYER!!!!
    //all of the resources for our API

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        //this.playerService = new PlayerService();
        //we should avoid this and use dependecy injection
        //we put autowired so playerService will be 'magicaly' instantiated
        //we also need to tell that PlayerService is a class that needs to be instanciated (Spring bean)
        //we put @Service on PlayerService
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

//    @PutMapping(path = "/{externalId}")
//    public void updatePlayer(
//            @PathVariable("externalId") String externalId,
//           @RequestBody PlayerRequestDTO playerRequestDTO){
//
//        playerService.updatePlayer(externalId,playerRequestDTO);
//    }

}
