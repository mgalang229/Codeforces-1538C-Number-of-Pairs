import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

/*

goal: no. of valid pairs
therefore, the initial order doesn't matter

l <= a[i] + a[j] <= r

l - a[i] = lowerBound
r - a[i] = upperBound

5 - 1 = 4
8 - 1 = 7

we'll need two binary searches:

1. find the lowerBoundIndex
condition: a[mid] should be >= lowerBound and a[mid-1] should be less
if lowerBound exists:
- check if mid > i
if lowerBound doesn't exist:
- if a[i] < l, don't do anything
- if a[i] >= l, use this index 

2. find the upperBoundIndex
condition: a[mid] should be <= upperBound and a[mid+1] should be greater
if upperBound exists:
- check if mid > lowerBoundIndex
if upperBound doesn't exist:
check if a[lowerBoundIndex] + a[n-1] <= upperBound
if yes, use n 


Example:
1
14 10 15
1 2 2 3 3 4 5 5 5 5 5 6 6 7

(3,7)
(3,7)
(4,6) (4,6) (4,7)
(5,5) (5,5) (5,5) (5,5) (5,6) (5,6) (5,7)
(5,5) (5,5) (5,5) (5,6) (5,6) (5,7)
(5,5) (5,5) (5,6) (5,6) (5,7)
(5,5) (5,6) (5,6) (5,7)
(5,6) (5,6) (5,7)
(6,6) (6,7)
(6,7)

no. of valid pairs = 33

 */

public class Main {
	
	public static void main(String[] args) {	
		FastScanner fs = new FastScanner();
		PrintWriter out = new PrintWriter(System.out);
		int T = 1;
		T = fs.nextInt();
		for (int tc = 0; tc < T; tc++) {
			int n = fs.nextInt();
			int l = fs.nextInt();
			int r = fs.nextInt();
			int[] a = fs.readArray(n);
			sort(a);
			long ans = 0;
			for (int i = 0; i < n; i++) {
				//set actual lower and upper bounds
				int lowerBound = l - a[i];
				int upperBound = r - a[i];
				//find lower bound index
				int lowerBoundIndex = -1;
				int low = 0;
				int high = n - 1;
				while (low <= high) {
					int mid = low + (high - low) / 2;
					if (a[mid] >= lowerBound && mid > i) {
						if (mid - 1 == i) {
							lowerBoundIndex = mid;
							break;
						}
						if (mid - 1 >= 0 && a[mid-1] < lowerBound) {
							lowerBoundIndex = mid;
							break;
						}
						high = mid - 1;
					} else {
						low = mid + 1;
					}
				}
				//find upper bound index			
				int upperBoundIndex = -1;
				if (lowerBoundIndex != -1) {
					low = 0;
					high = n - 1;
					while (low <= high) {
						int mid = low + (high - low) / 2;
						if (a[mid] <= upperBound) {
							if (mid == n - 1) {
								upperBoundIndex = mid;
								break;
							}
							if (mid + 1 < n && a[mid+1] > upperBound) {
								upperBoundIndex = mid;
								break;
							}
							low = mid + 1;
						} else {
							high = mid - 1;
						}
					}
					//System.out.println(a[i] + " = " + upperBound);
					if (upperBoundIndex >= lowerBoundIndex) {
						long count = upperBoundIndex - lowerBoundIndex + 1;
						ans += count;
					}
				}
			}
			//System.out.println();
			//out.println(Arrays.toString(a));
			out.println(ans);
		}
		out.close();
	}
	
	static void sort(int[] a) {
		ArrayList<Integer> arr = new ArrayList<>();
		for (int x : a) {
			arr.add(x);
		}
		Collections.sort(arr);
		for (int i = 0; i < a.length; i++) {
			a[i] = arr.get(i);
		}
	}
	
	static void swap(int[] a, int x, int y) {
		int temp = a[x];
		a[x] = a[y];
		a[y] = temp;
	}
	
	static class FastScanner {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		StringTokenizer st = new StringTokenizer("");
		
		String next() {
			while (!st.hasMoreTokens()) {
				try {
					st = new StringTokenizer(br.readLine());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return st.nextToken();
		}
		
		int nextInt() {
			return Integer.parseInt(next());
		}
		
		int[] readArray(int n) {
			int[] a = new int[n];
			for (int i = 0; i < n; i++) {
				a[i] = nextInt();
			}
			return a;
		}
		
		long[] readLongArray(int n) {
			long[] a = new long[n];
			for (int i = 0; i < n; i++) {
				a[i] = nextLong();
			}
			return a;
		}
		
		long nextLong() {
			return Long.parseLong(next());
		}
		
		double nextDouble() {
			return Double.parseDouble(next());
		}
		
		String nextLine() {
			String str = "";
			try {
				if (st.hasMoreTokens()) {
					str = st.nextToken("\n");
				} else {
					str = br.readLine();
				}
			} catch(IOException e) {
				e.printStackTrace();
			}
			return str;
		}
	}
}
