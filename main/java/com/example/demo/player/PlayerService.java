package com.example.demo.player;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


//PlayerServices has to be instanciated
//Service(for readability) same thing as Component

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public List<PlayerResponseDTO> getPlayers() {
        List<Player> playerEntities = playerRepository.findAll();
        List<PlayerResponseDTO> responseList = new ArrayList<>();
        for (Player player : playerEntities) {
            PlayerResponseDTO responseDTO = toResponseDTO(player);
            responseList.add(responseDTO);
        }
        return responseList;
        // return playerEntities.stream().
//                map(player -> toResponseDTO(player)).collect(Collectors.toList());
    }

    public void addNewPlayer(PlayerRequestDTO playerRequestDTO) {
        if (mailAlreadyExists(playerRequestDTO.getEmail())) {
            throw new IllegalStateException("Sorry this email is taken");
        } else {
            Player player = toEntity(playerRequestDTO);
            playerRepository.save(player);
        }
    }

    public void deletePlayer(String externalId) {
        Player playerToDelete = playerRepository.findPlayerByExternalId(externalId)
                .orElseThrow(() -> new IllegalStateException(
                        "Player with id" + externalId + " does not exists"));
        playerRepository.delete(playerToDelete);
    }

    @Transactional
    public void updatePlayer(String externalId, PlayerRequestDTO playerRequestDTO) {
        Player player = playerRepository.findPlayerByExternalId(externalId)
                .orElseThrow(() -> new IllegalStateException(
                        "Player with id" + externalId + " does not exists")
                );

            player.setName(playerRequestDTO.getName());
            player.setRank(RankEnum.get(playerRequestDTO.getRank()));
            player.setDateOfBirth(playerRequestDTO.getDateOfBirth());
            player.setEmail(playerRequestDTO.getEmail());
            playerRepository.save(player);
    }

    private Player toEntity(PlayerRequestDTO dto) {
        Player entity = new Player();
        entity.setEmail(dto.getEmail());
        entity.setName(dto.getName());
        entity.setRank(RankEnum.get(dto.getRank()));
        entity.setDateOfBirth(dto.getDateOfBirth());
        return entity;
    }

    private PlayerResponseDTO toResponseDTO(Player entity) {
        PlayerResponseDTO responseDTO = new PlayerResponseDTO();
        responseDTO.setAge(entity.getAge());
        responseDTO.setEmail(entity.getEmail());
        responseDTO.setRank(entity.getRank());
        responseDTO.setName(entity.getName());
        responseDTO.setDateOfBirth(entity.getDateOfBirth());
        responseDTO.setExternalId(entity.getExternalId());
        return responseDTO;
    }

    private Boolean mailAlreadyExists(String email) {
        Optional<Player> playerOptional = playerRepository
                    .findPlayerByEmail(email);
            return playerOptional.isPresent();
    }
}
