public class UnionFind {

    private int[] parent;
    private int[] sz; // sz[i]表示以 i 为根的集合中元素个数

    public UnionFind(int n) {
        parent = new int[n];
        sz = new int[n];

        for (int i = 0; i < n; i++) {
            parent[i] = i; // 初始每个点都自成一个集合
            sz[i] = 1;
        }
    }
    public void unionElements(int p, int q){
        // 将两个元素合并到一个集合, p 连到 q 上，也可以 q 连 p
        int pRoot = find(p);
        int qRoot = find(q);
        if(pRoot == qRoot) return;
        parent[pRoot] = qRoot; // 这里的pRoot 此后就不会被find到
        sz[qRoot] += sz[pRoot];
    }
    public boolean isConnected(int p, int q){
        // 判定p q 是否在一个集合
        return find(p) == find(q);
    }
    public int find(int p) {
        // 寻找p 所在的集合
        if (p != parent[p])
            parent[p] = find(parent[p]);
        return parent[p];
    }
    public int size(int p){
        // p 所在集合有几个元素
        return sz[find(p)];
    }

}
