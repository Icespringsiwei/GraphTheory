import java.util.ArrayList;

// 无向无权图
public class GraphDFS {

    private Graph G;
    private boolean[] visited;
    private ArrayList<Integer> pre = new ArrayList<>(); // 深度优先前序遍历
    private ArrayList<Integer> post = new ArrayList<>(); // 深度优先后序遍历

    public GraphDFS(Graph G){
        this.G = G;
        visited = new boolean[G.V()];
        for(int v = 0; v < G.V(); v ++)
            if(!visited[v])
                dfs(v);
    }

    public Iterable<Integer> pre() { // ArrayList
        // 返回深度优先前序遍历的结果
        return pre;
    }
    public Iterable<Integer> post() { // ArrayList
        // 返回深度优先后序遍历的结果
        return post;
    }

    private void dfs(int v){
        visited[v] = true;
        pre.add(v);
        for(int w: G.adj(v))
            if(!visited[w])
                dfs(w);
        post.add(v);
    }

    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        GraphDFS graphDFS = new GraphDFS(g);
        System.out.println(graphDFS.post());
    }

}
