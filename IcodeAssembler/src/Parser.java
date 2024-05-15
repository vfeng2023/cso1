/**
 * Java Parser
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;

public class Parser {
    /**
     * Takes filename as parameter and initializes the reading file
     * @param filename
     */
    public static final int LABEL = 0;
    public static final int COMMAND = 1;
    private ArrayList<String> myCommands;
    private int programcounter = -1;
    private Pattern validC;
    private Pattern validJ;
    String currentCommand = "";
    ArrayList<String> tokenized;
    public Parser(String filename){
        myCommands = new ArrayList<>();
        Scanner sc = null;
        validJ = Pattern.compile("[(]\\w+[)]");
        //validC = Pattern.compile("\\w+[=]?.+[;]?\\w+");
        try{
            sc = new Scanner(new File(filename));
        }catch(FileNotFoundException e){

            System.out.println("Oops, file "+filename + " was not found.");
            System.exit(0);
        }

        while(sc.hasNext()){
            //remove comments and whitespace
            String cmd = sc.nextLine();
            int commentloc = cmd.indexOf("//");
            if(commentloc >= 0){
                cmd = cmd.substring(0, commentloc);
            }

            cmd = cmd.strip();
            //cmd = cmd.replaceAll("\\s+","");
            if(!cmd.isEmpty()){
                myCommands.add(cmd);
            }
        }
        //System.out.println(myCommands.toString());
        for(String t: myCommands){
            System.out.println(t);
        }
    }

    public boolean hasMoreCommands(){

        if(programcounter+1 >= myCommands.size()) return false;
        return true;
    }

    /**
     * Reads the next command from
     * the input and makes it the current
     * command. Should be called only
     * if hasMoreCommands() is true.
     * Initially there is no current command.
     * @return
     */
    public void advance(){
        if(hasMoreCommands()){
            programcounter ++;
            currentCommand = myCommands.get(programcounter);
        }
    }

    /**
     * splits next line into it's component pieces
     */

    public void tokenize(){
        String[] result = currentCommand.split("\\s+",2);
        tokenized = new ArrayList<>();
        tokenized.add(result[0]);
        if(result.length > 1){
            String[] args = result[1].split(",\\s*");
            tokenized.addAll(Arrays.asList(args));
        }
    }

    /**
     * Gets the directive part of the command. If there is none, return null,
     * Commands come in the formate of DIRECTIVE a, b, [+c]
     */
    public String getDirective(){
        return tokenized.get(0);
//        int end = currentCommand.indexOf(' ');
//        int commas = currentCommand.indexOf(',');
//        if(end < 0){
//            if(commas < 0){
//                return null;
//            }
//            else{
//                end = currentCommand.length();
//            }
//        }
//        return currentCommand.substring(0, end);
//        if(end < 0){
//            return null
//        }
    }

    /**
     * Returns argument a
     * @return
     */
    public String getA(){
        if(1 < tokenized.size()){
            return tokenized.get(1);
        }else{
            return null;
        }
    }

    /**
     *Return argument b of directive
     */
    public String getB(){
        if(2 < tokenized.size()){
            return tokenized.get(2);
        }
        return null;
    }

    /**
     * Get optional third argument
     */
    public String getC(){
        if(3 < tokenized.size()){
            return tokenized.get(3);
        }
        return null;
    }

    /**
     * Extracts a label name. only call if the command is a label
     */
    public String getLabel(){
        return currentCommand.substring(1, currentCommand.length()-1);
    }
    public void reset(){
        //bring program counter to beginning
        programcounter = -1;
    }

    public int getCommandType(){
        if(currentCommand.charAt(0)=='(' && currentCommand.charAt(currentCommand.length()-1) == ')'){
            return LABEL;
        }
        return COMMAND;
    }


}
