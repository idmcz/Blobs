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
	
	public Commit(String pTree, String summary, String author) {
		this.pTree = pTree;
		this.summary = summary;
		this.author = author;
		parent = null;
		child = null;
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
	
	
}
