package ru.dobrochan.service;

import org.springframework.stereotype.Component;
import ru.dobrochan.model.Maze;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class EilerGenerator implements MazeGenerator {
    Map<Integer, Set<Integer>> mazeSet;

    public EilerGenerator() {
        mazeSet = new HashMap<>();
    }

    @Override
    public Maze generateMaze(int width, int height) {
        Maze maze = new Maze();
        mazeSet.clear();
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
                else {
                    mergeRight(j, line);
                }
            }
            // ставим сплошную стенку справа по всем строчкам
            matrixA[i][width - 1] = 1;

            // 4. решаем ставить стенку снизу или нет. Не имеет смысла для последней строчки.
            if (i < height - 1) {
                for (int j = 0; j < width; j++) {
//            4.2 Стенку решили ставить, стенку будем ставить, если множество,
//            которому принадлежит текущая ячейка имеет более одной ячейки без нижней границы
                    if (getWallDecision()) {
                        Set<Integer> set = mazeSet.get(line[j]);
                        if (set.size() > 1) {
                            matrixB[i][j] = 1;
                            // сразу же подготавливаем строку для следующего шага
                            set.remove(j);
                            // создаем новое множество там где были нижние ячейки
                            line[j] = lastSet;
                            mazeSet.computeIfAbsent(lastSet, k -> new HashSet<>()).add(j);
                            lastSet++;
                        }
                    }
                }
//                5.1   Если решили сгенерировать ещë одну строку,
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
            // last line
            else {
                for (int j = 0; j < width; j++) {
// 5.2 Если решили что, текущая строка последняя, то каждой ячейке присвойте нижнюю стенку и:
                    matrixB[i][j] = 1;
//                Двигаясь слева направо, если множество текущей клетки
//                и клетки справа не совпадает уберите между ними стенку (для того чтобы убрать замкнутые участки).
//                Объедините множества текущей ячейки и ячейки справа.
                    if (j < width - 1 && line[j] != line[j + 1]) {
                        matrixA[i][j] = 0;
                        mergeRight(j, line);
                    }
                }
                System.out.println();
            }
        }
        maze.setWidth(width);
        maze.setHeight(height);
        maze.setMatrixA(matrixA);
        maze.setMatrixB(matrixB);
        return maze;
    }

    private void mergeRight(int leftCell, int[] line) {
        int oldSet = line[leftCell + 1];
        Set<Integer> rightSet = mazeSet.get(oldSet);
        for (Integer integer : rightSet) {
            line[integer] = line[leftCell];
        }
        mazeSet.get(line[leftCell]).addAll(rightSet);
        mazeSet.remove(oldSet);
    }

    private boolean getWallDecision() {
        return Math.random() > 0.5;
    }
}
