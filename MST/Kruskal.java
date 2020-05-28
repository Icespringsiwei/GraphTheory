import java.util.ArrayList;
import java.util.Collections;

public class Kruskal {
    // 最小生成树的结果用 ArrayList 保存相应的 n-1 条边
    private WeightedGraph G;
    private ArrayList<WeightedEdge> mst;
    public Kruskal(WeightedGraph G){
        this.G = G;
        mst = new ArrayList<>();

        CC cc = new CC(G);
        if(cc.count() > 1)return;
        ArrayList<WeightedEdge> edges = new ArrayList<>();
        for(int v = 0; v < G.V(); v ++)
            for(int w: G.adj(v))
                if(v < w)// 避免 2-0, 0-2 这样的重复存储
                    edges.add(new WeightedEdge(v, w, G.getWeight(v, w)));
        Collections.sort(edges);

        UnionFind uf = new UnionFind(G.V());
        for(WeightedEdge edge: edges){
            int v = edge.getV();
            int w = edge.getW();
            if(!uf.isConnected(v, w)){
                mst.add(edge);
                uf.unionElements(v, w);
            }
        }
    }
    public ArrayList<WeightedEdge> result(){
        return mst;
    }
    public static void main(String args[]){
        WeightedGraph g = new WeightedGraph("g.txt");
        Kruskal kl = new Kruskal(g);
        System.out.println(kl.result());
    }
}
