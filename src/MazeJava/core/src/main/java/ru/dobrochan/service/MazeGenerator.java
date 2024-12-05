package ru.dobrochan.service;

import ru.dobrochan.model.Maze;

public interface MazeGenerator {
    Maze generateMaze(int width, int height);
}
