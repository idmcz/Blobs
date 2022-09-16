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
	
	public Index() {
		blobs = new HashMap<String,String>();
	}
	
	public void init() throws IOException {
		
		File objectsFolder = new File("./test/objects");
		 
        for (File file: Objects.requireNonNull(objectsFolder.listFiles())) {
            if (!file.isDirectory()) {
                file.delete();
            }
        }
		Files.deleteIfExists(Paths.get("./test/objects"));
		Files.createDirectory(Paths.get("./test/objects"));
		File index = new File("./test", "index.txt");
		if(index.createNewFile()) {
		}else {
			index.delete();
			index.createNewFile();
		}
	}
	
	public void add(String fileName) {
		Blob nBlob = new Blob(fileName);
		blobs.put(fileName,nBlob.getSHA1());
		try {
			
			writeKeyVal.write(fileName+" : "+nBlob.getSHA1()+"\n");
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
			writeKeyVal = new FileWriter("./test/index.txt");
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
			Files.delete(Paths.get("./test/index.txt"));
			blobs.remove(fileName);
			File index = new File("./test", "index.txt");
			index.createNewFile();
			FileWriter indexWr = new FileWriter("./test/index.txt");
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