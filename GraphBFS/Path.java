import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class Path {
    private Graph G;
    private int s, t;
    private boolean[] visited;
    private int[] pre;

    public Path(Graph G, int s, int t){

        G.validateVertex(s);
        G.validateVertex(t);

        this.G = G;
        this.s = s;
        this.t = t;

        visited = new boolean[G.V()];
        pre = new int[G.V()];
        for(int i = 0; i < G.V(); i ++)
            pre[i] = -1;

        bfs();
    }

    private void bfs(){
        // BFS 寻找路径 s -> t
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = true;
        pre[s] = s;

        if(s == t) return;
        while(!queue.isEmpty()) {
            int v = queue.remove(); // 取队头

            for (int w : G.adj(v))
                if (!visited[w]) {
                    queue.add(w);
                    visited[w] = true;
                    pre[w] = v;
                    if(w == t) return; // 已找到路径，提前返回
                }
        }
    }

    public boolean isConnected(){
        return visited[t];
    }

    public Iterable<Integer> path(){

        ArrayList<Integer> res = new ArrayList<>();
        if(!isConnected()) return res;
        int cur = t;
        while(cur != s){
            res.add(cur);
            cur = pre[cur];
        }
        res.add(s);
        Collections.reverse(res);
        return res;
    }

    public static void main(String[] args){
        Graph g = new Graph("g.txt");
        Path path = new Path(g,0,6);
        System.out.println(path.path());
    }
}
