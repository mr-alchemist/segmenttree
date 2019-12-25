import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Arrays;
import java.util.Scanner;

public class SegmentTreeSum {
	
	long[] array;
	int size;
	int startIndex;
	private SegmentTreeSum() {
		
	}
	
	public SegmentTreeSum(int size) {
		//if(size <= 0)
		//	throw new Exception("The size parameter must be more than zero, but taken " + size);
		int pow = 0;
		int d = size;
		for(;d > 1;pow++) {
			d = d / 2;
		}
		
		int x2 = (int)(Math.pow(2,pow));
		if(x2 != size)x2 = x2*2;
		startIndex = x2 - 1;
		int arraySize = x2*2 - 1;
		
		this.size = size;
		array = new long[arraySize];
		Arrays.fill(array, 0);
	}
	
	
	public void set(int index, long value) {//индексация с 0
		int realIndex = startIndex + index;
		if(array[realIndex] == value)return;
		array[realIndex] = value;
		updateTree(realIndex);
	}
	
	public long get(int index) {//индексация с 0
		return array[startIndex + index];
	}
	
	public long getSum(int L, int R) {//индексация с 0
		L = L + startIndex;
		R = R + startIndex;
		
		long res = 0;
		while(L <= R) {
			if( (L & 1) == 0 )
				res += array[L];
			if( (R & 1) == 1 )
				res += array[R];
			
			L = L/2;
			
			if(R == 1)
				R = -1;
			else
				R = (R - 2)/2;
		}
		return res;
	}
	
	public void setI1(int index, long value) {//индексация с 1
		set(index - 1, value);
	}
	public long getI1(int index) {//индексация с 1
		return get(index - 1);
	}
	
	public long getSumI1(int L, int R) {//индексация с 1
		return getSum(L - 1, R - 1);
	}
	
	private void updateTree(int newValueIndex) {
		if(newValueIndex == 0)return;
		int index = getUpperIndex(newValueIndex);
		while(index >= 0) {
			int leftIndex = getLowerLeftIndex(index);
			int rightIndex = getLowerRightIndex(index);
			array[index] = array[leftIndex] + array[rightIndex];
			if(index == 0)break;
			index = getUpperIndex(index);
		}
	}
	
	private int getUpperIndex(int index) {
		return (index - 1)/2;
	}
	
	private int getLowerLeftIndex(int index) {
		return index*2 + 1;
	}
	
	private int getLowerRightIndex(int index) {
		return index*2 + 2;
	}
	
	public static void main(String[] args) {
		
		String inFileName = "sum.in";//"D:\\projects\\eclipse-workspace\\segtree\\src\\sum.in";
		String outFileName = "sum.out";//"D:\\projects\\eclipse-workspace\\segtree\\src\\sum.out";
		
		try {
			Writer writer = new FileWriter(outFileName);
			
			Scanner scanner = new Scanner(new File(inFileName));
			int N = scanner.nextInt();
			int k = scanner.nextInt();
			
			SegmentTreeSum st = new SegmentTreeSum(N);
			for(int i = 0; i < k;i++) {
				String command = scanner.next();
				int i1 = scanner.nextInt();
				int i2 = scanner.nextInt();
				if(command.equals("A")) {
					st.setI1(i1, i2);
				}
				if(command.equals("Q")) {
					long sum = st.getSumI1(i1, i2);
					writer.write(Long.toString(sum)+"\r\n");
				}
			}
			
			scanner.close();
			writer.close();
		}
		catch(Exception ex){
			System.out.println("error: " + ex.getMessage()); 
		}
		
		
	}
}
