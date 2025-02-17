import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Multi_Hashing {
    public static final int w = 1000;  // number of table entries
    public static final int flows = 1000;  // number of flows
    public static final int d = 3;  // number of hashes
    public static Random rand = new Random();
	

    public static void main(String[] args) {
        int[] hashTable = new int[w]; // table entries initialized to 0
        int counter = 0;

        for (int i = 0; i < flows; i++) {
            int flowID = rand.nextInt(Integer.MAX_VALUE);   // generate flow IDs randomly

            for (int j = 0; j < d; j++) {
                int index = Math.abs((flowID ^ (j * 31)) % w);
                if (hashTable[index] == 0) {    // empty slot found
                    hashTable[index] = flowID;
                    counter++;
                    break;
                }
            }
            // the flow is ignored as all locatios are occupied
        }

        // write output to file
        try (FileWriter writer = new FileWriter("hash_table_output.txt")) {
            writer.write("the number of flows in the hash table: " +counter + "\n");
            for (int entry : hashTable) {
                writer.write(entry + "\n");
            }
            System.out.println("Output written to hash_table_output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
