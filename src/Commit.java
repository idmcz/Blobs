import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
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
	
	public Commit(String pTree, String summary, String author, String parent) throws IOException, NoSuchAlgorithmException {
		this.pTree = pTree;
		this.summary = summary;
		this.author = author;
		this.parent = parent;
	
		child = null;
		date = getDate();
		wrCommitFile(genSHA1Sub());
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
