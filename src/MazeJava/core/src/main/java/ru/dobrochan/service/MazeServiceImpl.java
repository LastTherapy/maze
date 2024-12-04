package ru.dobrochan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dobrochan.config.PrintSymbolConfig;
import ru.dobrochan.model.Maze;
import ru.dobrochan.model.MazeSolution;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

        Map<Integer, Set<Integer>> mazeSet = new HashMap<>();
        int lastSet = 1;

        int[][] matrixA = new int[height][width];
        int[][] matrixB = new int[height][width];

        // 1. создаем пустую строку
        int[] line = new int[width];
        // 2. каждую ячейку присваиваем своему множеству
        for (int i = 0; i < width; i++) {
            mazeSet.computeIfAbsent(lastSet, k -> new HashSet<>()).add(i);
            line[i] = lastSet;
            lastSet++;
        }

        for (int i = 0; i < height; i++) {
            // 3. решаем ставить стенку српава или нет
            for (int j = 0; j < width - 1; j++) {
//                    3.1 Стенку решили ставить, просто ставим и идем дальше.
                    if (getWallDecision()) {
                        matrixA[i][j] = 1;
                    }
//            3.2 Стенку решили не ставить, но если текущая ячейка и ячейка справа принадлежат одному множеству,
//            то между ними всегда нужно ставить стенку, чтобы предотвратить зацикливание участков.
                    else if (line[j] == line[j + 1]) {
                        matrixA[i][j] = 1;
                    }
//            3.3 Стенку решили не ставить и условие в пункте 2 не выполнилось.
//            В данном случае нужно объединить множество к которому относится ячейка справа с множеством текущей ячейки.
                    else  {
                        int oldSet = line[j + 1];
                        // get set of right cell
                        Set<Integer> rightSet = mazeSet.get(oldSet);
                        // change number for all set in cells
                        for (Integer integer : rightSet) {
                            line[integer] = line[j];
                        }
                        mazeSet.get(line[j]).addAll(rightSet);
                        mazeSet.remove(oldSet);
                    }

            }
            // 4. решаем ставить стенку снизу или нет
            for (int j = 0; j < width; j++) {
//            4.2 Стенку решили ставить, стенку будем ставить, если множество,
//            которому принадлежит текущая ячейка имеет более одной ячейки без нижней границы
                if (getWallDecision()) {
                    Set<Integer> set = mazeSet.get(line[j]);
                    if (set.size() > 1) {
                        matrixB[i][j] = 1;
                    }
                    set.remove(j);
                    // сразу же подготавливаем строку для следующего шага
                    // создаем новое множество там где были ниние ячейки
                    line[j] = lastSet;
                    mazeSet.computeIfAbsent(lastSet, k -> new HashSet<>()).add(j);
                    lastSet++;
                }
            }
            // final line
            if (i == height - 1) {
                for (int j = 0; j < width; j++) {
                    matrixB[i][j] = 1;
                    if (j < width - 1 && line[j] != line[j + 1]) {
                        matrixA[i][j] = 0;
                    }
                }
            }
            else {
//              5.1   Если решили сгенерировать ещë одну строку,
//                то нужно скопировать текущую строку, удалить из нее все правые стенки,
//                и там где были нижние стенки ячейкам присвоить пустые множества, удалить все нижние границы
                for (int j = 0; j < width; j++) {
                    if (matrixB[i][j] == 1) {
                        line[j] = lastSet;
                        mazeSet.computeIfAbsent(lastSet, k -> new HashSet<>()).add(j);
                        lastSet++;
                    }
                }

            }
        }
        maze.setWidth(width);
        maze.setHeight(height);
        maze.setMatrixA(matrixA);
        maze.setMatrixB(matrixB);
        maze.print();
        return maze;
    }

    private boolean getWallDecision() {
        return Math.random() > 0.5;
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
