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
	}
}
