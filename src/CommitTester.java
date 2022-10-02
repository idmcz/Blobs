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
//		Path p = Paths.get("index.txt");
//		Files.delete(p);
		Blob b3 = new Blob ("test3.txt");
		Blob b4 = new Blob ("test4.txt");
		Index i2 = new Index ();
		i2.init();
		i2.add("test3.txt");
		i2.add("test4.txt");
		/**
		 * make a bunch of files
		 * add only a couple, commit, add then commit
		 */
		//parent is the sha'd version of the commit
		Commit com2 = new Commit("did something", "Naalah Shiddy-Pants",com1.genSHA1Sub());
//		Path p2 = Paths.get("test/index.txt");
//		Files.delete(p2);
//		Scanner sc = new Scanner();
	}
}
