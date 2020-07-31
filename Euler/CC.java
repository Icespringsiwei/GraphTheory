import java.util.ArrayList;
// 无向无权图
public class CC {

    private Graph G;
    private int[] visited; // 标记顶点是否被访问以及属于哪个连通分量
    private int cccount = 0; // 求连通分量个数

    public CC(Graph G){
        this.G = G;
        visited = new int[G.V()];
        for(int i = 0; i < visited.length; i ++)
            visited[i] = -1;
        for(int v = 0; v < G.V(); v ++)
            if(visited[v] == -1) {
                dfs(v, cccount);
                cccount ++; // dfs(v, cccount ++);
            }
    }

    private void dfs(int v, int ccid){// ConnectedComponent ID
        visited[v] = ccid;
        for(int w: G.adj(v))
            if(visited[w] == -1)
                dfs(w, ccid);
    }

    public int count(){
        return cccount;
    }

    public ArrayList<Integer> getCC(){// 查看连通分量标记

        ArrayList<Integer> cc = new ArrayList<>();
        for(int i = 0; i < visited.length; i ++)
            cc.add(visited[i]);
        return cc;
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
    public boolean isConnected(int v, int w){
        // 判断两个顶点是否连接
        G.validateVertex(v);
        G.validateVertex(w);
        return visited[v] == visited[w];
    }
    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        CC cc = new CC(g);
        System.out.println(cc.count());
        System.out.println(cc.getCC());
        ArrayList<Integer>[] comp = cc.components();
        for(int ccid = 0; ccid < comp.length; ccid ++){
            System.out.print(ccid + ": ");
            for(int w: comp[ccid])
                System.out.print(w + " ");
            System.out.println();
        }
    }

}
