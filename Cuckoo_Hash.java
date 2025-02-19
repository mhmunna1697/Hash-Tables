import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Cuckoo_Hash {
    public static final int w = 1000;   // number of table entries
    public static final int flows = 1000;   // number of flows
    public static final int d = 3;  // number of hashes
    public static final int s = 2; // number of Cuckoo steps
    public static Random rand = new Random();


    public static void main(String[] args) {
        int[] hashTable = new int[w];   // table entries initialized to 0
        int insertedFlows = 0;  // count the number of flows in the hash table

        for (int i = 0; i < flows; i++) {
            int flowID = rand.nextInt(Integer.MAX_VALUE);   // generate flow IDs randomly
            if (cuckooInsert(flowID, hashTable)) {
                insertedFlows++;
            }
        }

        // write output to file
        try (FileWriter writer = new FileWriter("cuckoo_hash_output.txt")) {
            writer.write("the number of flows in the Cuckoo hash table: " +insertedFlows + "\n");
            for (int entry : hashTable) {
                writer.write(entry + "\n");
            }
            System.out.println("Output written to cuckoo_hash_output.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // record flows in the Cuckoo hash table
    public static boolean cuckooInsert(int flowID, int[] table) {
        int[] hashPositions = new int[d];
        for (int i = 0; i < d; i++) {
            hashPositions[i] = Math.abs((flowID ^ (i * 31)) % w);
        }

        for (int pos : hashPositions) {
            if (table[pos] == 0) {  // empty slot found
                table[pos] = flowID;
                return true;
            }
        }
        // No available slot found. now try to relocate existing flows and place new flows
        for (int i = 0; i < d; i++) {
            if (cuckooMove(table, flowID, hashPositions, i, s)) {
                return true;
            }
        }
        return false; // unable to place the flow
    }

    // relocate existing flows and place new flows
    public static boolean cuckooMove(int[] table, int flowID, int[] hashPositions, int idx, int stepsLeft) {
        if (stepsLeft == 0) {
            return false;   // reached max displacement limit
        }

        int pos = hashPositions[idx];
        int displacedFlowID = table[pos];
        table[pos] = flowID;

        if (displacedFlowID == 0) {
            return true;    // successfully placed
        }

        int[] displacedHashPositions = new int[d];
        for (int i = 0; i < d; i++) {
            displacedHashPositions[i] = Math.abs((displacedFlowID ^ (i * 31)) % w);
        }

        // find available position for displaced flows
        for (int i = 0; i < d; i++) {
            if (displacedHashPositions[i] == pos) {
                continue;   // skip the current position
            }
            if (table[displacedHashPositions[i]] == 0 || 
                cuckooMove(table, displacedFlowID, displacedHashPositions, i, stepsLeft - 1)) {
                table[displacedHashPositions[i]] = displacedFlowID;
                return true;
            }
        }
        // if failed, revert the temporary placement
        table[pos] = displacedFlowID;
        return false;
    }
}
