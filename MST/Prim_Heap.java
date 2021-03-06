import java.util.ArrayList;
import java.util.Collections;
import java.util.PriorityQueue;
import java.util.Queue;

public class Prim_Heap {

    // 最小生成树的结果用 ArrayList 保存
    private WeightedGraph G;
    private ArrayList<WeightedEdge> mst;

    public Prim_Heap(WeightedGraph G) {
        this.G = G;
        mst = new ArrayList<>();

        CC cc = new CC(G);
        if (cc.count() > 1) return;
        ArrayList<WeightedEdge> edges = new ArrayList<>();
        boolean visited[] = new boolean[G.V()];
        visited[0] = true;// 初始

        Queue<WeightedEdge> pq = new PriorityQueue<>();
        for(int w: G.adj(0))
            pq.add(new WeightedEdge(0, w, G.getWeight(0, w)));
        while(!pq.isEmpty()){
            WeightedEdge minEdge = pq.remove();
            if(visited[minEdge.getV()] && visited[minEdge.getW()])
                continue;
            mst.add(minEdge);

            int newv = visited[minEdge.getV()] == true ? minEdge.getW(): minEdge.getV();
            visited[newv] = true;// 拓展切分
            for(int w: G.adj(newv))
                if(!visited[w])
                    pq.add(new WeightedEdge(newv, w, G.getWeight(newv, w)));
        }
    }

    public ArrayList<WeightedEdge> result() {
        return mst;
    }

    public static void main(String args[]) {
        WeightedGraph g = new WeightedGraph("g.txt");
        Prim_Heap prim = new Prim_Heap(g);
        System.out.println(prim.result());
    }
}


