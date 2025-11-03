import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.File;  
import java.io.FileNotFoundException;  
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class WordCounter{
   
    public static int processText(StringBuffer input, String y) throws InvalidStopwordException, TooSmallText {
        Pattern p = Pattern.compile("[A-Za-z0-9']+");
        Matcher m = p.matcher(input);
        int totcount = 0;
        int beforestopcount = 0;
        boolean stopwordfound = false;
        
        while(m.find() == true) {
            String word = m.group();
            totcount++;

            if (stopwordfound == false) {
                if (word.equals(y)) {
                    stopwordfound = true;
                    beforestopcount = totcount;
                }
            }
        }
        if (totcount < 5) {
            throw new TooSmallText("Only found " + totcount + " words.");
        }
        if (y == null) {
            return totcount;
        }
        if ( stopwordfound == false) {
            throw new InvalidStopwordException("Couldn't find stopword: " + y);
        }

        return beforestopcount;
    }
    public static StringBuffer processFile(String y) throws EmptyFileException{
        String currentPath = y;

        while (true) {
            File f = new File(currentPath);
            
            try {
                Scanner fileScanner = new Scanner(f);
                StringBuffer result = new StringBuffer();

                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    result.append(line);
                }
                fileScanner.close();

                if (result.length() == 0) {
                    throw new EmptyFileException(currentPath + " was empty");
                }

                return result;

            } catch (FileNotFoundException e) {
                Scanner scan = new Scanner(System.in);
                currentPath = scan.nextLine();
            }
        }
    }

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);

        int option = 0;
        while (option != 1 && option != 2) {
            String line = in.nextLine();
            try {
            option = Integer.parseInt(line);
            } catch (Exception e) {}
        }

        String stopword;
        

        if (args.length >= 2) {
            stopword = args[1];
        } else {
            stopword = null;
        }
        StringBuffer text = null;
        String path;

        if (option == 1) {

            if (args.length >= 1) {
                path = args[0];
            } else {
                path = "";
            }
            try {
                text = processFile(path);
            } catch (EmptyFileException e) {
                text = new StringBuffer("");
            }
        } else if (option == 2) {
        
            String inputText;
            if (args.length >= 1) {
                inputText = args[0];
            }
            else {
                inputText = "";
            }
            text = new StringBuffer(inputText);
        }

        try {
            int count = processText(text, stopword);
            System.out.print("Found " + count + " words.");
        } catch (TooSmallText e) {
            System.out.print(e.toString());
        } catch (InvalidStopwordException e) {
            System.out.print(e.toString());
        }

    }
}