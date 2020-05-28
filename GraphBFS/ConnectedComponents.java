import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class ConnectedComponents {
    private Graph G;
    private int[] visited;
    private int cccount = 0;

    public ConnectedComponents(Graph G){
        this.G = G;
        visited = new int[G.V()];
        for(int i = 0; i < visited.length; i ++)
            visited[i] = -1;

        for(int v = 0; v < G.V(); v ++)
            if(visited[v] == -1) {
                bfs(v, cccount);
                cccount ++;
            }
    }
    private void bfs(int s, int ccid){
        Queue<Integer> queue = new LinkedList<>();
        queue.add(s);
        visited[s] = ccid;

        while(!queue.isEmpty()){
            int v = queue.remove();

            for(int w: G.adj(v))
                if(visited[w] == -1){
                    queue.add(w);
                    visited[w] = ccid;
                }
        }
    }

    public int count(){ // 返回连通分量个数
        return cccount;
    }

    public boolean isConnected(int v, int w){
        return visited[v] == visited[w];
    }

    public ArrayList<Integer>[] components(){
        // 返回各个连通分量
        ArrayList<Integer>[] res = new ArrayList[cccount];
        for(int i = 0; i < cccount; i ++)
            res[i] = new ArrayList<>();
        for(int v = 0; v < G.V(); v ++)
            res[visited[v]].add(v);
        return res;
    }
    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        ConnectedComponents cc = new ConnectedComponents(g);
        ArrayList<Integer>[] comp = cc.components();
        for(int ccid = 0; ccid < comp.length; ccid ++) {
            System.out.print(ccid + ": ");
            for (int w : comp[ccid])
                System.out.print(w + " ");
            System.out.println();
        }
    }
}
