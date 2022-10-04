import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BlobsTesters {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		Index i = new Index ();
		i.init();
	}

	@Test
	void test() throws IOException, NoSuchAlgorithmException {
	
		Blob b1 = new Blob ("test1.txt");
		Blob b2 = new Blob ("text2.txt");
		Index i = new Index ();
		i.init();
		i.add("test1.txt");
		i.add("text2.txt");
		Commit com1 = new Commit("did nothing", "Idalis Da-Goat McZeal", null);
//
		Blob b3 = new Blob ("test3.txt");
		Blob b4 = new Blob ("test4.txt");
		i.add("test3.txt");
		i.add("test4.txt");
		i.edit("text2.txt");
		String s = com1.genSHA1Sub();
		Commit com2 = new Commit("did something", "Naalah Shiddy-Pants",com1.genSHA1Sub());
		
//		Blob b5 = new Blob ("test5.txt");
		i.add("test5.txt");
		i.delete("test1.txt");
		Commit com3 = new Commit("did another thing", "I AM THE THIRD FILE",com2.genSHA1Sub());
		
		
		Blob b6 = new Blob ("test6.txt");
		Blob b7 = new Blob ("test7.txt");
//		Blob b8 = new Blob ("test8.txt");
		i.add("test6.txt");
		i.add("test7.txt");
		i.delete("text4.txt");
		i.delete("text5.txt");
//		i.add("test8.txt");
		Commit com4 = new Commit("did the last thing", "fourth the best",com3.genSHA1Sub());
		
		Blob b8 = new Blob ("test8.txt");
		i.add("test8.txt");
		Commit com5 = new Commit("LAST", "LAST MCZEAL",com4.genSHA1Sub());
	}

}
