package algorithm;
import model.*;
import java.util.*;

public class Prim
{
    public static List<Edge> mst(Graph g)
    {
        boolean[] vis=new boolean[g.V];
        List<Edge> res=new ArrayList<>();
        PriorityQueue<Edge> pq=new PriorityQueue<>(Comparator.comparingInt(e->e.weight));
        vis[0]=true;

        for(Edge e:g.edges)
        {
            if(e.from==0) pq.add(e);
        }

        while(!pq.isEmpty())
        {
            Edge e=pq.poll();
            if(!vis[e.to])
            {
                vis[e.to]=true; res.add(e);
                for(Edge ne:g.edges)
                    if(ne.from==e.to && !vis[ne.to]) pq.add(ne);
            }
        }
        return res;
    }
}
