# **Hash Table Implementations (Multi-Hash, Cuckoo Hash, D-Left Hash)**

## **Overview**
This repository contains Java implementations of three different hash table techniques used in network packet processing and data stream management:

- **Multi-Hash Table**: Uses multiple hash functions to insert flows into a single table.
- **Cuckoo Hash Table**: Uses multiple hash functions and allows flow relocation to resolve collisions.
- **D-Left Hash Table**: Divides the hash table into multiple segments and places each flow into the least occupied segment.

Each hash table handles **1000 flows** and **1000 table entries**, using different hashing strategies to optimize placement and minimize collisions.

---

## **Hashing Techniques**
### **1. Multi-Hash Table**
- Uses **multiple hash functions** to compute different positions for each flow.
- Inserts the flow into the **first available slot**.
- If all positions are occupied, the flow is **discarded**.

### **2. Cuckoo Hash Table**
- Uses **multiple hash functions** to find candidate positions.
- If a position is occupied, the existing flow is **displaced (kicked out)** and moved to one of its alternative positions.
- The process repeats for a limited number of **Cuckoo Steps** before rejecting the flow.

### **3. D-Left Hash Table**
- Divides the table into **multiple segments** (e.g., 4 segments of 250 entries each).
- Each segment has its own **hash function**.
- A flow is placed in the **least occupied segment** to balance load.

---

## **How to Run**
### **Prerequisites**
- Install **Java 8+**.
- Clone the repository:
  ```bash
  git clone https://github.com/mhmunna1697/Hash-Tables
  cd Hash-Tables

## **Compilation & Execution**
Compile and run the individual hash table implementations. for example:

### **Multi-Hash Table**
```bash
javac Multi_Hashing.java
java Multi_Hashing
```

## **Output Files**
Each execution generates an output file:

- `multi-hashing-output.txt`
- `cuckoo_hash_output.txt`
- `d-left_hash_table_output.txt`


Each file contains:

- **First line** → Number of successfully inserted flows.
- **Remaining lines** → Table entries (flow IDs or `0` if empty).

---

## **Example Output**
Example contents of an output file (`cuckoo_hash_table_output.txt`):

- **940** → Number of flows successfully inserted.
- **Remaining lines** → Flow IDs in the table (or `0` for empty slots).
940
12345
67890
0
45678
...

---

## **Performance Analysis**
| **Hashing Method** | **Flows Inserted (Expected)** | **Collisions Handled** |
|--------------------|----------------------------|------------------------|
| Multi-Hash        | ~800-830                    | None (discard on collision) |
| Cuckoo Hash       | ~920-960                    | Displacement & relocation |
| D-Left Hash       | ~870-890                    | Balanced insertion |

## **Future Improvements**
- Implement **dynamic table resizing** to handle overflow scenarios efficiently.
- Enhance **hash function selection** for better distribution and reduced collisions.
- Optimize **Cuckoo hashing steps** to minimize displacement failures.
- Introduce **parallelized insertion** to improve performance in high-throughput environments.
- Develop **visualization tools** to analyze hash table behavior.