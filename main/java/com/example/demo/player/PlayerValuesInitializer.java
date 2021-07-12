package com.example.demo.player;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

@Component
public class PlayerValuesInitializer {

    @Bean
    CommandLineRunner commandLineRunner(PlayerRepository repository){
        return  args->{
            Player nole = new Player(1L,
                "Novac Djokovic",
                LocalDate.of(1987, Month.JUNE,22),
                "djokovic@fortech.ro",
                RankEnum.AMATEUR);

            Player medv = new Player(2L,
                "Daniil Medveev",
                LocalDate.of(1975, Month.JULY,4),
                "medveev@fortech.ro",
                RankEnum.BEGINNER);

            Player nadal = new Player(3L,
                    "Rafael Nadal",
                    LocalDate.of(1986, Month.JUNE,8),
                    "nadal@fortech.ro",
                    RankEnum.AMATEUR);

            Player tsisipas = new Player(4L,
                    "Stefanos Tsitsipas",
                    LocalDate.of(1998, Month.AUGUST,12),
                    "tzitipas@fortech.ro",
                    RankEnum.AMATEUR);

            Player zverev = new Player(5L,
                    "Alexander Zverev",
                    LocalDate.of(1997, Month.SEPTEMBER,03),
                    "zverev@fortech.ro",
                    RankEnum.LEGEND);

            Player thiem = new Player(6L,
                    "Dominic Thiem",
                    LocalDate.of(1993, Month.APRIL,20),
                    "thiem@fortech.ro",
                    RankEnum.SEMI_PRO);

            Player rublev = new Player(7L,
                    "Andrey Rublev",
                    LocalDate.of(1997, Month.APRIL,20),
                    "rublev@fortech.ro",
                    RankEnum.SEMI_PRO);

            Player mateo = new Player(8L,
                    "AMateo Berettini",
                    LocalDate.of(1996, Month.APRIL,12),
                    "mateo@fortech.ro",
                    RankEnum.PRO);

            Player federer = new Player(9L,
                    "Roger Federer",
                    LocalDate.of(1981, Month.AUGUST,8),
                    "federer@fortech.ro",
                    RankEnum.WORLD_CLASS);

            List<Player> list= new ArrayList<>();
            list.add(nole);
            list.add(medv);
            list.add(nadal);
            list.add(tsisipas);
            list.add(zverev);
            list.add(thiem);
            list.add(rublev);
            list.add(mateo);
            list.add(federer);
            repository.saveAll(
                    list
            );
        };
    }

}
