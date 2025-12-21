
package model;
import java.util.*;
public class Graph {
    public int V;
    public boolean directed;
    public ArrayList<Edge> edges=new ArrayList<>();

    public Graph(int v,boolean d)
    {
        V=v;
        directed=d;
    }
    public void addEdge(int f,int t,int w)
    {
        edges.add(new Edge(f,t,w));
        if(!directed)
        {
            edges.add(new Edge(t,f,w));
        }
    }
}
