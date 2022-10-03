import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
public class Commit {
	String pTree;
	String summary;
	String author;
	String date;
	String parent;
	String child;
	String prevT;
	
	public Commit(String summary, String author, String parent) throws IOException, NoSuchAlgorithmException {
		this.summary = summary;
		this.author = author;
		this.parent = parent;
	
		date = getDate();
		
		
		Tree t = new Tree (convertIndex(),getPTree());
		prevT = t.getTreeSha();
		child = prevT;
		wrCommitFile(genSHA1Sub());
		PrintWriter writer = new PrintWriter("index");
		writer.print("");
		writer.close();
		
		//at the end do tree: parent
		//in commit there should be a method abt get contents and create sha1, call that for parent to get that file
	}
	
	public String getPTree () throws IOException {
		if (parent!=null) {
		BufferedReader brTest = new BufferedReader(new FileReader("test/objects/"+parent));
	    brTest.readLine();
	    brTest.readLine();
	    return brTest.readLine();
		}
		return "null";
	}
	
	public static ArrayList <String> convertIndex() throws FileNotFoundException {
//		Path p = Paths.get("index.txt");
		Scanner s = new Scanner(new File ("index"));
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNextLine()){
			String ss = s.nextLine();
		    list.add(ss);
		}
		s.close();
		return list; 
	}
	
	private String getDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();  
		return(dtf.format(now));
	}
	
	private String toSHA1(String str) throws NoSuchAlgorithmException {
		try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            byte[] messageDigest = md.digest(str.getBytes());
            BigInteger no = new BigInteger(1, messageDigest);
            String hashtext = no.toString(16);
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
	}
	
	public String genSHA1Sub() throws NoSuchAlgorithmException {
		return(toSHA1(summary+date+author+parent));
	}
	
	public void setChild(Commit child) throws NoSuchAlgorithmException {
		this.child = child.genSHA1Sub();
	}
	public void setChild(String child) {
		this.child = child;
	}
	
	private void wrCommitFile(String fileName) throws IOException, NoSuchAlgorithmException {
		File file = new File("test/objects/"+fileName);
		System.out.println (file.getAbsolutePath());
		if(file.createNewFile()){
			
		}else{
			file.delete();
		file.createNewFile();
		}
		FileWriter fw = new FileWriter(file);
		/**
		 * XX PREVIOUS TREE'S SHA
		 * -- CURRENT TREE'S CHILD
		 */
//		if (getPTree()==null) 
//			fw.write("null");
//		else 
//			fw.write(getPTree());
//		fw.write("\n");
		if(child != null)
			fw.write(child);
		fw.write("\n");
		/** 
		 * PREVIOUS COMMIT'S SHA
		 */
		if (parent!=null)
		fw.write(parent);//where the parent tree is
		else
			fw.write("null");
		fw.write("\n");
		/**
		 * XX CHILD TREE/ CURRENT TREE
		 * NEXT COMMIT'S SHA, this one is actually just rewriting the previous commits third line
		 */
		fw.write("null");
		if (parent!=null) {
		FileReader f = new FileReader ("test/objects/"+parent);
		BufferedReader br = new BufferedReader (f);
		String pt1 = br.readLine() + "\n" + br.readLine();
		br.readLine();
		String pt2 = br.readLine() + "\n" + br.readLine() +  "\n" + br.readLine() + "\n";
		PrintWriter rew = new PrintWriter ("test/objects/"+parent);
		rew.write (pt1 + "\n" + genSHA1Sub() + "\n" + pt2);
		rew.close();
		}
		/**
		 * OTHER STUFF
		 */
		fw.write("\n" + author + "\n" + date + "\n" + summary);
		fw.close();
	}
	
}
