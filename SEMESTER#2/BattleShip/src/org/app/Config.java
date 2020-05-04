package org.app;

import org.game.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class Config {
    @Bean
    @Scope("prototype")
    public Logic logic() {
        return new Logic(GameMode.ONE_PLAYER);
    }

    @Bean
    @Scope("prototype")
    public Logic logicOnePlayer() {
        return new Logic(GameMode.ONE_PLAYER);
    }

    @Bean
    @Scope("prototype")
    public Logic logicTwoPlayers() {
        return new Logic(GameMode.TWO_PLAYERS);
    }

    @Bean
    @Scope("singleton")
    public GameField playerField() {
        return new GameField(GameField.PLAYER_MODE);
    }

    @Bean
    @Scope("prototype")
    public GameField enemyField() {
        return new GameField(GameField.ENEMY_MODE);
    }

    @Bean
    @Scope("prototype")
    public GameField nonField() {
        return new GameField();
    }

    @Bean
    @Scope("prototype")
    public AI enemyAILow() {
        return new AI(playerField(), IntelligenceLevel.LOW);
    }

    @Bean
    @Scope("prototype")
    public AI enemyAIMedium() {
        return new AI(playerField(), IntelligenceLevel.MEDIUM);
    }

    @Bean
    @Scope("prototype")
    public AI enemyAIHigh() {
        return new AI(playerField(), IntelligenceLevel.HIGH);
    }

    @Bean
    @Scope("prototype")
    public Ship ship1() {
        return new Ship(1);
    }
    @Bean
    @Scope("prototype")
    public Ship ship2() {
        return new Ship(2);
    }
    @Bean
    @Scope("prototype")
    public Ship ship3() {
        return new Ship(3);
    }
    @Bean
    @Scope("prototype")
    public Ship ship4() {
        return new Ship(4);
    }

    @Bean
    @Scope("prototype")
    public Ship ship0() {
        return new Ship();
    }
}
