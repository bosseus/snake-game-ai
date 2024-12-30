import java.awt.Point;
import java.util.List;

public class SnakeAI {
    private final PathFinder pathFinder;
    private final int GRID_SIZE;
    private List<Point> currentPath;
    private Point nextMove;

    public SnakeAI(int gridSize) {
        this.GRID_SIZE = gridSize;
        this.pathFinder = new PathFinder(gridSize);
    }

    public Point getNextMove(Point snakeHead, Point food, List<Point> snakeBody) {
        // If we don't have a path or need to recalculate
        if (currentPath == null || currentPath.isEmpty() || needToRecalculatePath(snakeHead)) {
            currentPath = pathFinder.findPath(snakeHead, food, snakeBody);
            
            // If no path to food is found, try survival mode
            if (currentPath.isEmpty()) {
                return findSafeMove(snakeHead, snakeBody);
            }
            
            // Remove the current position from the path
            if (!currentPath.isEmpty() && currentPath.get(0).equals(snakeHead)) {
                currentPath.remove(0);
            }
        }

        // Get the next move from the path
        if (!currentPath.isEmpty()) {
            nextMove = currentPath.get(0);
            currentPath.remove(0);
            return nextMove;
        }

        // If no path is found, try to find a safe move
        return findSafeMove(snakeHead, snakeBody);
    }

    private boolean needToRecalculatePath(Point snakeHead) {
        return nextMove != null && !snakeHead.equals(nextMove);
    }

    private Point findSafeMove(Point snakeHead, List<Point> snakeBody) {
        // Try all possible moves
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Up, Right, Down, Left
        
        for (int[] dir : directions) {
            Point newPos = new Point(snakeHead.x + dir[0], snakeHead.y + dir[1]);
            
            // Check if the move is safe
            if (isSafeMove(newPos, snakeBody)) {
                return newPos;
            }
        }
        
        // If no safe move is found, return the current position (game over)
        return snakeHead;
    }

    private boolean isSafeMove(Point pos, List<Point> snakeBody) {
        // Check boundaries
        if (pos.x < 0 || pos.x >= GRID_SIZE || pos.y < 0 || pos.y >= GRID_SIZE) {
            return false;
        }
        
        // Check collision with snake body
        for (Point bodyPart : snakeBody) {
            if (pos.equals(bodyPart)) {
                return false;
            }
        }
        
        return true;
    }
} 