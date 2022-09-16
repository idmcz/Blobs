import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Scanner;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Blob {
	 private String filePath;
	 
     private byte[] contentBytes;
     
     private String contentStr;
     
     private String sha1;
     
     private File SHA1Hash;
	
     
     private String directory = "objects/⁩";
	public Blob(String fileName) {
		
		filePath = new String(fileName);
		try {
	       contentBytes = readFile("./test/"+filePath, StandardCharsets.UTF_8);
	       sha1 = toSHA1(contentBytes);
	    } catch (IOException e) {
	        e.printStackTrace();
	    } catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		contentStr = new String(contentBytes, StandardCharsets.UTF_8);
		System.out.println(sha1);
		SHA1Hash = new File("./test/objects", sha1 + ".txt");
		try {
			if(SHA1Hash.createNewFile()) {
				
			}else {
				SHA1Hash.delete();
				SHA1Hash.createNewFile();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		wrFile(sha1+".txt", contentStr);
	}
	
	public String getSHA1() {
		return sha1;
	}
	
	public byte[] getBytes(){
		return contentBytes;
	}
	
	private static byte[] readFile(String path, Charset encoding) throws IOException
    {
		System.out.println(path);
		System.out.println(Paths.get(path));
		System.out.println(Files.readAllBytes(Paths.get(path)));
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return encoded;
        //return new String(encoded, encoding);
    }
	
	private void wrFile(String fileName, String str) {
		 try {
		      FileWriter myWriter = new FileWriter("./test/objects/"+fileName);
		      myWriter.write(str);
		      myWriter.close();
		      System.out.println("Successfully wrote to the file.");
		    } catch (IOException e) {
		      System.out.println("An error occurred.");
		      e.printStackTrace();
		    }
	}
	
	private String toSHA1(byte[] convertme) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-1");
	    return Base64.getEncoder().encodeToString(md.digest(convertme));
	}
	
}