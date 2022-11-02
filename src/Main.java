/*
This class has the user interface and graphics of the game
It uses the GameLogic class for anything related to the game logic
*/
import javax.swing.*;


public class Main {
    public static void main(String[] args) {

        GameLogic game = new GameLogic();

        //main game loop
        while (true){
            String guess = JOptionPane.showInputDialog("Please enter your guess");

            //check if input is valid
            if(game.is_valid_input(guess))
            {
                //check if guess is correct
                int[] results = game.calculate_result(guess);

                if (results[GameLogic.BULLSEYE_COUNT_INDEX] == GameLogic.NUM_LEN)
                {
                        JOptionPane.showMessageDialog(null,"Well done!\nthe number was " + game.get_number() +
                                "\nIt took you only " + game.get_guess_count() + " guesses!");
                    int choice = JOptionPane.showConfirmDialog(null, "\nWould like to start another game?");
                    if (choice!=JOptionPane.OK_OPTION)
                    {
                        JOptionPane.showMessageDialog(null, "It was fun while it lasted.\nSee you :)");
                        return;
                    }
                    else
                        game.restart_game();

                }
                else {
                    JOptionPane.showMessageDialog(null, game.get_history());
                }
            }
        }//end of main game loop

    }//end of main method

}//end class Main