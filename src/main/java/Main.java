import java.io.File;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final String regexFileName = ".+?(?=\\.)";
    private static final String replacementFileName = "myFile";
    private static final String regexFileType = "(\\..*)";

    // Main method, calls other program functions
    public static void main (String[] args) {

        // Get users home directory
        String home = System.getProperty("user.home");

        // OS Independent file separators
        String filePath = (home + File.separator + "Desktop" + File.separator + "TestFolder" + File.separator);

        // Call function at passed filepath
        multiRename(filePath);
    }

    // Function performs regular expression matching
    public static String regexFunc(String regexPattern, String inputString) {

        // Compile regex pattern and match to input string
        Pattern pattern = Pattern.compile(regexPattern, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputString);

        // If a match is found, return match
        if (matcher.find()) {
            return matcher.group(0);
        }

        // Return nothing if no match is found
        return null;
    }

    // Multiple rename file function
    public static void multiRename(String filepath) {

        // Define folder as a file
        File folder = new File(filepath);

        // Separate files in folder into an array of files
        File[] listOfFiles = folder.listFiles();

        // Attempt to list files in folder
            if (listOfFiles != null) {

                // Sort the array of files
                Arrays.sort(listOfFiles);

                // Define number of files in the folder and initialise counter
                int numFilesInFolder = listOfFiles.length;
                int j = 1;

                // Try listing filenames in folder list
                try {

                    // Loop through each file in folder
                    for (int i = 0; i < (listOfFiles.length); i++) {

                        // If you find a file do the following
                        if (listOfFiles[i].isFile()) {

                            // Get file type of file as string
                            String fileTypeString = regexFunc(regexFileType, listOfFiles[i].getName());

                            // If the filetype was found then do function
                            if (fileTypeString!= null) {

                                // define new name of file
                                String renameFileString = (replacementFileName + "_" + j + fileTypeString);

                                // call function to rename files
                                renameFile(listOfFiles[i], renameFileString, filepath);
                                j = j + 1;
                            }
                        }
                    }
                    // Catch any error from reading files
                } catch (Exception e) {
                    System.out.println("Error: " + e);
                }

                // If the list is empty then print no files found to terminal
            } else {
                System.out.println("No files found!");
            }
    }

    // Function renames file and returns true if the operation was successful
    public static boolean renameFile(File file, String replacementName, String path) {

        // File (or directory) with new name
        File file2 = new File(path + replacementName);

        if (file2.exists()) {
            return false;
        }

        // Rename file (or directory)
        // Return true if renaming operation worked, false if it failed
        return file.renameTo(file2);
    }
}
