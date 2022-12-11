package Input;

import Coder.Coder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Key {

    private String key; //собственно ключ
    private ArrayList<Integer> key_ints; //хранит список значений букв
    private int[][] key_ints_matr; //а он хранит их массивом
    private int razryadnost; //а это разрядность матрицы (см. выше)
    private int det;
    private int[][] key_ints_matr_dop;

    //Конструктор вызывает метод для получения ключевого слова, затем обращается к методу класса Coder
    // для конвертации букв в числа. Также считает разрядность матрицы и инициализирует массив
    public Key() {
        key = readKey();
        System.out.println(getKey());
        key_ints = Coder.toInt(this);
        razryadnost = (int) Math.sqrt(key.length());
        System.out.println("Размерность матрицы: " + razryadnost + "x" + razryadnost);
        key_ints_matr = new int[razryadnost][razryadnost];
        key_ints_matr_dop = new int[razryadnost][razryadnost];
    }

    public int getDet() {
        return det;
    }

    public void setDet(int det) {
        this.det = det;
    }

    //помещение элемента в матрицу по индексу
    public void put_key_matr(int i, int j, int num) {
        key_ints_matr[i][j] = num;
    }

    //помещение элемента в матрицу по индексу
    public void put_key_matr_dop(int i, int j, int num) {
        key_ints_matr_dop[i][j] = num;
    }

    //получение элемента из матрицы по индексу
    public int get_from_key_matr_dop(int i, int j) {
        return key_ints_matr_dop[i][j];
    }

    //получение элемента из матрицы по индексу
    public int get_from_key_matr(int i, int j) {
        return key_ints_matr[i][j];
    }

    //получение элемента из списка чисел по индексу
    public int get_from_key_ints(int index) {
        return key_ints.get(index);
    }

    //получение разрядности
    public int getRazryadnost() {
        return razryadnost;
    }

    //получение ключевого слова строкой
    public String getKey() {
        return key;
    }

    public int[][] getKey_ints_matr() {
        return key_ints_matr;
    }

    //считывание ключевого от пользователя и проверка его на соответствие заданным условиям (целый корень).
    public String readKey() {
        System.out.println("Введите ключ. Его длина должна быть равна целому числу в квадрате");
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String prom_key = reader.readLine();
            boolean podhodit = false;
            for (int i = 0; i < 17; i++) {
                if (prom_key.length() == i * i) podhodit = true;
                if (podhodit) break;
            }
            if (podhodit) return prom_key.toLowerCase();
            else {
                System.out.println("Не подходит, длина ключа: " + prom_key.length());
                return "";
                //тут впоследствии будем кидать исключение, прерывающее программу
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
