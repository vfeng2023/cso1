// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class IcodeAssembler {
    public static void main(String[] args) {
        Parser myparser = new Parser(args[0]); //name should be in format filename.icode
        PrintWriter pw = null;
        try{
            pw = new PrintWriter(new File(args[0].substring(0, args[0].length()-6)+".binary"));
        }catch (IOException e){
            System.out.println("Ooops, could not create binary output file");
            System.exit(0);
        }
        //first pass to encode the labeltable
        int instructioncount = 0;
        SymbolTable symtable = new SymbolTable();
        while(myparser.hasMoreCommands()){
            myparser.advance();
            if(myparser.getCommandType()==Parser.COMMAND){
                myparser.tokenize();
                if(myparser.getC()!=null)
                    instructioncount += 1;
                instructioncount += 1;
            }else{
                String label = myparser.getLabel();
                if(!symtable.contains(label)){
                    symtable.addEntry(label, instructioncount);
                }
            }
        }
        myparser.reset();
        //second pass to build the assembly code
        while(myparser.hasMoreCommands()){
            myparser.advance();
            if(myparser.getCommandType()==Parser.LABEL) continue;
            myparser.tokenize();
            String directive = myparser.getDirective();
            String a = myparser.getA();
            String b = myparser.getB();
            String c = myparser.getC();

            StringBuilder bitstr = new StringBuilder("0");

            if(directive!=null){
                bitstr.append(Code.comp(directive));
            }
            if (a != null) {

                try{
                    int aindex = Integer.parseInt(a);
                    bitstr.append(String.format("%2s", Integer.toBinaryString(aindex)).replace(' ','0'));
                }catch(NumberFormatException e){
                    System.out.println("Oops, "+ a +" is not a valid address value");
                }
            }
            if(b!=null){
                if(symtable.contains(b)){
                    throw new IllegalArgumentException("Oops! Labels can only be called as the c-arg");

                }else{
                    int bindex;
                    try{
                        bindex = Integer.parseInt(b);
                        bitstr.append(String.format("%2s", Integer.toBinaryString(bindex)).replace(' ','0'));
                    }catch(NumberFormatException e){
                        bitstr.append(Code.keyword(b));
                    }
                }

            }

            int hexcode = Integer.parseInt(bitstr.toString(), 2);
            pw.print(Integer.toString(hexcode, 16)+" ");

            if(c!=null){
                if(symtable.contains(c)){
                    int loc = symtable.getAddress(c);
                    pw.print(Integer.toString(loc, 16)+" ");

                }
                else if(c.length() >= 2 && c.substring(0,2).equalsIgnoreCase("0x")){
                    int hex = Integer.parseInt(c.substring(2), 16);
                    pw.print(Integer.toString(hex, 16)+" ");
                }else{

                    int decimal = Integer.parseInt(c);
                    int sign = 1;
                    if(decimal < 0){
                        sign = -1;
                    }
                    //convert  to twos commplement
                    if(sign < 0){
                        decimal = decimal*-1;
                        decimal = (~decimal + 1)&0xFF;
                        System.out.println(decimal);
                    }

                    pw.print(String.format("%2s",Integer.toString(decimal, 16)).replace(' ', '0')+" ");
                }

            }
            pw.println();
        }
        pw.close();
    }
}