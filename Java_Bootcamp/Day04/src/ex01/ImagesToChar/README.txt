## Project Structure
```
ImagesToChar/
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
javac -d ./target src/java/edu/school21/printer/*/*.java
```

3. Copy the resources to the target folder
```
mkdir -p target/resources && cp -r src/resources/* target/resources
```

4. Create the JAR archive
Explanation:
- c creates a new archive.
- f specifies the output file (images-to-chars-printer.jar).
- m includes the manifest file (src/manifest.txt).
- Folders edu and resources/image.bmp are included in the JAR.

```
jar cfm target/images-to-chars-printer.jar src/manifest.txt -C target edu -C target resources/image.bmp
```

### Execution

1. Run the JAR Archive:
```
java -jar target/images-to-chars-printer.jar <white_char> <black_char>
```
   - `<white_char>`: Character to represent white pixels
   - `<black_char>`: Character to represent black pixels

### Example
#### Input:
- White character: `.`
- Black character: `0`

#### Command:
```bash
java -jar target/images-to-chars-printer.jar . 0
```