package cub.game;

import cub.game.app.Game;
import cub.game.app.Gamer;
import cub.game.exception.GameCreationException;
import cub.game.exception.GameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * Rules:
 * - При создании игры указывается кол-во игроков. Минимум 2
 * - Порядок ходов меняется каждый раунд в свободном порядке
 * - Каждый игрок может сходить только один раз за раунд
 * - После создания игры дается тема рисунка
 * - Время хода игрока составляет не более 2 минут
 */
@RestController
@RequestMapping("/api/game")
public class GameController {

    /**
     * /api/game
     * - @GetMapping or @PostMapping
     * - @GetMapping("/qwe") - GET /api/game/qwe
     *
     * @GetMapping("/{id}") - GET /api/game/{id}
     */

    private final static String[] GAME_TOPICS = {
            "CIRCLE", "Panter", "Magic", "Wow", "Pony", "Rick&Morty"
    };
    private final List<Game> allGames = new ArrayList<>();

    @PostMapping
    public Game getGame(@RequestBody NewGameParams params) {
        if (params.getGamers() < 2)
            throw new GameCreationException("Players count must be greater than 1");
        Game game = new Game();
        game.setId(UUID.randomUUID());
        game.setTopic(getTopic());
        game.setCurrentGamer(null);
        game.setMaxGamersCount(params.getGamers());
        game.setGamersQueue(new ArrayList<>());
        game.setGamersList(new ArrayList<>());
        allGames.add(game);
        return game;
    }

    @GetMapping("/{id}")
    public Game getGameById(@PathVariable("id") UUID id) {
        for (Game g : allGames) {
            if (id.equals(g.getId()))
                return g;
        }
        throw new GameNotFoundException("Game not found");
    }

    @PostMapping("/{id}")
    public Game joinGame(
            @PathVariable("id") UUID id,
            @RequestBody JoinGameParams params
    ) {
        Game foundGame = getGameById(id);
        final var currentGamers = foundGame.getGamersList().size();
        if (foundGame.getMaxGamersCount() >= currentGamers)
            throw new GameCreationException("No free places");
        Gamer newGamer = new Gamer();
        newGamer.setId(UUID.randomUUID());
        String gamerNickname = "Gamer at " + LocalDateTime.now();
        if (params != null
                && params.getNickname() != null
                && !params.getNickname().isEmpty())
            gamerNickname = params.getNickname();

        newGamer.setTitle(gamerNickname);
        foundGame.getGamersList().add(newGamer);
        return foundGame;
    }

    @PostMapping("/{id}/draw")
    public void drawLine(
            @PathVariable("id") UUID id
            /* some args */
    ) {
        Game currentGame = getGameById(id);
        // TODO: Implement game round
    }

    @GetMapping
    public List<Game> getAllGames() {
        return allGames;
    }

    private String getTopic() {
        Random random = new Random(System.currentTimeMillis());
        return GAME_TOPICS[random.nextInt(GAME_TOPICS.length)];
    }
}
