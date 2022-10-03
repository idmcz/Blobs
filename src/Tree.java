import java.util.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;

public class Tree {
	
	private ArrayList<String> insideIndex;
	public String pTreeSha;
	private String sha1Array = ""; 
	public ArrayList <String> toPut;
	
	public Tree (ArrayList<String> strArr, String pTreeSha) throws IOException {
		ArrayList<String> srrr = strArr;
		this.pTreeSha = pTreeSha;
		this.insideIndex = strArr; //save array into fullArray
		for (String s : insideIndex) {//create string with all lines in array
			sha1Array+=s + "\n";
		}
//		String what = getSha1(sha1Array.trim());
		sha1Array = getSha1(sha1Array.trim());//turn list of blobs/trees -> sha1 for name
		
		writeList();
	}
	
//	public void comb (String prev, String fName) throws IOException {
//		BufferedReader br = new BufferedReader(new FileReader (prev));
//		String first = br.readLine(); // hash of previous tree
//		while (br.ready()) {
//			String cur = br.readLine();
//			toPut.add(cur);
//			if (cur.substring(cur.length()-fName.length()).equals(fName)) {
//				toPut.add(first);
//			}
//		}
//		comb (first.substring(7),fName);
//	}
	
	private void writeList() throws IOException {
		File f = new File("test/objects/" + sha1Array);
		FileWriter writer = new FileWriter (f);
		writer.append("tree : " + pTreeSha + "\n");
		for (String str : insideIndex) {
			//catching deleted files
//			if (str.substring (0,9).equals ("*deleted*")) {
//				comb (pTreeSha, str.substring(10));
//			}
			if (!str.equals("")) {
			//issue is that @index 1 of inside index is a space, should fix later
			String sha = str.substring(str.length()-40);
			String fname = str.substring(0,str.length()-42);
			writer.append("blob : " +sha+ " " +fname+"\n");
			}
		}
		writer.close();
	}
	
	public String getTreeSha () {
		return sha1Array;
	}
	
	private static String getSha1 (String input) {
		String value = input;
		String sha1 = "";
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-1");
	        digest.reset();
	        digest.update(value.getBytes("utf8"));
	        sha1 = String.format("%040x", new BigInteger(1, digest.digest()));
		} catch (Exception e){
			e.printStackTrace();
		}

		return sha1;
	}
}
