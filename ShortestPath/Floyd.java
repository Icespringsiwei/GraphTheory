import java.util.Arrays;

public class Floyd {
    private WeightedGraph G;
    private int[][] dis;
    private boolean hasNegativeCycle = false;
    public Floyd(WeightedGraph G){
        this.G = G;
        dis = new int[G.V()][G.V()];
        for(int i = 0; i < G.V(); i ++)
            Arrays.fill(dis[i], 0x3f3f3f3f);
        for(int v = 0; v < G.V(); v ++){
            dis[v][v] = 0;
            for(int w: G.adj(v)){
                dis[v][w] = G.getWeight(v, w);
            }
        }

        for(int t = 0; t < G.V(); t ++)
            for(int v = 0; v < G.V(); v ++)
                for(int w = 0; w < G.V(); w ++)
                    if(dis[v][t] != 0x3f3f3f3f && dis[t][w] != 0x3f3f3f3f
                            && dis[v][t] + dis[t][w] < dis[v][w])
                        dis[v][w] = dis[v][t] + dis[t][w];

        for(int v = 0; v < G.V(); v ++)
            if(dis[v][v] < 0)
                hasNegativeCycle = true;

    }
    public boolean hasNegCycle(){
        return hasNegativeCycle;
    }
    public boolean isConnectedTo(int v, int w){
        G.validateVertex(v);
        G.validateVertex(w);
        return dis[v][w] != 0x3f3f3f3f;
    }
    public int disTo(int v, int w){
        if(isConnectedTo(v, w))
            return dis[v][w];
        throw new RuntimeException("v-w is not connected!");
    }
    public static void main(String args[]){
        WeightedGraph g = new WeightedGraph("g.txt");
        Floyd f = new Floyd(g);
        if(!f.hasNegativeCycle){
            for(int v = 0; v < g.V(); v ++) {
                for (int w = 0; w < g.V(); w++)
                    System.out.print(f.disTo(v, w) + " ");
                System.out.println();
            }
        }

    }
}
