
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class decoder {
	
	static decodeNode root = new decodeNode(-1);
	
	public static void decodeOutput(String[] args){
			Map<Integer, String> codeTable = readCodeTable(args[1]);
			buildDecodeTree(codeTable);
			String encodedString = readEncodedString(args[0]);
			decodeString(encodedString,root);
	}
	
public static Map<Integer, String> readCodeTable(String args) {
		
		BufferedReader br;
		String line;
		Map<Integer, String> codeTable = new HashMap<Integer, String>();
		try {
		br = new BufferedReader(new FileReader(args));
		while ((line = br.readLine()) != null && !line.isEmpty()) {
	    	String[] data = line.split(" ");
	    	codeTable.put(new Integer(data[0]), data[1]);
	    }
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
    	return codeTable;
	}

	private static String readEncodedString(String args) {

		StringBuilder encodedInput = new StringBuilder();
		File file = new File(args);
		byte[] data = new byte[(int)file.length()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(data);
			in.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		BitSet bitset = new BitSet();
		bitset = BitSet.valueOf(data);
		for(int i = 0; i < data.length * 8; i++){
			if(bitset.get(i)){
				encodedInput.append('1');
			}
			else{
				encodedInput.append('0');
			}
		}
		return encodedInput.toString();
	}

	private static void decodeString(String encodedString, decodeNode root) {
 		FileWriter fw=null;
		BufferedWriter bw=null;
		decodeNode node = root;
		try {
			fw = new FileWriter("decoded.txt");
			bw = new BufferedWriter(fw);
			 char[] c = encodedString.toCharArray();
			 for (int bitcounter = 0; bitcounter < encodedString.length(); bitcounter++) {
				 if (node!=null && c[bitcounter] == '0') {
		            	node = node.left;
		            	if(node.left == null && node.right == null){
		            		bw.write(""+node.data);
		            		node = root;
		            		if( bitcounter != encodedString.length() - 1){
		            			bw.write('\n');
		            		}
					 	}
		            } 
		            else if (node!=null && c[bitcounter] == '1') {
		            	node = node.right;
		            	if(node.left == null && node.right == null){
		            		bw.write(""+node.data);
		            		node = root;
		            		if( bitcounter != encodedString.length() - 1){
		            			bw.write('\n');
		            		}
					 	}
		            }
		        }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(bw!=null){
				try {
					bw.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(fw!=null){
			  try {
				fw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  }
		}
	}

	
	private static void buildDecodeTree(Map<Integer, String> codeTable) {
		for (Map.Entry<Integer, String> entry : codeTable.entrySet()) {
            makeLeafNode(entry.getKey(),entry.getValue());
        }
	}

	private static void makeLeafNode(Integer key,String value) {
		decodeNode node = root;
		decodeNode element = new decodeNode(key);
		char[] bitarray = value.toCharArray();
		for(int i=0; i < bitarray.length - 1 ;i++){
			if(bitarray[i] == '0'){
				if(node.left == null){
					node.left = new decodeNode(-1);
				}
				node = node.left;
			}
			else if(bitarray[i] == '1'){
				if(node.right == null){
					node.right = new decodeNode(-1);
				}
				node = node.right;
			}
		}
		if(bitarray[bitarray.length - 1] == '0'){
			if(node.left == null){
				node.left = element;
			}
		}
		else{
			if(node.right == null){
				node.right = element;
			}
		}
	}

	public static void main(String[] args) throws NumberFormatException, IOException {

		decoder.decodeOutput(args);
		
	}
	

}
