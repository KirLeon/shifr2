package Input;

import java.util.ArrayList;

public class Alphabet {
    public static ArrayList<Character> alphabet = new ArrayList<>(40);
    public static void full_alphabet(){
        for(int i = 1072; i<1104; i++){
            if (i == 1078){
                alphabet.add('ё');
                alphabet.add((char) i);
                continue;
            }
            alphabet.add((char) i);
        }
        alphabet.add((char) 46);
        alphabet.add((char) 44);
        alphabet.add((char) 32);
        alphabet.add((char) 63);
        int n = 0;
        for(char c: alphabet){
            System.out.print(n + "" + c + " ");
            n++;
        }
        System.out.println();
    }

}
