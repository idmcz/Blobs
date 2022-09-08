import java.io.IOException;

public class BlobTester {
	public static void main(String [] args) {
		//Blob blob = new Blob("./test/something.txt");
		
		Index ind = new Index();
		try {
			ind.init();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ind.openFile();
		ind.add("Hello.txt");
		ind.add("Bye.txt");
		ind.closeFile();
		
		ind.remove("Hello.txt");
		//ind.remove("Bye.txt");
	}
}
