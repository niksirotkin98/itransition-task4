package com.gmail.niksirotkin98;

import de.vandermeer.asciitable.*;
import org.apache.commons.codec.binary.Hex;

public class Display {

    
    private String[] rules;

    private Controller control;
    
    private String line = "--------------";

    private String rulesTable = "Error: rule table not composed! Call \"composeRulesTable(...)\" method.";

    private final String[] RESULT_MESSAGES = {"Draw", "You win!", "Computer win!", "Error: unexpected result!"};
    private final String[] HELP_TABLE_RESULTS = {"Draw", "Win", "Lose", "Error: unexpected result!"};
    
    Display(Controller control, String[] args){
        this.rules = args;
        this.control = control;
        composeRulesTable();
    }
    
    public static void printErrorMessage(String message) {
        System.out.println("Error: " + message);
    }
    
    public void composeRulesTable() {
        AsciiTable at = new AsciiTable();
        String[] cols = new String[rules.length + 1];
        cols[0] = "Rule\\With";
        for(int i = 1; i < cols.length; i++){
            cols[i] = rules[i - 1];
        }
        at.addRule();
        at.addRow(cols);
        at.addRule();
        for (int i = 0; i < rules.length; i++)
        {
            cols = new String[rules.length + 1];
            cols[0] = rules[i];
            for (int j = 1; j < cols.length; j++)
            {
                int result = control.computeResult(i, j - 1);
                cols[j] = getHelpTableResult(result);
            }
            at.addRow(cols);
            at.addRule();
        }
        rulesTable = at.render();
    }
    
    public String getOptionMessage(int option){
        if( option == -2 ){
            return "exit";
        } else if( option == -1 ) {
            return "help";
        } else if( option > -1 && option < rules.length){
            return rules[option];
        } 
        return "unknown_option(" + option + ")";
    }
    
    public void inputOptions(){
        System.out.println("Available moves:");
        System.out.println("?: Help");
        System.out.println("0: Exit");
        for(int i = 0; i < rules.length; i++){
            System.out.printf("%d: %s\n", i + 1, rules[i]);
        }
        System.out.print("Enter your move:");
    }

    public void help(){
        printLine();
        System.out.println("Rules table:\nRead from row and compare with column");
        System.out.println(rulesTable);
    }

    public void printLine(){
        System.out.println(line);
    }

    public void printMove(String message, String move){
        System.out.println(message + ": " + move);
    }

    public void printResult(int result){
        printLine();
        System.out.println(getResultMessage(result));
    }

    public void printHMAC(byte[] hmac){
        System.out.println("--- New Round ---");
        System.out.println("HMAC: " + Hex.encodeHexString(hmac));
    }

    public void printHMACKey(byte[] key){
        printLine();
        System.out.println("HMAC key: " + Hex.encodeHexString(key));
        printLine();
    }

    private String getResultMessage(int result) {
        switch (result) 
        {
            case 0: 
                return RESULT_MESSAGES[0];
            case 1:
                return RESULT_MESSAGES[1];
            case -1:
                return RESULT_MESSAGES[2];
            default:
                return RESULT_MESSAGES[3];
        }
    }

    private String getHelpTableResult(int result) {
        switch (result) 
        {
            case 0: 
                return HELP_TABLE_RESULTS[0];
            case 1:
                return HELP_TABLE_RESULTS[1];
            case -1:
                return HELP_TABLE_RESULTS[2];
            default:
                return HELP_TABLE_RESULTS[3];
        }
    }
}
