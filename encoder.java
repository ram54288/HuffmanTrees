import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class encoder {
	
	
static Map<Integer, Integer> frequency_table = new HashMap<Integer, Integer>();

public static Map<Integer, Integer> BuildFrequencyTable(String args) throws NumberFormatException, IOException {

	BufferedReader br = new BufferedReader(new FileReader(args));
    String line;
    while ((line = br.readLine()) != null && !line.isEmpty()) {
    	Integer data = Integer.parseInt(line);
    	if(frequency_table.containsKey(data)){
    		frequency_table.put(data, frequency_table.get(data) + 1);
    	}
    	else{
    		frequency_table.put(data, 1);
    	}
    }
    br.close();
    
    return frequency_table;
}

public static void encodeTheInput(Map<Integer, String> codeTable, String args) throws IOException {
	StringBuilder stringBuilder = new StringBuilder();
	BitSet bitSet;
	BufferedReader br = new BufferedReader(new FileReader(args));
    String line;
    Integer data;
    while((line = br.readLine()) != null && !line.isEmpty()){
    	data = Integer.parseInt(line);
    	stringBuilder.append(codeTable.get(data));
    }
	br.close();
	bitSet = getBitSet(stringBuilder.toString());
    OutputStream out = new FileOutputStream("encoded.bin");
    byte[] totalBytes = bitSet.toByteArray();
    out.write(totalBytes);
    if(out !=null)
        out.close();
}

private static BitSet getBitSet(String input) {
	
	 BitSet bitSet = new BitSet(input.length());
	 int bitcounter = 0;
        for (Character c: input.toCharArray()) {
            if (c.equals('1')) {
                bitSet.set(bitcounter);
            } 
            bitcounter++;
        }
     return bitSet;
}
	
	private static FourWayHeapClass createFourWayHeapQueue(Map<Integer, Integer> freq_table) {
		
		FourWayHeapClass heap = new FourWayHeapClass(freq_table.size() + 1);
        for (Map.Entry<Integer, Integer> entry : freq_table.entrySet()) {
            heap.insert(new huffmanTreeNode(entry.getKey(), entry.getValue(), null, null));
        }
        return heap;
	}

	private static huffmanTreeNode build_tree_using_4way_heap(FourWayHeapClass priorityQueue) {
		while (priorityQueue.size() > 4) {
            final huffmanTreeNode node1 = priorityQueue.removeMin();
            final huffmanTreeNode node2 = priorityQueue.removeMin();
            huffmanTreeNode node = new huffmanTreeNode(-1, node1.frequency + node2.frequency, node1, node2);
            priorityQueue.insert(node);
        }
        return priorityQueue.removeMin();
	}
	
	private static void generateHuffmanCodes(Set<Integer> dataset, huffmanTreeNode node) {
       
       makeHuffmanCode(node, "");
       BufferedWriter bw;
       try {
			bw = new BufferedWriter(new FileWriter("code_table.txt"));
			for(Map.Entry<Integer, String> entry : codetable.entrySet()){
				bw.write(entry.getKey()+" "+entry.getValue()+"\n");
			}
			bw.close();
       }
       catch (IOException e) {
			e.printStackTrace();
       }
    }

    private static void makeHuffmanCode(huffmanTreeNode node, String s) {
    	if(node == null)
    		return;
        if (node.left == null && node.right == null) {
            codetable.put(node.ch, s.toString());
            return;
        }    
        makeHuffmanCode(node.left, s+"0");
        makeHuffmanCode(node.right, s+"1");
    }
    static Map<Integer, String> codetable = new HashMap<Integer, String>();

	public static void main(String[] args) throws NumberFormatException, IOException {

    	Map<Integer, Integer> freq_table = encoder.BuildFrequencyTable(args[0]);
    	FourWayHeapClass priorityQueue = createFourWayHeapQueue(freq_table);
    	huffmanTreeNode root = build_tree_using_4way_heap(priorityQueue);
    	generateHuffmanCodes(freq_table.keySet(), root);
    	encoder.encodeTheInput(codetable,args[0]);
	}

}
