import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Integer> symtable;
    public SymbolTable(){
        symtable = new HashMap<>();
        symtable.put("SP",0);
        symtable.put("LCL",1);
        symtable.put("ARG", 2);
        symtable.put("THIS", 3);
        symtable.put ("THAT",4);
        symtable.put("SCREEN",0x4000);
        symtable.put("KBD",0x6000);

        for(int i=0; i <= 15; i++){
            symtable.put("R"+i, i);
        }
    }
    public void addEntry(String symbol, int address){
        symtable.put(symbol, address);
    }
    public boolean contains(String symbol){
        return symtable.containsKey(symbol);
    }
    public int getAddress(String symbol){
        return symtable.get(symbol);
    }
}
