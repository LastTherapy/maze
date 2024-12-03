package ru.dobrochan.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.dobrochan.utils.Printable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class AbstractMaze implements Printable {
    protected int width;
    protected int height;
    protected int[][] matrixA;
    protected int[][] matrixB;

}
