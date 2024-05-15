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
    public static final int A_COMMAND = 0;
    public static final int C_COMMAND = 1;
    public static final int L_COMMAND = 2;
    private ArrayList<String> myCommands;
    private int programcounter = -1;
    private Pattern validC;
    private Pattern validJ;
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
            cmd = cmd.replaceAll("\\s+","");
            if(!cmd.isEmpty()){
                myCommands.add(cmd);
            }
        }
        //System.out.println(myCommands.toString());
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
        }
    }

    /**
     * Returns the type of the current
     * command:
     * m A_COMMAND for @Xxx where
     * Xxx is either a symbol or a
     * decimal number
     * m C_COMMAND for
     * dest=comp;jump
     * m L_COMMAND (actually, pseudo command) for (Xxx) where Xxx
     * is a symbol.
     * @return
     */
    public int commandType(){
        String cmd = myCommands.get(programcounter);
        if(cmd.charAt(0) == '@'){
            return A_COMMAND;

        }else if(cmd.charAt(0) == '(' && cmd.charAt(cmd.length()-1)==')'){
            return L_COMMAND;
        }else{
            return C_COMMAND;
        }
    }
    private String getCommand(){
        return myCommands.get(programcounter);
    }
    /**
     *  Returns the symbol or decimal
     * Xxx of the current command
     * @Xxx or (Xxx). Should be called
     * only when commandType() is
     * A_COMMAND or L_COMMAND.
     * @return
     */
    public String symbol(){

        if(commandType()==A_COMMAND){
            return getCommand().substring(1);
        }else{
            return getCommand().substring(1, getCommand().length()-1);
        }
    }

    /**
     *  Returns the dest mnemonic in
     * the current C-command (8 possi￾bilities). Should be called only
     * when commandType() is C_COMMAND
     * @return
     */
    public String dest(){
        String cmd = getCommand();
        int eqindx = cmd.indexOf('=');
        if(eqindx < 0){
            return "null";
        }else{
            return cmd.substring(0, eqindx);
        }
    }
    /**
     * Returns the comp mnemonic in
     * the current C-command (28 possibilities). Should be called only
     * when commandType() is
     * C_COMMAND.
     */
    public String comp(){
        String cmd = getCommand();
        int start = cmd.indexOf('=');
        int end = cmd.indexOf(';');
        if(start == 0){
            start = -1;
        }
        if(end < 0){
            end = cmd.length();
        }
        return cmd.substring(start+1, end);

    }

    /**
     * Returns the jump mnemonic in
     * the current C-command (8 possibilities). Should be called only
     * when commandType() is
     * C_COMMAND.
     */
    public String jump(){
        String cmd = getCommand();
        int start = cmd.indexOf(';');
        if(start < 0){
            return "null";
        }
        return cmd.substring(start+1);
    }

    public void reset(){
        //bring program counter to beginning
        programcounter = -1;
    }


}
