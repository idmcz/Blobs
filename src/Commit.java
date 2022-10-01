import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public Commit(String summary, String author, String parent) throws IOException, NoSuchAlgorithmException {
		this.summary = summary;
		this.author = author;
		this.parent = parent;
	
		child = null;
		date = getDate();
		wrCommitFile(genSHA1Sub());
		
		Tree t = new Tree (convertIndex());
		Path p = Paths.get("index.txt");
		Files.delete(p);
		
		//at the end do tree: parent
		//in commit there should be a method abt get contents and create sha1, call that for parent to get that file
	}
	
	public static ArrayList convertIndex() throws FileNotFoundException {
		Scanner s = new Scanner(new File("index.txt"));
		ArrayList<String> list = new ArrayList<String>();
		while (s.hasNext()){
		    list.add(s.next());
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
		byte[] convertme = str.getBytes();
	    MessageDigest md = MessageDigest.getInstance("SHA-1");
	    return Base64.getEncoder().encodeToString(md.digest(convertme));
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
		File file = new File(".test/objects/"+fileName);
		if(file.createNewFile()){}else{file.delete();file.createNewFile();}
		FileWriter fw = new FileWriter(file);
		fw.write("./test/objects/"+pTree + "\n");
		if(parent != null)
			fw.write("./test/objects/"+parent);
		fw.write("\n");
		if(child != null)
			fw.write("./test/objects/"+child);
		fw.write("\n" + author + "\n" + date + "\n" + summary);
		fw.close();
	}
	
}
