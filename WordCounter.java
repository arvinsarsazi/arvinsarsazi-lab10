public class WordCounter{

    static InputStream sysInBackup;
    public WordCounter(){
        
    }
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

        return null;
    }

    public static void main(String[] args) {

    }
}