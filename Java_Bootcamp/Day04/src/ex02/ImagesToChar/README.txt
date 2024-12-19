## Project Structure
```
ImagesToChar/
├── lib/
│   └── jcommander-1.82.jar
│   ├── JColor-5.5.1.jar
├── src/
│   ├── java/
│   │   ├── edu/
│   │   │   ├── school21/
│   │   │   │   ├── printer/
│   │   │   │   │   ├── app/
│   │   │   │   │   │   └── Main.java
│   │   │   │   │   ├── logic/
│   │   │   │   │   │   └── ImageConverter.java
│   ├── resources/
│   │   └── image.bmp
│   ├── manifest.txt
├── target/
│   ├── edu/
│   │   ├── school21/
│   │   │   ├── printer/
│   │   │   │   ├── app/
│   │   │   │   │   └── Main.class
│   │   │   │   ├── logic/
│   │   │   │   │   └── ImageConverter.class
│   ├── com/
│   │   ├── diogonunes/
│   │   ├── beust/
│   ├── resources/
│   │   └── image.bmp
│   ├── images-to-chars-printer.jar
├── README.txt
```

## Usage
### Compilation
1. Deleting and Creating target directory
```
rm -rf target && mkdir target
```

2. Compile the source files:
```
javac -cp "lib/*" src/java/edu/school21/printer/*/*.java -d target
```

3. Copy the resources to the target folder
```
mkdir -p target/resources && cp -r src/resources/* target/resources
cd target && jar fx ../lib/JColor-5.5.1.jar && jar fx ../lib/jcommander-1.82.jar && cd ..
```

4. Create the JAR archive
Explanation:
- c creates a new archive.
- f specifies the output file (images-to-chars-printer.jar).
- m includes the manifest file (src/manifest.txt).
- Folders edu and resources/image.bmp are included in the JAR.

```
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target edu -C target com -C target resources/image.bmp
```

### Verify JAR structure
To ensure the JAR contains the necessary files:
```bash
jar tf target/images-to-chars-printer.jar
```
Expected output:
```
META-INF/MANIFEST.MF
edu/school21/printer/app/Main.class
edu/school21/printer/logic/ImageConverter.class
com/beust/jcommander/...
com/diogonunes/jcolor/...
resources/image.bmp
```

### Execution

1. Run the JAR Archive:
```
java -jar target/images-to-chars-printer.jar --while=<white_char> --black=<black_char>
```
   - `<white_char>`: Character to represent white pixels
   - `<black_char>`: Character to represent black pixels

### Example
#### Input:
- White character: `magenta`
- Black character: `blue`

#### Command:
```bash
java -jar target/images-to-chars-printer.jar --black=magenta --white=blue
```
