import java.util.*;

public class Code {
    private Map<String, Integer> memtocode;
    private Map<String, Integer> jumptocode;

    private Map<String, Integer> cmdtocode;
    public Code(){
        memtocode = Map.ofEntries(
                Map.entry("null", 0),
                Map.entry("M", 1),
                Map.entry("D", 2),
                Map.entry("MD", 3),
                Map.entry("A",4),
                Map.entry("AM",5),
                Map.entry("AD",6),
                Map.entry("AMD",7)

        );

        jumptocode = Map.ofEntries(
                Map.entry("null", 0),
                Map.entry("JGT", 1),
                Map.entry("JEQ", 2),
                Map.entry("JGE", 3),
                Map.entry("JLT",4),
                Map.entry("JNE",5),
                Map.entry("JLE",6),
                Map.entry("JMP",7)

        );

        cmdtocode = Map.ofEntries(
                //for when a=0
              Map.entry("0", 42),
              Map.entry("1", 63),
              Map.entry("-1",Integer.parseInt("111010",2)),
                Map.entry("D",Integer.parseInt("001100",2)),
                Map.entry("A",Integer.parseInt("110000",2)),
                Map.entry("!D",Integer.parseInt("001101",2)),
                Map.entry("!A",Integer.parseInt("110001",2)),
                Map.entry("-D",Integer.parseInt("001111",2)),
                Map.entry("-A",Integer.parseInt("110011",2)),
                Map.entry("D+1",Integer.parseInt("011111",2)),
                Map.entry("A+1",Integer.parseInt("110111",2)),
                Map.entry("D-1", Integer.parseInt("001110",2)),
                Map.entry("A-1", Integer.parseInt("110010",2)),
                Map.entry("D+A",Integer.parseInt("000010",2)),
                Map.entry("D-A",Integer.parseInt("010011",2)),
                Map.entry("A-D",Integer.parseInt("000111",2)),
                Map.entry("D&A",Integer.parseInt("000000",2)),
                Map.entry("D|A",Integer.parseInt("010101",2)),
                //for when a=1
                Map.entry("M",Integer.parseInt("1110000",2)),
                Map.entry("!M",Integer.parseInt("1110001",2)),
                Map.entry("-M",Integer.parseInt("1110011",2)),
                Map.entry("M+1",Integer.parseInt("1110111",2)),
                Map.entry("M-1", Integer.parseInt("1110010",2)),
                Map.entry("D+M", Integer.parseInt("1000010",2)),
                Map.entry("D-M",Integer.parseInt("1010011",2)),
                Map.entry("M-D",Integer.parseInt("1000111",2)),
                Map.entry("D&M",Integer.parseInt("1000000",2)),
                Map.entry("D|M",Integer.parseInt("1010101",2))
        );


    }

    public int dest(String mem){
        if(!memtocode.containsKey(mem)){
            throw new IllegalArgumentException("Command "+mem + "not found");
        }
        else{
            return memtocode.get(mem);
        }
    }

    public int comp(String mem){
        if(!cmdtocode.containsKey(mem)){
            throw new IllegalArgumentException("Command "+mem + "not found");
        }
        else{
            return cmdtocode.get(mem);
        }
    }

    public int jump(String mem){
        if(!jumptocode.containsKey(mem)){
            throw new IllegalArgumentException("Command "+mem + "not found");
        }
        else{
            return jumptocode.get(mem);
        }
    }





}
