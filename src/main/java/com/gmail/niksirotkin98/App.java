package com.gmail.niksirotkin98;

import java.util.HashSet;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class App 
{
    public static void main( String[] args )
    {
        App app = new App(args);
        app.start();
    }

    private final String[] args;

    private Display display;

    private Controller control;

    private int playerInput = -1;

    private String input = null;

    private RuleNode player;

    private RuleNode computer;

    private boolean isExit = false;

    private Secret secret;

    private Scanner sc;

    private Random rand = new Random();

    App(String[] args){
        this.args = args;
        checkArgs();
    }

    void start(){
        if(isExit) 
            return;
        control = new Controller(args);
        display = new Display(control, args);
        sc = new Scanner(System.in);
        do{
            update();
        }
        while (!this.isExit);
    }

    void update(){
        computerInput();
        displayHMAC();
        playerInput();
    }

    void playerInput(){
        display.printLine();
        display.inputOptions();
        getPlayerInput();
        optionsControl();
    }

    void computerInput(){
        computer = control.getRule(rand.nextInt(args.length));
        secret = new Secret(computer.getKey());
    }

    void optionsControl(){
        if( playerInput == 0 ){
            exit();
        } else if( playerInput == -1 ){
            display.help();
            pressAnyKeyToContinue();
            playerInput();
        } else if( playerInput > 0 && playerInput < args.length + 1){
            player = control.getRule(playerInput - 1);
            result();
            pressAnyKeyToContinue();
        } else {
            invalidInput();
            playerInput();
        }
    }

    void result(){
        displayMoves();
        displayResult();
        displayHMACKey();
    }

    void displayHMAC(){
        display.printHMAC(secret.getHMAC());
    }

    void displayHMACKey(){
        display.printHMACKey(secret.getSecretKey());
    }

    void displayMoves(){
        display.printMove("Your move", player.getKey());
        display.printMove("Computer move", computer.getKey());
    }
    void displayResult(){
        int result = control.computeResult(player.getPosition(), computer.getPosition());
        display.printResult(result);
    }

    void getPlayerInput(){
        input = sc.next();
        try{
            playerInput = Integer.parseInt(input);
        } catch(NumberFormatException e){
            if(input.contains("help") || input.contains("?")){
                playerInput = -1;
            } else if(input.contains("exit")){
                playerInput = 0;
            } else {
                playerInput = -999;
            }
        }
    }

    void invalidInput(){
        Display.printErrorMessage("invalid input: " + input + "(" + playerInput + ")");
    }

    void exit(){
        isExit = true;
        sc.close();
    }

    void checkArgs(){
        isExit =  !(checkArgsCount() && checkArgsOdd() && checkArgsUniq());
    }

    boolean checkArgsCount(){
        if (args.length < 3) 
        {
            Display.printErrorMessage("not enough parameters. There must be => 3 and odd parameters. Example: \"rock paper scissors\".");
            return false;
        } 
        return true;
    }

    boolean checkArgsOdd(){
        if (args.length % 2 == 0)
        {
            Display.printErrorMessage("the number of parameters must not be even. Example: \"1 2 3 4 5\".");
            return false;
        }
        return true;
    }

    boolean checkArgsUniq(){
        Set<String> uniqArgs = new HashSet<>();
        for (String a : args) 
        {
            if (!uniqArgs.add(a)) 
            {
                Display.printErrorMessage("there should be no duplicate parameters(\"" + a + "\"). Example: \"Rock Paper Scissors Lizard Spock\".");
                return false;
            }
        }
        return true;
    }

    void pressAnyKeyToContinue()
    { 
        System.out.println("Press Enter key to continue...");
        try
        {
            System.in.read();
        }  
        catch(Exception e)
        {}  
    }
}
