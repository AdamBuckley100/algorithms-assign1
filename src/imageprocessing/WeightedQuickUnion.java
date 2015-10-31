package imageprocessing;

public class WeightedQuickUnion {
	private int[] id;
	private int[] sz;
	private int count;

	public WeightedQuickUnion(int N) {
		count = N;
		id = new int[N];
		sz = new int[N];
		for (int i = 0; i < N; i++) {
			id[i] = i;
			sz[i] = 1;
		}
	}
	
	public int getSize()
	{
		return sz.length;
	}
	
//count is the number of individual objects
	public int count() {
		return count;
	}

	public int root(int p)
	{
		while (p != id[p])
		{
			id[p] = id[id[p]];
			p = id[p];
		}
		return p;
	}

	public boolean connected(int p, int q) {
		return root(p) == root(q);
	}

	public void union(int p, int q) {
		int rootOfP = root(p);
		int rootOfQ = root(q);
		if (rootOfP == rootOfQ) return;

		if (sz[rootOfP] < sz[rootOfQ]) { id[rootOfP] = rootOfQ; sz[rootOfQ] += sz[rootOfP]; }
		else
		{ id[rootOfQ] = rootOfP; sz[rootOfP] += sz[rootOfQ]; }
		count--;
	}
}