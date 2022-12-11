package Input;

import Coder.Coder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Message {
    private String message; //сообщение
    private ArrayList<Integer> message_ints; //список номеров букв
    private int[][] n_blocks_arr; //Массив номеров букв (см. выше)
    private ArrayList<Integer> output_ints;
    private int nbamount;

    //конструктор вызывает метод для получения письма, затем обращается к методу класса Coder для конвертации букв в числа
    public Message() {
        message = readMessage();
        System.out.println(message);
        message_ints = Coder.toInt(this);
        output_ints = new ArrayList<>(message_ints.size());
    }

    public Message(int n, int razryad){
        init_n_block(n, razryad);
    }

    //получение сообщения строкой
    public String getMessage() {
        return message;
    }

    //получение длины сообщения
    public int getMessageLength() {
        return message.length();
    }

    //инициализация массива кол-вом n блоков, каждый вместительностью равной разрядности матрицы
    public void init_n_block(int n, int razryadnost) {
        n_blocks_arr = new int[n][razryadnost];
    }

    //помещение элемента в n-блок
    public void put_to_n_block(int n, int r, int num) {
        n_blocks_arr[n][r] = num;
    }

    //получение элемента из n-блока
    public int get_from_n_block(int i, int j) {
        return n_blocks_arr[i][j];
    }

    //получение элемента из массива чисел
    public int get_from_message_ints(int index) {
        return message_ints.get(index);
    }

    public int getNbamount() {
        return nbamount;
    }

    public void setNbamount(int nbamount) {
        this.nbamount = nbamount;
    }

    public int[] get_one_block(int index){
        return n_blocks_arr[index];
    }

    //считывание сообщения от пользователя
    public String readMessage() {
        System.out.println("Введите сообщение");
        try {
            BufferedReader reader1 = new BufferedReader(new InputStreamReader(System.in));
            String s = reader1.readLine().toLowerCase();
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

}
