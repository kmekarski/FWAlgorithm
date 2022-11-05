package org.example.model;

import java.util.*;


/**
 * FWmodel class represents a model from MVC design pattern.
 * All calculations required for program to work are made in this class.
 * @author SuperStudent
 * @version  version1
 */
public class FWmodel{

    public static void checkSize(int size) throws GraphSizeException {
        if(size<=1)
            throw new GraphSizeException("Graph Size needs to be more than 1.");
    }

    /**
     * List of integers that represent graph used in Floyd-Warshall algorithm.
     */

    ArrayList <ArrayList<Integer>> graph;

    /**
     * Public constructor that prints out a statement about instantation of class.
     */
    public FWmodel() {
        System.out.println("Model starts");
        graph = new ArrayList<>();
    }


    public void setGraph(ArrayList <ArrayList<Integer>> someGraph)
    {
        this.graph = new ArrayList<>();
        for(ArrayList<Integer> ar : someGraph)
        {
            ArrayList<Integer> toAdd = new ArrayList<>(ar);
            this.graph.add(toAdd);
        }

    }
    public void FloydWarshall()
    {
        int size = graph.size();
        for(int k=0; k<size; k++)
        {
            for(int i=0; i<size; i++)
            {
                for(int j=0; j<size; j++)
                {
                    if(graph.get(i).get(j) > graph.get(i).get(k) + graph.get(k).get(j))
                    {
                        graph.get(i).set(j,graph.get(i).get(k) + graph.get(k).get(j));
                    }
                }
            }
        }
    }
    public ArrayList <ArrayList<Integer>> getSolvedGraph()
    {
        return  graph;
    }
    public void showResult()
    {
        for(ArrayList<Integer> ar : graph)
        {
            for(Integer n : ar)
            {
                System.out.println(n);
            }
        }
    }
}
