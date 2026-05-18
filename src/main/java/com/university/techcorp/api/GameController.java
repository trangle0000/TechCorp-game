package com.university.techcorp.api;

import com.university.techcorp.service.GameService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    @GetMapping("/state")
    public String state() {
        return gameService.getState();
    }

    @PostMapping("/action/{choice}")
    public String action(@PathVariable int choice) {
        return gameService.performAction(choice);
    }

    @PostMapping("/new")
    public String newGame() {
        return gameService.resetGame();
    }
}
