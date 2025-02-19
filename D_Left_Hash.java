import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class D_Left_Hash {
    public static final int w = 1000;   // number of table entries
    public static final int flows = 1000;   // number of flows
    public static final int d = 4;  // number of segments (hashes)
    public static final int segment_size = w / d;   // each segment has 250 table entries
    public static Random rand = new Random();


    public static void main(String[] args) {
        int[] hashTable = new int[w]; // table entries initialized to 0
        int insertedFlows = 0;  // count the number of flows in the hash table

        for (int i = 0; i < flows; i++) {
            int flowID = rand.nextInt(Integer.MAX_VALUE);   // generate flow IDs randomly

            for (int j = 0; j < d; j++) {
                int segmentStart = j * segment_size;
                int pos = segmentStart + Math.abs((flowID ^ (j * 31)) % segment_size);
                if (hashTable[pos] == 0) {  // empty slot found
                    hashTable[pos] = flowID;
                    insertedFlows++;
                    break;
                }
            }
            // the flow is ignored as all locatios are occupied
        }

        // write output to file
        try (FileWriter writer = new FileWriter("d-left-hash-output.txt")) {
            writer.write("the number of flows in the d-left hash table: " +insertedFlows + "\n");
            for (int entry : hashTable) {
                writer.write(entry + "\n");
            }
            System.out.println("Output written to d-left-hash-output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
