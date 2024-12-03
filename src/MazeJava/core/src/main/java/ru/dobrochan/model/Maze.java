package ru.dobrochan.model;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dobrochan.config.PrintSymbolConfig;

@Component
public class Maze extends AbstractMaze {
    private final PrintSymbolConfig printSymbolConfig;

    @Autowired
    public Maze(PrintSymbolConfig printSymbolConfig) {
        this.printSymbolConfig = printSymbolConfig;
    }


    @Override
    public void print() {
        // top line.
        System.out.print(" ");
        for (int i = 0; i < width; i++) {
            System.out.print(printSymbolConfig.getBottom());
        }
        System.out.println(" ");
        // center
        for (int i = 0; i < height; i++) {
            // left wall anyway
            System.out.print(printSymbolConfig.getLeftWall());
            for (int j = 0; j < width; j++) {

                if (matrixA[i][j] == 1 && matrixB[i][j] == 1) {
                    if (j < width - 1) {
                        System.out.print(printSymbolConfig.getBottomRight());
                    }
                    // we always print right wall so need to change in this case
                    else {
                        System.out.print(printSymbolConfig.getBottom());
                    }
                }
                // bot line wall without right wall
                else if (matrixB[i][j] == 1) {
                    System.out.print(printSymbolConfig.getBottom());
                }
                // right wall without bot wall
                else if (matrixA[i][j] == 1) {
                    if (j < width - 1) {
                        System.out.print(printSymbolConfig.getRightCell());
                    }
                    // we always print right wall so need to skip in this case
                    else {
                        System.out.print(printSymbolConfig.getEmptyCell());
                    }
                }
                else {
                    System.out.print(printSymbolConfig.getEmptyCell());
                }
            }
            System.out.println(printSymbolConfig.getRightWall());
        }
    }
}
