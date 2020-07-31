import java.util.ArrayList;
import java.util.Stack;

public class EulerLoop {
    private Graph G;
    public EulerLoop(Graph G){
        this.G = G;
    }
    public boolean hasEulerLoop(){
        // 欧拉回路存在的前提是连通，首先判断连通性
        CC cc = new CC(G);
        if(cc.count() > 1) return false;
        for(int v = 0; v < G.V(); v ++)
            if(G.degree(v) % 2 == 1)
                return false;
        return true;
    }
    public ArrayList<Integer> result(){
        // 返回欧拉回路结果
        ArrayList<Integer> res = new ArrayList<>();// 充当Loop栈
        if(!hasEulerLoop()) return res;
        Graph g = (Graph) G.clone();// 用 G 的副本 g 寻找欧拉回路
        // 删除 g 的边不会影响 G
        Stack<Integer> stack = new Stack<>(); // curPath 栈
        int curv = 0;
        stack.push(curv);
        while (!stack.isEmpty()){
            if(g.degree(curv) != 0){
                // 度不为0说明当前顶点连的还有边，也就是还有路可走
                stack.push(curv);
                int w = g.adj(curv).iterator().next(); // 可迭代列表的第一个元素,即取g的任意邻点
                g.removeEdge(curv, w);
                curv = w;
            }else {
                // curv 到不了其它顶点，则已经找到一个环
                res.add(curv);
                curv = stack.pop();
            }
        }
        return res;
    }
    public static void main(String args[]){
        Graph g = new Graph("g.txt");
        EulerLoop el = new EulerLoop(g);
        System.out.println(el.result());
    }
}
