public class Main {
    public static void main(String[] args) {
        int ma[][] = new int[][]{{0,12,29},{16,9,14},{9,8,13}};
        for(int i = 0; i<3; i++){
            for(int j = 0; j< 3; j++){
                System.out.print(ma[i][j] + " ");
            }
            System.out.println();
        }
        int[][]matr_dop = new int[3][3];
        int a00 = ma[0][0];
        int a01 = ma[0][1];
        int a02 = ma[0][2];
        int a10 = ma[1][0];
        int a11 = ma[1][1];
        int a12 = ma[1][2];
        int a20 = ma[2][0];
        int a21 = ma[2][1];
        int a22 = ma[2][2];
        matr_dop[0][0] = (a11*a22 - a21*a12);
        matr_dop[0][1] = (a10*a22 - a12*a20)*-1;
        matr_dop[0][2] = (a10*a21 - a11*a20);
        matr_dop[1][0] = (a01*a22 - a02*a21)*-1;
        matr_dop[1][1] = (a00*a22 - a02*a20);
        matr_dop[1][2] = (a00*a21 - a01*a20)*-1;
        matr_dop[2][0] = (a01*a12 - a02*a11);
        matr_dop[2][1] = (a00*a12 - a02*a10)*-1;
        matr_dop[2][2] = (a00*a11 - a01*a10);
        for(int i = 0; i<3; i++){
            for(int j = 0; j< 3; j++){
                System.out.print(matr_dop[i][j] + " ");
            }
            System.out.println();
        }
    }

}