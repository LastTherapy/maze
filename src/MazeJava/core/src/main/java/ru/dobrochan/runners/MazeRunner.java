package ru.dobrochan.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.dobrochan.model.Maze;
import ru.dobrochan.service.MazeService;

@Component
public class MazeRunner implements CommandLineRunner {
    @Autowired
    Maze maze;
    @Autowired
    MazeService mazeService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello MAze Runner");
        mazeService.loadMaze("/Users/helllomind/schoo21/A1_Maze_Java-1/src/MazeJava/core/src/main/resources/example_maze.txt");
    }
}
