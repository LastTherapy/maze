package ru.dobrochan.view;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.dobrochan.config.PrintSymbolConfig;
import ru.dobrochan.model.Maze;

@Component
public class ConsoleView {

    private final PrintSymbolConfig printSymbolConfig;

    @Autowired
    public ConsoleView(PrintSymbolConfig printSymbolConfig) {
        this.printSymbolConfig = printSymbolConfig;
    }

    public void print (Maze maze) {
            // top line.
            System.out.print(" ");
            for (int i = 0; i < maze.getWidth(); i++) {
                System.out.print(printSymbolConfig.getBottom());
            }
            System.out.println(" ");
            // center
            for (int i = 0; i < maze.getHeight(); i++) {
                // left wall anyway
                System.out.print(printSymbolConfig.getLeftWall());
                for (int j = 0; j < maze.getWidth(); j++) {

                    if (maze.getMatrixA()[i][j] == 1 && maze.getMatrixB()[i][j] == 1) {
                        if (j < maze.getWidth() - 1) {
                            System.out.print(printSymbolConfig.getBottomRight());
                        }
                        // we always print right wall so need to change in this case
                        else {
                            System.out.print(printSymbolConfig.getBottom());
                        }
                    }
                    // bot line wall without right wall
                    else if (maze.getMatrixB()[i][j] == 1) {
                        System.out.print(printSymbolConfig.getBottom());
                    }
                    // right wall without bot wall
                    else if (maze.getMatrixA()[i][j] == 1) {
                        if (j < maze.getWidth() - 1) {
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
