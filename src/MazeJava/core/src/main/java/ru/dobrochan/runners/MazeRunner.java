package ru.dobrochan.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.dobrochan.model.Maze;
import ru.dobrochan.service.MazeService;
import ru.dobrochan.view.ConsoleView;

import java.io.BufferedReader;
import java.io.FileReader;

@Component
public class MazeRunner implements CommandLineRunner {

    @Autowired
    MazeService mazeService;
    @Autowired
    ConsoleView consoleView;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Hello MAze Runner");
        Maze maze = mazeService.generateMaze(10, 10);
//        BufferedReader reader = new BufferedReader(new FileReader("/Users/helllomind/schoo21/A1_Maze_Java-1/src/MazeJava/core/src/main/resources/example_maze.txt"));
//        mazeService.loadMaze(reader);
    }
}
