 
import java.text.DecimalFormat;

public class PrintBoards

//authors: Laura Wheatley, Nikki Kitner

{
    static UniqueBoards boards = new UniqueBoards();
    static int allBoards[] = boards.allGenes();
    static DecimalFormat df = new DecimalFormat("000000000");
    static int ctrOBlocked = 0;
    static int ctrXWon = 0;
    static int ctrInvalid = 0;
    public static void main(String[]args){

        //least evolved strategy
        //int strategy[] = {6,4,7,6,5,0,7,4,3,1,8,3,7,7,6,8,0,8,3,5,0,8,5,7,3,4,8,5,8,8,0,7,1,5,6,0,8,5,5,2,4,2,5,0,2,5,3,4,4,3,8,0,1,3,7,0,1,6,3,3,8,4,2,3,3,3,3,4,8,0,2,6,1,2,0,4,6,5,7,6,4,3,0,5,8,6,0,8,0,2,8,3,4,3,0,1,4,3,8,6,4,3,2,7,4,7,2,3,2,3,3,8,2,0,3,3,3,1,2,4,8,8,2,3,3,3,1,3,0,5,5,0,2,6,0,0,4,5,6,3,5,0,6,6,7,4,1,0,1,1,8,8,7,3,1,1,3,4,1,4,0,2,3,1,3,8,8,3,7,6,1,7,4,3,5,7,1,5,0,8,0,2,1,6,6,6,6,7,2,3,2,5,7,1,7,6,2,5,7,3,7,0,4,8,7,2,2,4,1,7,7,2,3,0,8,1,6,1,5,4,5,7,6,0,7,7,5,3,2,2,6,5,1,0,7,4,3,8,7,4,2,0,6,0,0,1,1,0,7,1,1,4,0,8,6,2,7,3,4,1,7,0,1,8,2,4,4,5,1,7,4,6,0,8,0,7,0,0,4,1,3,3,3,1,6,6,4,2,1,1,6,8,4,6,0,5,7,7,8,2,6,7,4,8,7,4,1,7,8,3,4,8,0,7,2,4,2,5,0,7,5,0,1,7,8,5,3,4,8,7,7,2,8,2,0,1,8,4,0,1,6,7,5,2,6,4,0,0,1,2,4,2,6,7,3,8,1,0,3,6,3,6,1,1,8,4,7,4,3,1,4,0,6,5,0,6,8,3,8,5,7,6};

        //most evolved strategy
        int strategy[] = {4,4,4,5,2,0,2,2,2,3,4,1,3,0,1,0,1,3,3,3,3,2,0,3,1,6,1,5,2,2,2,1,1,2,0,7,8,1,4,2,4,4,0,4,2,4,2,8,0,2,2,1,0,8,0,0,2,1,0,0,2,2,6,7,6,1,2,2,4,4,5,4,7,8,4,3,4,7,8,3,3,0,1,0,1,5,0,0,8,0,3,1,6,5,4,4,1,4,4,4,1,1,2,6,7,8,0,6,0,0,1,6,7,8,0,1,0,8,7,3,6,1,4,4,8,6,8,4,8,0,2,8,4,4,4,1,6,6,0,0,6,0,8,6,6,1,8,5,5,5,1,6,7,8,8,6,7,8,7,1,0,0,1,0,8,1,4,0,0,0,4,7,4,4,4,4,4,6,0,0,4,5,0,0,0,0,6,6,0,1,1,1,0,0,5,0,6,1,0,1,2,3,6,0,8,4,7,8,0,4,6,0,1,0,0,0,0,1,3,1,0,1,1,6,7,8,4,4,4,2,4,2,7,8,6,7,2,0,0,7,4,4,4,2,6,2,6,7,1,4,8,0,0,0,0,0,0,4,0,0,2,0,8,6,0,0,4,0,2,6,7,0,6,0,0,0,0,0,0,0,0,0,0,0,4,0,4,7,0,7,0,0,4,5,4,6,6,4,4,0,7,6,5,0,6,0,0,0,6,0,0,0,0,5,8,5,1,0,0,7,0,4,1,2,3,1,1,1,7,1,1,0,3,4,1,1,1,1,1,1,8,1,4,8,4,5,1,4,4,4,4,5,7,1,1,1,4,1,1,1,1,1,1,1,1,1,8,4,4,5,5,4,4,4,4,4,4,5,7,5,4,4};

       System.out.println("Empty = 0, X = 1, O = 2");
       System.out.println();
       for (int i = 0; i < 382; i++){
         System.out.println("Board " + i + ": ");
         printBoard(allBoards[i]);

         System.out.println("New Board: ");
         int num = placeX(allBoards[i],strategy[i]);

         if (num == 400){
          System.out.println("Invalid Move");
          ctrInvalid++;
            }
         else {
             printBoard(num);
            }


            ctrOBlocked += countOBlocked(num);
            System.out.println("O Count: " + countOBlocked(num));
            ctrXWon += xWon(num);
            System.out.println("X won: " + xWon(num));


        }

        System.out.println("Number of O's blocked: " + ctrOBlocked);
        System.out.println("Number of wins for X: " + ctrXWon);
        System.out.println("Number of invalid moves: " + ctrInvalid);
        }

    //places X on board by taking original board and the spot where the x will be placed
    //and returning the new board. if it is placed on a spot that already has an X or O on it, the
    //returned number will be set to 400 (arbitrarily) to be evaluated by the fitness method.
        public static int placeX(int num, int i){

            int numSeperated[] = new int[9]; //array where each element is a different digit of the number
            int p = 100000000;
            int t = 1;
            int numFinal = 0;               //new board
            boolean bad = false;            // boolean is true when x is placed on invalid spot

            //seperates input number into different components
            numSeperated[0] = (num/100000000);
            numSeperated[1] = ((num/10000000)%10);
            numSeperated[2] =  ((num/1000000)%10);
            numSeperated[3] = ((num/100000)%10);
            numSeperated[4] = ((num/10000)%10);
            numSeperated[5] = ((num/1000)%10);
            numSeperated[6] = ((num/100)%10);
            numSeperated[7] = ((num/10)%10);
            numSeperated[8] =  num%10;


            for (int j = 0; j < 9; j++){

                if (j != i){
                     numFinal = numFinal + numSeperated[j] * p/t; //adds original values on board to new board
                }
                else if (j == i){ //
                    if (numSeperated[j] != 0){
                        bad = true;
                        numFinal = numFinal + 0;
                    }
                    else{
                        numSeperated[i] = 1;
                        numFinal = numFinal + numSeperated[i]*p/t;
                    }
                    }
                t = t*10;
            }


            if (bad == true) {
                numFinal = 400;
                //System.out.println(numFinal);
                return numFinal;
            }
            else {
                //System.out.println(numFinal);
                return numFinal;
            }

        }
        public static int placeO(int num){
            int numSeperated[] = new int[9]; //array where each element is a different digit of the number
            int p = 100000000;
            int t = 1;
            int numFinal = 0;
            int ctr = 0; //counts number of empty spots on board
            int ctr2 = 0;

            numSeperated[0] = (num/100000000);
            numSeperated[1] = ((num/10000000)%10);
            numSeperated[2] =  ((num/1000000)%10);
            numSeperated[3] = ((num/100000)%10);
            numSeperated[4] = ((num/10000)%10);
            numSeperated[5] = ((num/1000)%10);
            numSeperated[6] = ((num/100)%10);
            numSeperated[7] = ((num/10)%10);
            numSeperated[8] =  num%10;

           for (int j = 0; j < 9; j++){

                //counts number of empty spots on board
                if (numSeperated[j] != 0){
                     ;
                }
                else if (numSeperated[j] == 0){
                    ctr++;
                }

            }


            //randomly generates a number from 1-ctr (ctr = amount of empty spots on board)
            int i = (int)((Math.random()*ctr)+1);


            for (int j = 0 ; j < 9; j++){

                if (numSeperated[j] != 0){
                      numFinal = numFinal + numSeperated[j] * p/t; //adds digit to new board
                }

                else if (numSeperated[j] == 0){
                   ctr2++; //counts what empty space the array is on
                   if (ctr2 == i){ //if the randomly generated i is equal to the number ctr2 is on, the O is placed in that spot
                       numSeperated[j] = 2;
                       numFinal = numFinal + numSeperated[j]*p/t; //adds digit to new board
                }

               }
               t = t*10;
            }

            return numFinal;
        }
        public static int countOBlocked(int num){
            int a,b,c,d,e,f,g,h,i;
              int ctr = 0;

            a = boards.getA(num);
                 b = boards.getB(num);
                 c = boards.getC(num);
                 d = boards.getD(num);
                 e = boards.getE(num);
                 f = boards.getF(num);
                 g = boards.getG(num);
                 h = boards.getH(num);
                 i = boards.getI(num);

                 if ((a==2&&b==2&&c==1) || (b==2&&c==2&&a==1) || (d==2&&e==2&&f==1) || (e==2&&f==2&&d==1) || (g==2&&h==2&&i==1) || (h==2&&i==2&&g==1)
                        || (a==2&&d==2&&g==1) || (b==2&&e==2&&h==1) || (c==2&&f==2&&i==1) || (d==2&&g==2&&a==1) || (e==2&&h==2&&b==1) || (f==2&&i==2&&c==1)
                        || (a==2&&e==2&&i==1) || (e==2&&i==2&&a==1) || (g==2&&e==2&&c==1) || (e==2&&c==2&&g==1) || (a==2&&c==2&&b==1) || (d==2&&f==2&&e==1)
                        || (g==2&&i==2&&h==1) || (a==2&&g==2&&d==1) || (b==2&&h==2&&e==1) || (c==2&&i==2&&f==1) || (g==2&&c==2&&e==1) || (a==2&&i==2&&e==1)){
                        ctr++;
                        }

                        return ctr;

        }
        public static int xWon(int num){

            int a,b,c,d,e,f,g,h,i;
            int ctr = 0;
                a = boards.getA(num);
                 b = boards.getB(num);
                 c = boards.getC(num);
                 d = boards.getD(num);
                 e = boards.getE(num);
                 f = boards.getF(num);
                 g = boards.getG(num);
                 h = boards.getH(num);
                 i = boards.getI(num);

               if ((a==1&&b==1&&c==1) || (a==1&&d==1&&g==1)||(d==1&&e==1&&f==1)||(b==1&&e==1&&h==1)||
                            (g==1&&h==1&&i==1)||(c==1&&f==1&&i==1) || (a==1&&e==1&&i==1) || (c==1&&e==1&&g==1)){
                                ctr++;
                            }
               return ctr;
        }

        public static void printBoard(int num){

            int numSeperated[] = new int[9]; //array where each element is a different digit of the number
            numSeperated[0] = (num/100000000);
            numSeperated[1] = ((num/10000000)%10);
            numSeperated[2] =  ((num/1000000)%10);
            numSeperated[3] = ((num/100000)%10);
            numSeperated[4] = ((num/10000)%10);
            numSeperated[5] = ((num/1000)%10);
            numSeperated[6] = ((num/100)%10);
            numSeperated[7] = ((num/10)%10);
            numSeperated[8] =  num%10;
            int i = 0;

            System.out.printf("%d\t%d\t%d\n%d\t%d\t%d\n%d\t%d\t%d\n", numSeperated[0],numSeperated[1],numSeperated[2],numSeperated[3],numSeperated[4],numSeperated[5],numSeperated[6],numSeperated[7],numSeperated[8]);

            System.out.println();

        }
    }
