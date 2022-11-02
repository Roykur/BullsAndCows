/*
This class has the logic of the game Bulls And Cows
It creates a random number of NUM_LEN digits with no repeating digits
It checks the validity of user guesses, returns error for invalid inputs, saves and holds user guesses history.
*/
import javax.swing.*;
import java.lang.Math;
import java.util.Arrays;

public class GameLogic {

    protected static final int NUM_LEN = 4; //length of the number to guess
    protected static final int BULLSEYE_COUNT_INDEX = 0; // bullseye count index in results array
    protected static final int HIT_COUNT_INDEX = 1; // hits count index in results array
    private int number; // the secret number
    private int guess_count; // how many guesses the user had guessed
    private boolean[] containing_digits; // the digits of this.number
    private String[] history; // list of string that representes the history of user guesses


    //empty constructor
    public GameLogic(){
        this.number = create_num();
        this.guess_count = 0;
        this.history = new String[0];
    }

    //create a new number to guess (the number is NUM_LEN long)
    private int create_num(){
        while (true){
            int range_min = (int)Math.pow(10, NUM_LEN-1);
            int range_max = (int)Math.pow(10, NUM_LEN);
            int temp_num = (int)(Math.random() *( range_max-range_min) + range_min);
            if(is_no_digits_repeats(temp_num)==0)
                return temp_num;
        }

    }//end of create_num method

    //get method for number property
    public int get_number(){
        return this.number;
    }

    //get mthod for count property
    public int get_guess_count(){
        return this.guess_count;
    }

    //gets an int as argument and returns the number od repeating digits in num
    private int is_no_digits_repeats(int num){

        int repetition_count = 0;

        boolean[] is_digit_repeats = new boolean[10];
        for(int j=0; j<NUM_LEN; j++)
        {
            int digit = num%10;
            num = num/10;

            for (int i = 0; i < 10; i++)
            {
                if(is_digit_repeats[digit])
                    repetition_count++;
            }
            is_digit_repeats[digit] = true;
        }
        if (this.containing_digits == null && repetition_count==0)
            this.containing_digits = Arrays.copyOf(is_digit_repeats, 10);
        return repetition_count;
    }


    // calculates how many hits and how many bullseye guess had compared to this.number
    public int[] calculate_result(String guess){
        //check bullseye
        int bullseye_count = 0;
        String str_num = Integer.toString(this.number);
        for(int i=0; i<NUM_LEN; i++)
        {
            if (Character.getNumericValue(guess.charAt(i)) == Character.getNumericValue(str_num.charAt(i)))
                bullseye_count++;
        }

        //check hits
        int hit_count = 0;
        for(int i=0; i<NUM_LEN; i++)
        {
            if (this.containing_digits[Character.getNumericValue(guess.charAt(i))])
                hit_count++;
        }
        hit_count = hit_count-bullseye_count;


        int[] results = new int[]{bullseye_count, hit_count};

        //results calculated - add to history
        this.guess_count++;
        add_to_history(guess, results);

        return results;
    }


    //return true if user_guess is a valid input. otherwise prints an error
    public boolean is_valid_input(String user_guess)
    {
        int intVal;
        //if empty
        if(user_guess == null || user_guess.equals(""))
        {
            JOptionPane.showMessageDialog(null, "the number you entered is empty");
            return false;
        }

        //if not correct length
        if (user_guess.length()!=NUM_LEN)
        {
            JOptionPane.showMessageDialog(null, "the number you entered is not " + NUM_LEN + " digits long");
            return false;
        }

        //if integer
        try{
            intVal = Integer.parseInt(user_guess);

            //if negative number
            if(intVal<0) {
                JOptionPane.showMessageDialog(null, "the number you entered is negative");
                return false;
            }

            //if it has repetitions
            if(is_no_digits_repeats(intVal) > 0) {
                JOptionPane.showMessageDialog(null, "the number must not have repeating digits");
                return false;
            }
        }
        catch (NumberFormatException exc){
            JOptionPane.showMessageDialog(null, "the string you entered is not a number");
            return false;
        }

        //guess is valid
        return true;
    }

    //appends user_guess to the guess history
    private void add_to_history(String user_guess, int[] results){
        this.history = Arrays.copyOf(this.history, this.history.length+1);
        this.history[this.history.length-1] = "Guess " + this.guess_count + ": " + user_guess + " "
                + results[BULLSEYE_COUNT_INDEX] + " Bullseye " + results[HIT_COUNT_INDEX] + " Hits";
    }

    //print all guess history
    public void print_history(){
        for (String s : this.history) System.out.println(s);
    }

    //returns all guess history as a String
    public String get_history(){
        StringBuilder all_history = new StringBuilder();
        for (String s : this.history) all_history.append(s).append("\n");
        return all_history.toString();
    }



    //restarts the Game objects to new values
    public void restart_game(){
        this.history = new String[0];
        this.containing_digits = null;
        this.guess_count = 0;
        this.number = create_num();
    }

    //toString
    public String toString(){
        return Integer.toString(number);
    }


}
