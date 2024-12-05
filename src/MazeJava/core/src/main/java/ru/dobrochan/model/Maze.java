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
}
