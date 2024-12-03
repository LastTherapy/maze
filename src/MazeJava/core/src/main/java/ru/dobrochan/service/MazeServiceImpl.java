package ru.dobrochan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import ru.dobrochan.config.PrintSymbolConfig;
import ru.dobrochan.model.Maze;
import ru.dobrochan.model.MazeSolution;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class MazeServiceImpl implements MazeService{

    private final PrintSymbolConfig printSymbolConfig;

    @Autowired
    public MazeServiceImpl(PrintSymbolConfig printSymbolConfig) {
        this.printSymbolConfig = printSymbolConfig;
    }

    @Override
    public Maze generateMaze(int width, int height) {
        Maze maze = new Maze(printSymbolConfig);

        int[][] matrixA = new int[height][width];
        int[][] matrixB = new int[height][width];
        // create empty "string"
        int[] line = new int[width];
        // creating new 'sets'
        for (int i = 0; i < height; i++) {
            line[i] = i+1;
        }

        return new Maze(printSymbolConfig);
    }

    @Override
    public void saveMaze(Maze maze, String path) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(path));
        writer.write(maze.getHeight() + " " + maze.getWidth() + "\n");
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                writer.write(maze.getMatrixA()[i][j] + " ");
            }
            writer.write("\n");
        }
        writer.write("\n");
        for (int i = 0; i < maze.getHeight(); i++) {
            for (int j = 0; j < maze.getWidth(); j++) {
                writer.write(maze.getMatrixB()[i][j] + " ");
            }
            writer.write("\n");
        }
        writer.close();
    }

    @Override
    public Maze loadMaze(BufferedReader reader) throws IOException {
        Maze maze = new Maze(printSymbolConfig);

        String[] args = reader.readLine().split(" ");
        int height = Integer.parseInt(args[0]);
        int width = Integer.parseInt(args[1]);

        maze.setHeight(height);
        maze.setWidth(width);

        int[][] matrixA = loadMatrix(reader, height, width);
        // skip empty line
        reader.readLine();
        int[][] matrixB = loadMatrix(reader, height, width);

        maze.setMatrixA(matrixA);
        maze.setMatrixB(matrixB);

        maze.print();
        return maze;
    }

    private int[][] loadMatrix(BufferedReader reader, int height, int width) throws IOException {
        int[][] matrix = new int[height][width];
        for (int i = 0; i < height; i++) {
            String[] args = reader.readLine().split(" ");
            for (int j = 0; j < width; j++) {
                matrix[i][j] = Integer.parseInt(args[j]);
            }
        }
        return matrix;
    }

    @Override
    public MazeSolution solveMaze(Maze maze) {
        return null;
    }
}
