import static org.junit.Assert.assertTrue;
import java.util.*;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
//6 tests - 2 for blob, 3 for index 
//check file content 
//check file location
//check index and objects
//adds blob to index file 
//removes blob from index file
//multiple adds and at least 1 remove
class JUnitBlobTest {
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		//create test folder, objects folder in test, three txt files
		File foldert = new File("test");
		foldert.mkdirs();
		File foldero = new File("test/objects");
		foldero.mkdirs();
		PrintWriter writer = new PrintWriter("test/txt.txt");
		writer.println("some content");
		writer.close();
		PrintWriter writer1 = new PrintWriter("test/txt1.txt", "UTF-8");
		writer1.println("some content1");
		writer1.close();
		PrintWriter writer2 = new PrintWriter("test/txt2.txt", "UTF-8");
		writer2.println("some content2");
		writer2.close();
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		//deleting all txt files
		File f = new File("test/txt.txt");
		f.delete();
		File f1 = new File("test/txt1.txt");
		f1.delete();
		File f2 = new File("test/txt2.txt");
		f2.delete();
		File index = new File("test/index.txt");
		index.delete();
		
		//deleting objects folder w/ all contents  inside
		File folder = new File("test/objects");
		String[]entries = folder.list();
		for(String s: entries){
		    File currentFile = new File(folder.getPath(),s);
		    currentFile.delete();
		}
		folder.delete();
		
		//delete test folder
		File test = new File("test");
		test.delete();
	}

	@Test
	void blobFileContent() {
		Blob b = new Blob("txt.txt");
		File f = new File("test/objects/94e66df8cd09d410c62d9e0dc59d3a884e458e05");
		assertTrue(f.exists());//checks if file named the sha1 of some content exists in objects
	}
	
	@Test
	void checkIndexAndObjects() throws IOException {
		
		
		//initiate, add to index
		Index index = new Index();
		index.openFile();//maybe write this into the index.java class initiator/add method - need it to initialize print writer
		index.init();
		
		File ind = new File("test/index.txt");
		assertTrue(ind.exists());
		//Create scanner for index.txt 
		File f = new File("test/index.txt");
		Scanner s = new Scanner(f);
		index.add("txt1.txt");
		index.add("txt.txt");
		index.add("txt2.txt");
		
		//Check of blobs exist in objects folder 
		assertTrue(new File("test/objects/94e66df8cd09d410c62d9e0dc59d3a884e458e05").exists());
		assertTrue(new File("test/objects/e2a34d27ec895d3921f201a107386c9fc67b9885").exists());
		assertTrue(new File("test/objects/79eec2bd89a58604dc3f0537108b07200e894d7").exists());
		//check updated index first line and second line 
		

		assertTrue(s.nextLine().equals("txt1.txt : e2a34d27ec895d3921f201a107386c9fc67b9885"));
		assertTrue(s.nextLine().equals("txt.txt : 94e66df8cd09d410c62d9e0dc59d3a884e458e05"));


//		index.closeFile();
		
		//Remove, check remove
		index.remove("txt.txt");
		index.remove("txt1.txt");
		index.remove("txt2.txt");
		assertFalse(new File("test/objects/94e66df8cd09d410c62d9e0dc59d3a884e458e05").exists());
		assertFalse(new File("test/objects/e2a34d27ec895d3921f201a107386c9fc67b9885").exists());
		assertFalse(new File("test/objects/79eec2bd89a58604dc3f0537108b07200e894d79").exists());
		
		//Check removal from index.txt
		assertFalse(s.hasNext());//if removed all, would be empty
		
	}
	
	@Test
	void treeTest() throws IOException {
		//create arraylist, add elements (blobs and trees)
		ArrayList<String> list = new ArrayList<String>();
		list.add("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f");
		list.add("blob : 01d82591292494afd1602d175e165f94992f6f5f");
		list.add("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83");
		list.add("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b");
		list.add("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976");
		
		//Initialize tree with list, check if file with correct sha1 exists in objects
		Tree tree = new Tree(list);
		File file = new File("test/objects/dd4840f48a74c1f97437b515101c66834b59b1be");
		assertTrue(file.exists());
		
		//Check if all lines exist and are correct 
		Scanner scanner = new Scanner(file);
		assertTrue(scanner.nextLine().equals("blob : 81e0268c84067377a0a1fdfb5cc996c93f6dcf9f"));
		assertTrue(scanner.nextLine().equals("blob : 01d82591292494afd1602d175e165f94992f6f5f"));
		assertTrue(scanner.nextLine().equals("blob : f1d82236ab908c86ed095023b1d2e6ddf78a6d83"));
		assertTrue(scanner.nextLine().equals("tree : bd1ccec139dead5ee0d8c3a0499b42a7d43ac44b"));
		assertTrue(scanner.nextLine().equals("tree : e7d79898d3342fd15daf6ec36f4cb095b52fd976"));
		file.delete();
	}

}
