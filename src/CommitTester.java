import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.util.*;
public class CommitTester {
	public static void main(String [] args) throws NoSuchAlgorithmException, IOException {
		Blob b1 = new Blob ("test1.txt");
		Blob b2 = new Blob ("text2.txt");
		Index i = new Index ();
		i.add("test1.txt");
		i.add("text2.txt");
		Commit com1 = new Commit("did nothing", "Idalis Da-Goat McZeal", null);
//
		Blob b3 = new Blob ("test3.txt");
		Blob b4 = new Blob ("test4.txt");
		i.add("test3.txt");
		i.add("test4.txt");
		String s = com1.genSHA1Sub();
		Commit com2 = new Commit("did something", "Naalah Shiddy-Pants",com1.genSHA1Sub());
		
//		Blob b5 = new Blob ("test5.txt");
		i.add("test5.txt");
		i.delete("test1.txt");
		Commit com3 = new Commit("did another thing", "I AM THE THIRD FILE",com2.genSHA1Sub());
		
		
//		Blob b6 = new Blob ("test6.txt");
//		Blob b7 = new Blob ("test7.txt");
//		Blob b8 = new Blob ("test8.txt");
//		i.add("test6.txt");
//		i.add("test7.txt");
//		i.add("test8.txt");
//		Commit com4 = new Commit("did the last thing", "fourth the best",com3.genSHA1Sub());
//		
//		i.delete("text2.txt");
//		Commit com5 = new Commit("last one fast one", "NUMBA 555555",com4.genSHA1Sub());
		
	}
}
