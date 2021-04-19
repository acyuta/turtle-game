package cub.game.app;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Rules:
 * - При создании игры указывается кол-во игроков. Минимум 2
 * - Порядок ходов меняется каждый раунд в свободном порядке
 * - Каждый игрок может сходить только один раз за раунд
 * - После создания игры дается тема рисунка
 * - Время хода игрока составляет не более 2 минут
 */
@Getter
@Setter
public class Game {
    private UUID id;
    private int maxGamersCount;
    private String topic;
    private List<Gamer> gamersList;
    private List<Integer> gamersQueue;
    private Integer currentGamer;
    private LocalDateTime gamerDrawExpiration;


}
