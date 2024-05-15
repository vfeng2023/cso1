import java.util.HashMap;
import java.util.Map;

public class SymbolTable {
    private Map<String, Integer> symtable;
    public SymbolTable(){
        symtable = new HashMap<>();
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