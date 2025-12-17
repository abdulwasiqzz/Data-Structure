package algorithm;
import model.*;
import java.util.*;

public class BFS {
    public static List<Edge> traverse(Graph g,int s)
    {
        boolean[] vis=new boolean[g.V];
        List<Edge> steps=new ArrayList<>();
        Queue<Integer> q=new LinkedList<>();
        q.add(s);
        vis[s]=true;
        while(!q.isEmpty())
        {
            int u=q.poll();
            for(Edge e:g.edges)
            {
                if(e.from==u && !vis[e.to])
                {
                    vis[e.to]=true;
                    steps.add(e);
                    q.add(e.to);
                }
            }
        }
        return steps;
    }
}
