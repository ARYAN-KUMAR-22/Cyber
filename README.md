# A Secure Virtual Machine Communication Framework in CloudSim Using AES and DES

This project demonstrates a secure communication framework for Virtual Machines (VMs) within a CloudSim simulation environment. It utilizes two widely used symmetric-key encryption algorithms — **AES (Advanced Encryption Standard)** and **DES (Data Encryption Standard)** — to simulate secure data exchange between VMs in a cloud computing infrastructure.

## 📁 Project Structure

```

AESandDES/
├── AES.java         # AES encryption and decryption logic
├── DES.java         # DES encryption and decryption logic
└── Test.java        # Demonstrates encryption/decryption functionality

````

## 🔐 Features

- Simulates secure communication between VMs in CloudSim
- Implements AES (128-bit) and DES (56-bit) encryption/decryption
- Demonstrates core cryptographic operations in a virtualized environment
- Lightweight and modular design, suitable for simulation-based research

## ⚙️ Requirements

- Java Development Kit (JDK) 8 or above
- Java compiler (e.g., `javac`)
- CloudSim framework (for complete simulation integration)
- Terminal or IDE (e.g., IntelliJ, Eclipse, VS Code)

## 🚀 How to Run

1. **Clone the repository**
   ```bash
   git clone https://github.com/ARYAN-KUMAR-22/Cyber.git
   cd Cyber/AESandDES
````

2. **Compile the code**

   ```bash
   javac *.java
   ```

3. **Run the test program**

   ```bash
   java Test
   ```

> The `Test.java` class shows sample encryption and decryption using both AES and DES algorithms.

## 🔍 Example Output

```
Original Text: Hello, World!

--- AES ---
Encrypted Text: [Encrypted_String]
Decrypted Text: Hello, World!

--- DES ---
Encrypted Text: [Encrypted_String]
Decrypted Text: Hello, World!
```

## 🛡️ Disclaimer

This project is intended for educational and research purposes. Do **not** use this implementation in production environments without enhancing the security model (e.g., key exchange protocols, secure key storage, IV generation, and advanced padding schemes).

```

---

Let me know if you'd like to integrate this with actual CloudSim simulation classes or expand this into a full simulation workflow with VM tasks and encrypted message exchanges!
```
