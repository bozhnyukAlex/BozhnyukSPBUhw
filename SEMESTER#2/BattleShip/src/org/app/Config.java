package org.app;

import org.game.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
@ComponentScan("org.game")
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
    @Scope("prototype")
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

    /*@Bean
    @Scope("prototype")
    public AI enemyAILow() {
        return new AI(new GameField(), IntelligenceLevel.LOW);
    }

    @Bean
    @Scope("prototype")
    public AI enemyAIMedium() {
        return new AI(new GameField(), IntelligenceLevel.MEDIUM);
    }

    @Bean
    @Scope("prototype")
    public AI enemyAIHigh() {
        return new AI(new GameField(), IntelligenceLevel.HIGH);
    }*/


}
