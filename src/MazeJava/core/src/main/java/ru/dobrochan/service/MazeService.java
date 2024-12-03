package ru.dobrochan.service;
import ru.dobrochan.model.Maze;
import ru.dobrochan.model.MazeSolution;

import java.io.BufferedReader;
import java.io.IOException;


public interface MazeService {

    Maze generateMaze(int width, int height);

    void saveMaze(Maze maze, String path) throws IOException;

    Maze loadMaze(BufferedReader reader) throws IOException;

    MazeSolution solveMaze(Maze maze);
}
