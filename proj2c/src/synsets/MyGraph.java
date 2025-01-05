package synsets;

import edu.princeton.cs.algs4.Stack;
import java.util.*;

public class MyGraph<T> {
    public Map<T, List<T>> adjList;
    public MyGraph() {
        adjList = new HashMap<>();
    }
    public void addVertex(T vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
    }
    public void addEdge(T start, T end) {
        if (!adjList.containsKey(start)) {
            addVertex(start);
        }
        adjList.get(start).add(end);
    }
    public List<T> getNeighbors(T vertex) {
        return adjList.get(vertex);
    }
//    public Set<T> depthFirstTraversal(T root) {
//        Set<T> visited = new HashSet<>();
//        Stack<T> stack = new Stack<>();
//        stack.push(root);
//        while (!stack.isEmpty()) {
//            T vertex = stack.pop();
//            if (!visited.contains(vertex)) {
//                visited.add(vertex);
//                if (this.getNeighbors(vertex) != null) {
//                    for (T i : this.getNeighbors(vertex)) {
//                        stack.push(i);
//                    }
//                }
//            }
//        }
//        return visited;
//    }
    public Set<Integer> depthFirstTraversal(int id) {
        Set<Integer> hyps = new HashSet<>();
        findDescendantsHelper(id, hyps);
        hyps.add(id);
        return hyps;
    }
    public void findDescendantsHelper(int id, Set<Integer> set) {
        if (!adjList.containsKey(id)) {
            set.add(id);
        } else {
            for (T i : adjList.get(id)) {
                findDescendantsHelper((Integer) i, set);
                set.add((Integer) i);
            }
        }
    }
}
