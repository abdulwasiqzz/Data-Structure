package algorithm;
import model.*;
import java.util.*;

public class Dijkstra
{
    public static List<Edge> path(Graph g, int s, int d)
    {
        int[] dist = new int[g.V];
        int[] par = new int[g.V];

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(par, -1);

        dist[s] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{s, 0});

        while (!pq.isEmpty())
        {
            int[] c = pq.poll();
            int u = c[0];

            if (c[1] > dist[u]) {
                continue;
            }

            for (Edge e : g.edges)
            {
                if (e.from == u && dist[e.to] > dist[u] + e.weight)
                {
                    dist[e.to] = dist[u] + e.weight;
                    par[e.to] = u;
                    pq.add(new int[]{e.to, dist[e.to]});
                }
            }
        }
        List<Edge> path = new ArrayList<>();
        for (int v = d; par[v] != -1; v = par[v])
        {
            int from = par[v];
            int to = v;

            int weight = 0;
            for (Edge e : g.edges)
            {
                if (e.from == from && e.to == to)
                {
                    weight = e.weight;
                    break;
                }
            }

            path.add(new Edge(from, to, weight));
        }

        Collections.reverse(path);

        return path;
    }
}