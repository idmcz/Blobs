import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Index {
	HashMap<String, String> blobs;
	public Index() {
		blobs = new HashMap<String,String>();
	}
	
	public void init() throws IOException {
		Files.createDirectory(Paths.get("/path/to/object"));
		File index = new File("index.txt");
		if(index.createNewFile()) {
		}else {
			index.delete();
			index.createNewFile();
		}
	}
	
	public void add(String fileName) {
		Blob nBlob = new Blob(fileName);
		blobs.put(nBlob.getSHA1(), fileName);
		try {
			PrintWriter writeKeyVal = new PrintWriter("index.txt");
			writeKeyVal.write(fileName+": "+nBlob.getSHA1());
			writeKeyVal.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void remove(String fileName) {
	}
}
