package algorithm;
import model.*;
import java.util.*;

public class DFS
{
    static void dfs(Graph g,int u,boolean[] vis,List<Edge> steps)
    {
        vis[u]=true;
        for(Edge e:g.edges)
        {
            if(e.from==u && !vis[e.to])
            {
                steps.add(e);
                dfs(g,e.to,vis,steps);
            }
        }
    }
    public static List<Edge> traverse(Graph g,int s)
    {
        boolean[] vis=new boolean[g.V];
        List<Edge> steps=new ArrayList<>();
        dfs(g,s,vis,steps);
        return steps;
    }
}
