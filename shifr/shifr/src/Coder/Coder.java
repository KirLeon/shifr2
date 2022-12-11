package Coder;

import Input.Alphabet;
import Input.Key;
import Input.Message;

import javax.crypto.spec.PSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class Coder {
    public static ArrayList<Integer> toInt(Message m) {
        ArrayList<Integer> messageInt = new ArrayList<>(40);
        char[] char_arr = m.getMessage().toCharArray();
        for (char c : char_arr) {
            messageInt.add(Alphabet.alphabet.indexOf(c));
//            System.out.println(c);
        }
        for (int i : messageInt) {
            System.out.print(i + " ");
        }
        return messageInt;
    }

    public static ArrayList<Integer> toInt(Key k) {
        ArrayList<Integer> keyInt = new ArrayList<>(40);
        char[] char_arr = k.getKey().toCharArray();
        for (char c : char_arr) {
            keyInt.add(Alphabet.alphabet.indexOf(c));
//            System.out.println(c);
        }
        for (int i : keyInt) {
            System.out.println(i + " ");
        }
        return keyInt;
    }

    public static void full_matr_key(Key k) {
        System.out.println("Заполняем матрицу ключей");
        int index = 0;
        for (int i = 0; i < k.getRazryadnost(); i++) {
            for (int j = 0; j < k.getRazryadnost(); j++) {
                k.put_key_matr(i, j, k.get_from_key_ints(index));
                System.out.print(k.get_from_key_matr(i, j) + " ");
                index++;
            }
            System.out.println();
        }
    }

    //разбиение на n-блоки
    public static void divide_message_to_blocks(Message m, Key k) {

        //переменные для деления сообщения на n-блоки
        int length = m.getMessageLength();
        int razryadnost = k.getRazryadnost();
        int plus_probels = length % razryadnost;
        int n = length / razryadnost;
        int index = 0;
        if (plus_probels > 0) n++;

        //инициализация места в памяти под массив
        m.init_n_block(n, razryadnost);
        System.out.println();
        System.out.println(n + "-n " + razryadnost + "-r" + length + "-l" + plus_probels);

        //заполнение массива цифрами, соответствующими символам в алфавите
        OUTER:
        for (int i = 0; i < n; i++) {
            for (int r = 0; r < razryadnost; r++) {
                if (index == length) {
                    break OUTER;
                }
                m.put_to_n_block(i, r, m.get_from_message_ints(index));
                index++;
            }
        }

        m.setNbamount(n);

        //если остались пробелы, заполнение последнего n-блока необходимым кол-вом пробелов
        if (plus_probels > 0) {
            int suda = razryadnost - plus_probels;
            for (int a = 0; a < plus_probels; a++) {
                m.put_to_n_block(n - 1, suda, (char) 32);
                suda++;
            }
        }

        //вывод на экран массива
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < razryadnost; j++) {
                System.out.print(m.get_from_n_block(i, j) + " ");
            }
            System.out.println();
        }
    }

    public static int extended_gcd(int a, int b, AtomicInteger x, AtomicInteger y)
    {
        if (a == 0)
        {
            x.set(0);
            y.set(1);
            return b;
        }

        AtomicInteger _x = new AtomicInteger(), _y = new AtomicInteger();
        int gcd = extended_gcd(b % a, a, _x, _y);

        x.set(_y.get() - (b/a) * _x.get());
        y.set(_x.get());

        return gcd;
    }

    public static void calculate_output_matr(Message m, Key k) {
        ArrayList<Integer> output = new ArrayList<>();
        int[][] matrix = k.getKey_ints_matr();
        int n = m.getNbamount();
        int r = k.getRazryadnost();
        for (int i = 0; i < n; i++) {
            int[] current_block = m.get_one_block(i);
            for (int a = 0; a < r; a++) {
                int result = 0;
                for (int b = 0; b < r; b++) {
                    result += current_block[b] * matrix[b][a];
                }
                output.add(result);
            }
        }
        System.out.println(output);
        for (int l = 0; l < output.size(); l++) {
            output.set(l, output.get(l) % 37);
        }
        System.out.println(output);
        System.out.println("Результат шифровки: ");
        for (int i : output) {
            System.out.print(Alphabet.alphabet.get(i) + " ");
        }
    }

    // input ->
    public static void shift_to_n_blocks_to_matr(String s, Key k) {
        int length = s.length();
        int razryad = k.getRazryadnost();
        ArrayList<Integer> arr = new ArrayList<>(length);
        for (int i = 0; i < s.length(); i++) {
            arr.add(Alphabet.alphabet.indexOf(s.charAt(i))); //char -> int
        }
        for (int i = length-1; i>0; i--){
            if(arr.get(i) == 35){
                arr.remove(i);
                length--;
            }
            else break;
        }
        int plus_prob = length % razryad;
        int n = plus_prob == 0 ? length / razryad : (length / razryad) + 1;
        Message message = new Message(n, razryad);
        int[][] new_matr = new int[n][razryad];
        int index = 0;
        for(int i = 0; i < n; i++){
            for(int j = 0; j<razryad; j++){
                message.put_to_n_block(i, j, arr.get(index));
                System.out.println(arr.get(index));
                index++;
            }
        }
    }

    public static void find_det(Key k){
        switch (k.getRazryadnost()){
            case(1):
                k.setDet(k.get_from_key_ints(0));
                break;
            case (2):
                k.setDet(k.get_from_key_matr(0,0)*k.get_from_key_matr(1,1) - k.get_from_key_matr(0,1)*k.get_from_key_matr(1,0));
                break;
            case (3):
                k.setDet(((k.get_from_key_matr(0,0)*k.get_from_key_matr(1,1)*k.get_from_key_matr(2,2))
                        + (k.get_from_key_matr(1,0)*k.get_from_key_matr(2,1)*k.get_from_key_matr(0,2))
                        + (k.get_from_key_matr(0,1)*k.get_from_key_matr(1,2)*k.get_from_key_matr(2,0))) -
                        ((k.get_from_key_matr(0,2)*k.get_from_key_matr(1,1)*k.get_from_key_matr(2,0))
                        + (k.get_from_key_matr(1,2)*k.get_from_key_matr(2,1)*k.get_from_key_matr(0,0))
                        + (k.get_from_key_matr(0,1)*k.get_from_key_matr(1,0)*k.get_from_key_matr(2,2))));
                break;
        }
        System.out.println(k.getDet());
    }

    public static void matrix_dop(Key k){
        int razr = k.getRazryadnost();
        int[][] matr_dop = new int[razr][razr];
        if(razr == 2){
            int a00 = k.get_from_key_matr(0, 0);
            int a01 = k.get_from_key_matr(0, 1);
            int a10 = k.get_from_key_matr(1, 0);
            int a11 = k.get_from_key_matr(1, 1);
            matr_dop[0][0] = a11;
            matr_dop[0][1] = a10*-1;
            matr_dop[1][0] = a01*-1;
            matr_dop[1][1] = a00;
            k.put_key_matr(0, 0, a11);
            k.put_key_matr(0, 1, a10*-1);
            k.put_key_matr(1, 0, a01*-1);
            k.put_key_matr(1, 1, a00);
        }
        else if(razr == 3){
            int a00 = k.get_from_key_matr(0, 0);
            int a01 = k.get_from_key_matr(0, 1);
            int a02 = k.get_from_key_matr(0, 2);
            int a10 = k.get_from_key_matr(1, 0);
            int a11 = k.get_from_key_matr(1, 1);
            int a12 = k.get_from_key_matr(1, 2);
            int a20 = k.get_from_key_matr(2, 0);
            int a21 = k.get_from_key_matr(2, 1);
            int a22 = k.get_from_key_matr(2, 2);
            k.put_key_matr(0, 0, (a11*a22 - a21*a12));
            k.put_key_matr(0, 1, (a10*a22 - a12*a20)*-1);
            k.put_key_matr(0, 2, (a10*a21 - a11*a20));
            k.put_key_matr(1, 0, (a01*a22 - a02*a21)*-1);
            k.put_key_matr(1, 1, (a00*a22 - a02*a20));
            k.put_key_matr(1, 2, (a00*a21 - a01*a20)*-1);
            k.put_key_matr(2, 0, (a01*a12 - a02*a11));
            k.put_key_matr(2, 1, (a00*a12 - a02*a10)*-1);
            k.put_key_matr(2, 2, (a00*a11 - a01*a10));
            for(int i = 0; i<3; i++){
                for(int j = 0; j< 3; j++){
                    System.out.print(k.get_from_key_matr_dop(i, j) + " ");
                }
                System.out.println();
            }
        }
    }

    public static int reverse_element(Key k){
        AtomicInteger x = new AtomicInteger(), y = new AtomicInteger();
        extended_gcd(k.getDet(), 37, x, y);
        if(k.getDet()<0 && x.get()>0){
            return x.get();
        }
        else if(k.getDet()>0 && x.get()<0){
            return 37+x.get();
        }
        else if(k.getDet()>0 && x.get()>0){
            return x.get();
        }
        else if(k.getDet()<0 && x.get()<0){
            return x.get()*(-1);
        }
        else return 0;
    }

    public static void matr_alg_dop(Key k){

    }

    public static void main(String[] args) {
        Alphabet.full_alphabet();
        Key key1 = new Key();
        full_matr_key(key1);
        Message message1 = new Message();
        divide_message_to_blocks(message1, key1);
        calculate_output_matr(message1, key1);
        shift_to_n_blocks_to_matr("кбэьйц", key1);
        find_det(key1);
    }
}
