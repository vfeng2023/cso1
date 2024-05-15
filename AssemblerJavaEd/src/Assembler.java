import java.util.*;
import java.io.*;
public class Assembler {
    public static void main(String[] args) {
        //compiles given file
        String filename = args[0].strip();
        Parser myparser = new Parser(filename);
        Code converter = new Code();
        PrintWriter pw=null;
        SymbolTable symtable = new SymbolTable();
        //first pass
        int cmdcounter = 0;
        int varAddress = 16;
        while(myparser.hasMoreCommands()){
            myparser.advance();
            int cmdType = myparser.commandType();
            if(cmdType==Parser.L_COMMAND) {
                String name = myparser.symbol();
                if (!symtable.contains(name)) {
                    symtable.addEntry(name, cmdcounter);
                }
            }else{
                cmdcounter ++;
            }
//            }else if(cmdType==Parser.A_COMMAND){
//                String symbol = myparser.symbol();
//                if(!symtable.contains(symbol)){
//                    symtable.addEntry(symbol, varAddress);
//                    varAddress += 1;
//                }
//                cmdcounter ++;
//            }
        }

        //second pass
        //createput stream
        try {
            pw = new PrintWriter(new File(filename.substring(0, filename.indexOf('.')) + ".hack"));
        }catch (IOException ignored){
            System.out.println("Oops, file could not be created");
            System.exit(0);
        }
        myparser.reset();
        //parse
        while(myparser.hasMoreCommands())
        {
            //build command
            myparser.advance();
            //if(myparser.hasMoreCommands()==false) break;
            //write to file
            int commandType = myparser.commandType();
            if(commandType==Parser.A_COMMAND){
                StringBuilder bitstr = new StringBuilder("0");
                String mem = myparser.symbol();
                try{
                    int address = Integer.parseInt(mem);
                    bitstr.append(String.format("%15s", Integer.toBinaryString(address)).replace(" ", "0"));
                }catch(NumberFormatException e){
                    if(!symtable.contains(mem)){
                        symtable.addEntry(mem, varAddress);
                        varAddress ++;
                    }

                    bitstr.append(String.format("%15s", Integer.toBinaryString(symtable.getAddress(mem))).replace(" ", "0"));

                }

                pw.println(bitstr.toString());
            }
            else if(commandType==Parser.C_COMMAND){
                String dest = myparser.dest();
                String cmp = myparser.comp();
                String jump = myparser.jump();
                StringBuilder bitstr = new StringBuilder("111");
                bitstr.append(String.format("%7s", Integer.toBinaryString(converter.comp(cmp))).replace(" ","0"));
                bitstr.append(String.format("%3s", Integer.toBinaryString(converter.dest(dest))).replace(" ", "0"));
                bitstr.append(String.format("%3s",Integer.toBinaryString(converter.jump(jump))).replace(" ", "0"));
                pw.println(bitstr.toString());

            }
        }
        pw.close();
    }
}