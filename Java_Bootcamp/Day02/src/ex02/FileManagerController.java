package ex02;

import java.io.File;

public class FileManagerController {
    private final FileManager model;
    private final ConsoleView view;

    public FileManagerController(FileManager model, ConsoleView view) {
        this.model = model;
        this.view = view;
    }

    public void showCurrentDirectory() {
        view.showCurrentDirectory(model.getCurrentDirectory());
    }

    public void processCommand(String commandLine) {
        String[] patrs = commandLine.trim().split(" ", 2);
        String command = patrs[0];
        String argument = patrs.length > 1 ? patrs[1] : "";

        switch (command) {
            case "ls":
                File[] files = model.getDirectoryContents();
                view.showDirectoryContents(files, model);
                break;
            case "cd":
                if (model.changeDirectory(argument)) {
                    view.showCurrentDirectory(model.getCurrentDirectory());
                } else view.showErrorMessage("Folder not found: " + argument);
                break;
            case "mv":
                String[] moveArgs = argument.split(" ", 2);
                if (moveArgs.length != 2 || !model.moveOrRename(moveArgs[0], moveArgs[1])) {
                    view.showErrorMessage("Invalid move/rename operation.");
                }
                break;
            case "exit":
                System.exit(0);
                break;
            default: view.showErrorMessage("Unknown command: " + command);
        }
    }
}
