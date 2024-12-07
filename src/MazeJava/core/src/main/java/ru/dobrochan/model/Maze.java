package ru.dobrochan.model;


import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@Data
@NoArgsConstructor
public class Maze {
    private int width;
    private int height;
    private int[][] matrixA;
    private int[][] matrixB;

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(width).append(" ").append(height).append("\n");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(matrixA[i][j]).append(" ");
            }
            sb.append("\n");
        }
        sb.append("\n");
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                sb.append(matrixB[i][j]).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
