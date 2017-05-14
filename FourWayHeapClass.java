import java.util.NoSuchElementException;

public class FourWayHeapClass {
	
	int heapsize;
	
	
    huffmanTreeNode[] arr;

    public FourWayHeapClass(int capacity) {
    	this.heapsize = 3;
    	arr = new huffmanTreeNode[capacity + 3];
    	arr[0]=new huffmanTreeNode(0,-1, null, null);
    	arr[1]=new huffmanTreeNode(0,-1, null, null);
    	arr[2]=new huffmanTreeNode(0,-1, null, null);
    }
    
    private int parent(int n) 
    { 
    	return (n / 4) + 2; 
    }
    
    private int mchild(int m,int n) {
    	return (4 * (n-2))+(m - 1);
    }
    
	public void insert(huffmanTreeNode node) {
		if (heapsize == arr.length)
            throw new NoSuchElementException("Overflow Exception");
        arr[heapsize++] = node;
	    int child = heapsize - 1;
	    int parent = parent(child);
		//If parent node value is greater than child node value
		while( child > 2 && (arr[parent].frequency > arr[child].frequency)){
			swap(parent,child);
			child = parent;
			parent = parent(child);
		}
	}
	
	public huffmanTreeNode removeMin(){
		 if (heapsize == 0) 
			 throw new NoSuchElementException();
		 huffmanTreeNode minNode = arr[3];
		 arr[3] = arr[heapsize - 1];
		 heapsize--;
		 minHeapify(3);
		return minNode;
	}

    private void minHeapify(int i) {
		 int child;
	     huffmanTreeNode temp = arr[i];
	     while (mchild(1, i) < heapsize)
	     {
	         child = getMinChild(i);
	         if (arr[child].frequency < temp.frequency)
	             arr[i] = arr[child];
	         else
	             break;
	         i = child;
	     }
	     arr[i] = temp;
	}
    
    private int getMinChild(int i) 
    {
        int minchild = mchild(1, i);
        int k = 2;
        int child;
        while ((k <= 4) && ((child = mchild(k++, i)) < heapsize))
        {
            if (arr[child].frequency < arr[minchild].frequency) 
                minchild = child;
        }    
        return minchild;
    }

	private void swap(int i, int j) {
		huffmanTreeNode temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
	}

	public int size() {
			return heapsize;
	}

}
