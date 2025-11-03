public class WordCounter{

    private static Scanner SCANNER = new Scanner(System.in);
   
    public static int processText(StringBuffer input, String y) throws InvalidStopwordException, TooSmallText {
        Pattern p = Pattern.compile("[A-Za-z0-9']+");
        Marcher m = p.matcher(input);
        int count = 0;
        boolean stopwordfound = false;
        
        while(m.find() == true) {
            String word = m.group();
            count++;

            if (stopwordfound == false) {
                if (word.equals(stopword)) {
                    stopwordfound = true;
                    break;
                }
            }
        }
        if (count < 5) {
            throw new TooSmallText("Only found " + count + " words.");
        }

        if (target != null && !foundStop) {
            throw new InvalidStopwordException("Couldn't find stopword: " + stopword);
        }

        return count;
    }
    public static StringBuffer processFile(String y) throws EmptyFileException{
        String currentPath = y;

        while (true) {
            File f = new File(currentPath);
            Scanner fileScanner = new Scanner(f);
            try {
                StringBuffer result = new StringBuffer();

                while (fileScanner.hasNextLine()) {
                    String line = fileScanner.nextLine();
                    result.append(line);
                }

                if (result.length() == 0) {
                    throw new EmptyFileException(currentPath + " was empty");
                }

                return result;

            } catch (FileNotFoundException e) {
                System.out.println("File not found. Enter a valid filename:");
                currentPath = SCANNER.nextLine();
            }
        }
    }

    public static void main(String[] args) {

        

    }
}