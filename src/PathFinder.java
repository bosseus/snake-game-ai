import java.util.*;
import java.awt.Point;

public class PathFinder {
    private static final int MOVE_COST = 10;
    private final int GRID_SIZE;
    private Node[][] grid;
    private PriorityQueue<Node> openList;
    private boolean[][] closedList;
    private Point goal;

    public PathFinder(int gridSize) {
        this.GRID_SIZE = gridSize;
        this.grid = new Node[GRID_SIZE][GRID_SIZE];
        this.openList = new PriorityQueue<>();
        this.closedList = new boolean[GRID_SIZE][GRID_SIZE];
    }

    public List<Point> findPath(Point start, Point goal, List<Point> snakeBody) {
        resetPathFinder();
        this.goal = goal;

        // Initialize grid
        for (int x = 0; x < GRID_SIZE; x++) {
            for (int y = 0; y < GRID_SIZE; y++) {
                grid[x][y] = new Node(new Point(x, y));
            }
        }

        // Mark snake body as obstacles
        for (Point p : snakeBody) {
            if (p.x >= 0 && p.x < GRID_SIZE && p.y >= 0 && p.y < GRID_SIZE) {
                grid[p.x][p.y].isObstacle = true;
            }
        }

        // Start A* algorithm
        Node startNode = grid[start.x][start.y];
        startNode.g = 0;
        startNode.h = calculateHeuristic(start);
        startNode.f = startNode.g + startNode.h;

        openList.add(startNode);

        while (!openList.isEmpty()) {
            Node current = openList.poll();
            Point currentPoint = current.position;

            if (currentPoint.equals(goal)) {
                return reconstructPath(current);
            }

            closedList[currentPoint.x][currentPoint.y] = true;

            // Check all adjacent nodes
            for (Point neighbor : getValidNeighbors(currentPoint)) {
                if (closedList[neighbor.x][neighbor.y]) continue;

                Node neighborNode = grid[neighbor.x][neighbor.y];
                if (neighborNode.isObstacle) continue;

                int tentativeG = current.g + MOVE_COST;

                if (!openList.contains(neighborNode)) {
                    openList.add(neighborNode);
                } else if (tentativeG >= neighborNode.g) {
                    continue;
                }

                neighborNode.parent = current;
                neighborNode.g = tentativeG;
                neighborNode.h = calculateHeuristic(neighbor);
                neighborNode.f = neighborNode.g + neighborNode.h;
            }
        }

        return new ArrayList<>(); // No path found
    }

    private int calculateHeuristic(Point point) {
        return Math.abs(point.x - goal.x) + Math.abs(point.y - goal.y) * MOVE_COST;
    }

    private List<Point> getValidNeighbors(Point point) {
        List<Point> neighbors = new ArrayList<>();
        int[][] directions = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // Up, Right, Down, Left

        for (int[] dir : directions) {
            int newX = point.x + dir[0];
            int newY = point.y + dir[1];

            if (newX >= 0 && newX < GRID_SIZE && newY >= 0 && newY < GRID_SIZE) {
                neighbors.add(new Point(newX, newY));
            }
        }

        return neighbors;
    }

    private List<Point> reconstructPath(Node endNode) {
        List<Point> path = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            path.add(0, current.position);
            current = current.parent;
        }

        return path;
    }

    private void resetPathFinder() {
        openList.clear();
        closedList = new boolean[GRID_SIZE][GRID_SIZE];
    }

    private class Node implements Comparable<Node> {
        Point position;
        Node parent;
        int g; // Cost from start to current node
        int h; // Estimated cost from current node to goal
        int f; // Total cost (g + h)
        boolean isObstacle;

        Node(Point position) {
            this.position = position;
            this.g = Integer.MAX_VALUE;
            this.h = 0;
            this.f = Integer.MAX_VALUE;
            this.isObstacle = false;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.f, other.f);
        }
    }
} 