import java.util.*;

public class Code {
    private static Map<String, String> keywordtocode = Map.ofEntries(
            Map.entry("RD", "00"),
            Map.entry("PLUS", "01"),
            Map.entry("AND", "10"),
            Map.entry("RDA","11"),
            Map.entry("BITNOT", "00"),
            Map.entry("NEG", "01"),
            Map.entry("LGNOT", "10"),
            Map.entry("SAVEPC","11")
    );

    private static Map<String, String> cmdtocode = Map.ofEntries(
            //for when a=0
            Map.entry("LOAD", "000"),
            Map.entry("ADD", "001"),
            Map.entry("AND", "010"),
            Map.entry("LOADM","011"),
            Map.entry("WRITE", "100"),
            Map.entry("OP","101"),
            Map.entry("MOVE","110"),
            Map.entry("JLE", "111"),
            Map.entry("HALT", "10000000")
    );


    /**
     * Returns binary string of a particular command
     * @param mem
     * @return
     */
    public static String comp(String mem){
        if(!cmdtocode.containsKey(mem)){
            throw new IllegalArgumentException("Command "+mem + " not found");
        }
        else{
            return cmdtocode.get(mem);
        }
    }

    /**
     * returns binary string representing a
     * @param mem
     * @return
     */
    public static String keyword(String mem){
        if(!keywordtocode.containsKey(mem)){
            throw new IllegalArgumentException("Symbol "+mem + "not found");
        }
        return keywordtocode.get(mem);
    }

}
