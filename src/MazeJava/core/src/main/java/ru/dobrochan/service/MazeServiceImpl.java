package ru.dobrochan.service;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dobrochan.model.Maze;
import ru.dobrochan.model.MazeSolution;
import java.io.*;


@Service
public class MazeServiceImpl implements MazeService{

    private final MazeGenerator mazeGenerator;
    private Maze lastMaze;

    @Autowired
    public MazeServiceImpl(MazeGenerator mazeGenerator) {
        this.mazeGenerator = mazeGenerator;
    }


    @Override
    public Maze generateMaze(int width, int height) {
        lastMaze = mazeGenerator.generateMaze(width, height);
        return lastMaze;
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
        Maze maze = new Maze();

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
        lastMaze = maze;
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

    @Override
    public String getMazeString() {
        if (lastMaze == null) {
            return "";
        }
        else return lastMaze.toString();
    }
}
