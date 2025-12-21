package algorithm;
import model.*;
import java.util.*;

public class Kruskal
{
    static int find(int[] p,int i)
    {
        return p[i]==i?i:(p[i]=find(p,p[i]));
    }
    public static List<Edge> mst(Graph g)
    {
        List<Edge> res=new ArrayList<>();
        int[] p=new int[g.V];
        for(int i=0;i<g.V;i++) p[i]=i;
        g.edges.sort(Comparator.comparingInt(e->e.weight));

        for(Edge e:g.edges)
        {
            int x=find(p,e.from), y=find(p,e.to);
            if(x!=y){ res.add(e); p[x]=y; }
        }
        return res;
    }
}
