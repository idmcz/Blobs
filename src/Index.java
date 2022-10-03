import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Index {
	HashMap<String, String> blobs;
	FileWriter writeKeyVal;
	
	public Index() throws IOException {
		blobs = new HashMap<String,String>();
		init ();
		openFile();
	}
	
	public void init() throws IOException {
		//creates empty file of head
		//when you create a commit only thing it should have is sha of most recent commit
		//^^ only update head if you create a commit of its sha
		//should read from head
		//parent is always the head, head always only has ONE sha in it
		File objectsFolder = new File("./test/objects");
		 
        for (File file: Objects.requireNonNull(objectsFolder.listFiles())) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
		Files.deleteIfExists(Paths.get("./test/objects"));
		Files.createDirectory(Paths.get("./test/objects"));
		File index = new File("index");
		if(index.createNewFile()) {
		}else {
			index.delete();
			index.createNewFile();
		}
	}
	
	public void delete (String fileName) throws FileNotFoundException {
		blobs.remove(fileName);
		PrintWriter out = new PrintWriter("index");
		out.append("*deleted* " + fileName);
	}
	
	public void add(String fileName) throws IOException {
		Blob nBlob = new Blob(fileName);
		blobs.put(fileName,nBlob.getSHA1());
		try {
			String placebo = fileName+" : "+nBlob.getSHA1()+"\n";
			try(FileWriter fw = new FileWriter("index", true);
				    BufferedWriter bw = new BufferedWriter(fw);
				    PrintWriter out = new PrintWriter(bw))
				{
				    out.println(placebo);
				    //more code
				} catch (IOException e) {
				    //exception handling left as an exercise for the reader
				}
//			writeKeyVal.write(fileName+" : "+nBlob.getSHA1()+"\n");
			writeKeyVal.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openFile() {
		try {
			writeKeyVal = new FileWriter("index");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeFile() {
		try {
			writeKeyVal.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void remove(String fileName) {
		try {
			Files.delete(Paths.get("./test/objects/"+blobs.get(fileName)+".txt"));
			Files.delete(Paths.get("index"));
			blobs.remove(fileName);
			File index = new File("index");
			index.createNewFile();
			FileWriter indexWr = new FileWriter("index");
		    for (String key : blobs.keySet()) {
		    	indexWr.write(key + " : " + blobs.get(key) + "\n");
		    }
		    indexWr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}