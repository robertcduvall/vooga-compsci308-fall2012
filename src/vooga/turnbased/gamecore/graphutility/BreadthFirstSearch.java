package vooga.turnbased.gamecore.graphutility;

import java.awt.Dimension;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import vooga.turnbased.gamecore.gamemodes.MapMode;


/**
 * No heuristic methods unfortunately ...
 * But given the size of the map, most often path finding can be done within 0.1
 * seconds
 * 
 * @author Rex
 * 
 */
public class BreadthFirstSearch extends PathSearch {

    /**
     * constructor of the BFS
     * 
     * @param start Starting point
     * @param end Ending point
     * @param size Size of the table
     */
    public BreadthFirstSearch (Point start, Point end, Dimension size) {
        super(start, end, size);
    }

    /**
     * find path using breadth fist search
     * 
     * @return If a path could be found
     * @param start The point where the search begins from
     */
    public boolean findPath (Point start) {
        Queue<Node> bfsQueue = new LinkedList<Node>();
        bfsQueue.add(new Node(start));
        while (bfsQueue.size() != 0) {
            Node currentNode = bfsQueue.poll();
            if (checkVisited(currentNode.getLocation().x, currentNode.getLocation().y)) {
                // already visited
                continue;
            }
            // path found
            if (currentNode.getLocation().equals(getEnd())) {
                determinePath(currentNode);
                return true;
            }
            bfsQueue.addAll(currentNode.obtainAdjacentNodes());
        }
        return false;
    }

    /**
     * determine and set the path so that getPath() could be called
     * 
     * @param node Node representing the end point
     */
    private void determinePath (Node node) {
        List<Point> path = new ArrayList<Point>();
        Node myNode = node;
        while (myNode.getPreviousNodeInPath() != null) {
            path.add(myNode.getLocation());
            myNode = myNode.getPreviousNodeInPath();
        }
        Collections.reverse(path);
        setPath(path);
    }

    /**
     * Inner class that represents a bfs search state
     * It contains the location, as well as the previous node
     * 
     * @author Rex, Vo
     * 
     */
    final class Node {
        private Point myLocation;
        private Node myPreviousNodeInPath;

        /**
         * constructor
         * 
         * @param p location of the node
         */
        public Node (Point p) {
            myLocation = p;
            myPreviousNodeInPath = null;
        }

        /**
         * get the previous node in the path
         * 
         * @return previous node
         */
        public Node getPreviousNodeInPath () {
            return myPreviousNodeInPath;
        }

        /**
         * set the previous node
         * 
         * @param n The previous node
         */
        public void setPreviousNodeInPath (Node n) {
            myPreviousNodeInPath = n;
        }

        /**
         * obtain a list of nodes that the current nodes can reach
         * The list of nodes all have this node as the previous node in path
         * 
         * @return list of nodes that the current nodes can reach
         */
        public List<Node> obtainAdjacentNodes () {
            List<Node> adj = new ArrayList<Node>();
            if (validateMove(myLocation, MapMode.DOWN)) {
                adj.add(new Node(translatePoint(myLocation, MapMode.DOWN)));
            }
            if (validateMove(myLocation, MapMode.LEFT)) {
                adj.add(new Node(translatePoint(myLocation, MapMode.LEFT)));
            }
            if (validateMove(myLocation, MapMode.UP)) {
                adj.add(new Node(translatePoint(myLocation, MapMode.UP)));
            }
            if (validateMove(myLocation, MapMode.RIGHT)) {
                adj.add(new Node(translatePoint(myLocation, MapMode.RIGHT)));
            }
            for (Node node : adj) {
                node.setPreviousNodeInPath(this);
            }
            return adj;
        }

        /**
         * get location of the node
         * 
         * @return location
         */
        public Point getLocation () {
            return myLocation;
        }
    }

}
