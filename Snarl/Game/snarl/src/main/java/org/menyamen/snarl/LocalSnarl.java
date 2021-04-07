package org.menyamen.snarl;

import java.util.List;
import java.util.Scanner;

import org.menyamen.snarl.characters.PlayerImpl;
import org.menyamen.snarl.layout.Level;
import org.menyamen.snarl.manage.GameManager;

import static org.menyamen.snarl.util.TestingUtil.processLevelFile;

public class LocalSnarl {

    public static void main(String[] args) {

        String levelsFile ="snarl.levels";
        int playerCount = 1;
        int startLevel = 1;
        boolean observe = false;

        // Command Line Args
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("--levels")) {
                levelsFile = args[i + 1];
            }
            else if (args[i].equals("--players")) {
                playerCount = Integer.parseInt(args[i + 1]);
                if (observe && playerCount != 1) {
                    System.out.println("Cannot have observer and more than 1 players");
                    return;
                }
            }
            else if (args[i].equals("--start")) {
                startLevel = Integer.parseInt(args[i + 1]);
            }
            else if(args[i].equals("--observer")) {
                observe = true;
                if (playerCount != 1) {
                    System.out.println("Cannot have observer and more than 1 players");
                    return;
                }
            }
        }

        List<Level> levels = processLevelFile(levelsFile);

        GameManager gameManager = new GameManager(startLevel - 1, levels);

        Scanner input = new Scanner(System.in);

        for (int i = 0; i < playerCount; i++) {
            System.out.print("Enter Player " + i + " Name: ");
            String name = input.nextLine();
            gameManager.registerPlayer(new PlayerImpl(name));
        }

        gameManager.startGame(input);
        input.close();

        System.out.println("test");

        // // Prompt Player Names
        // for (int i = 0; i < playerCount; i++) {

        // }



    }
}