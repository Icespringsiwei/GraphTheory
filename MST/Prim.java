import java.util.ArrayList;
import java.util.Collections;

public class Prim {
    // 最小生成树的结果用 ArrayList 保存
    private WeightedGraph G;
    private ArrayList<WeightedEdge> mst;

    public Prim(WeightedGraph G){
        this.G = G;
        mst = new ArrayList<>();

        CC cc = new CC(G);
        if(cc.count() > 1)return;
        ArrayList<WeightedEdge> edges = new ArrayList<>();
        boolean visited[] = new boolean[G.V()];
        visited[0] = true;// 初始
        for(int i = 1; i < G.V(); i ++){
            WeightedEdge minEdge = new WeightedEdge(-1, -1, 0x3f3f3f3f);
            for(int v = 0; v < G.V(); v ++)
                if(visited[v])
                    for(int w: G.adj(v))
                        if(!visited[w] && G.getWeight(v, w) < minEdge.getWeight())
                            minEdge = new WeightedEdge(v, w, G.getWeight(v, w));
            mst.add(minEdge);
            visited[minEdge.getV()] = true; // 扩充切分
            visited[minEdge.getW()] = true;
        }
    }
    public ArrayList<WeightedEdge> result(){
        return mst;
    }
    public static void main(String args[]){
        WeightedGraph g = new WeightedGraph("g.txt");
        Prim prim = new Prim(g);
        System.out.println(prim.result());
    }
}
