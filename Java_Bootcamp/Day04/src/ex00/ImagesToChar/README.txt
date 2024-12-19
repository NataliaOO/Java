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
│   │   └── it.bmp
├── target/
│   └── (compiled .class files)
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

### Execution

1. Run the application with the following syntax:
   ```
   java -cp ./target edu.school21.printer.app.Main <white_char> <black_char> <image_path>
   ```
   - `<white_char>`: Character to represent white pixels
   - `<black_char>`: Character to represent black pixels
   - `<image_path>`: Full path to the BMP image file

### Example
#### Input:
- White character: `.`
- Black character: `0`
- Image file: `src/resources/it.bmp`

#### Command:
```bash
java -cp ./target edu.school21.printer.app.Main . 0 src/resources/it.bmp
```