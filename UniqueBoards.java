 
import java.text.DecimalFormat;

/**
 * PossibleBoards determines the amount and sequence of every possible unique board, while taking symmetry into account
 *
 * @author Laura Wheatley, Nikki Kitner
 */
 
public class UniqueBoards
{

    int chromosome[] = new int[382];
    boolean print;
    int uniqueBoards[] = new int[382];
    DecimalFormat df = new DecimalFormat("000000000");

    //method that converts number of base 10 to base 3 and returns the number in base 3
   public int[] allGenes(){
       int possibleBoards[] = new int[19682];
       //fills possibleBoards with every possible board combination (19683 combinations) by converting the numbers from
       //0 to 19682 in base 10 to base 3, where 0 = EMPTY, 1 = X, 2 = O
       for (int i = 0; i < (possibleBoards.length);i++){
           possibleBoards[i] = Base3(i);
        }
       //fills chromosome array with all unique boards calling the unique board method
       chromosome = uniqueBoards(possibleBoards);
       return chromosome;
    }

    public boolean Print(boolean print){
       if (print == true){
           for (int x = 0; x < chromosome.length; x++){
            System.out.println(df.format(uniqueBoards[x])); //prints and formats all unique boards
                }
            }
       if (print == true){
               System.out.printf("# of unique boards: %d",chromosome.length);
            }
       return print;
   }

   //method that converts numbers of base 10 to base 3
    public  static int Base3(int num) {
       int ret = 0, factor = 1;
        while (num > 0) {
         ret += num % 3 * factor;
         num /= 3;
         factor *= 10;
       }
       return ret;
   }

   //fills array uniqueBoards with every possible unique combination, taking symmetry into account
   public  int[] uniqueBoards(int boards[]){
      int a, b, c, d, e, f, g, h, i, sym1,sym2,sym3,sym4,sym5,sym6,sym7,num, num2;
      int ctr = 0;
      //determines and prints every unique board possible. stores all boards in an array.
      for (int k = 0; k < (Math.pow(3,9))-1;k++){
                 num = boards[k];
                 int xCount, oCount;

                 a = getA(num); //gets top left value on board
                 b = getB(num); //gets top center value on board
                 c = getC(num); //gets top right value on board
                 d = getD(num); //middle left
                 e = getE(num); //middle center
                 f = getF(num); //middle right
                 g = getG(num); //bottom left
                 h = getH(num); //bottom center
                 i = getI(num); //bottom right

                 //finds the symmetrical values of the board by rotating the board 90, 180, and 270 degrees and storing
                 //those numbers. flips board and rotates 90, 180, and 270 degrees again, to get all symmetrical values.
                 sym1 = Rotate90(num);
                 sym2 = Rotate90(sym1);
                 sym3 = Rotate90(sym2);
                 sym4 = Flip(num);
                 sym5 = Rotate90(sym4);
                 sym6 = Rotate90(sym5);
                 sym7 = Rotate90(sym6);

                 xCount = getXCount(a,b,c,d,e,f,g,h,i); //gets number of x's
                 oCount = getOCount(a,b,c,d,e,f,g,h,i); //gets number of o's
                 boolean illegal = illegalBoards(num);
                 boolean continueAfterWon = illegalWin(num);

                //determines if specific board meets the requirements to be considered a usable and unique board
                if ((xCount>oCount) && xCount != 1 || (oCount>xCount) || (xCount == 1 && oCount == 0)){
                     num = 5; //5 is an arbitrary number that is used to set the symmetrical elements of the
                                //array to something else.
                    }
                if (continueAfterWon == true){
                     num = 5;
                    }
                if (illegal == true){
                  num = 5;
                  }
                 //eliminates symetric boards from the array
                  for (int n = 0; n < (Math.pow(3,9))-1;n++){
                     if (boards[n] == sym1 || boards[n] ==  sym2|| boards[n] ==  sym3|| boards[n] ==  sym4||
                            boards[n] ==  sym5 || boards[n] ==  sym6|| boards[n] ==  sym7){
                            boards[n] = 5;
                    }
                }

                //adds unique boards to new array containing only unique sequences
                if (num != 5){
                    uniqueBoards[ctr] = num; //adds unique number to uniqueBoards array
                    ctr++; //counts how many elements are added to uniqueBoards array
                }

            }

       //System.out.println("Count: " + ctr);
       return uniqueBoards;
       }

       //returns number of x's on a specific board
       public static int getXCount(int a,int b,int c,int d,int e,int f,int g,int h,int i){
          int ctrX = 0;
              if (a == 1){
                     ctrX ++;
                    }
              if (b == 1){
                     ctrX ++;
                    }
              if (c == 1){
                     ctrX ++;
                    }
              if (d == 1){
                     ctrX ++;
                    }
              if (e == 1){
                     ctrX ++;
                    }
              if (f == 1){
                     ctrX ++;
                    }
              if (g == 1){
                     ctrX ++;
                    }
              if (h == 1){
                    ctrX ++;
                    }
              if (i == 1){
                     ctrX ++;
                    }
                    return ctrX;
                }

                //returns number of o's on a specific board
        public static int getOCount(int a,int b,int c,int d,int e,int f,int g,int h,int i){
            int ctrO = 0;
              if (a == 2){
                     ctrO ++;
                    }
               if (b == 2){
                     ctrO ++;
                    }
               if (c == 2){
                     ctrO ++;
                    }
               if (d == 2){
                     ctrO ++;
                    }
               if (e == 2){
                     ctrO ++;
                    }
               if (f == 2){
                     ctrO ++;
                    }
               if (g == 2){
                     ctrO ++;
                    }
               if (h == 2){
                     ctrO ++;
                    }
               if (i == 2){
                     ctrO ++;
                    }
                    return ctrO;
                }

                //returns whether the board is illegal or not. illegal boards have one row/column of x's and one row/column of o's.
                //which cannot happen because once one person wins, the game ends
        public boolean illegalBoards(int num){
                 int a, b, c, d, e, f, g, h, i;
                 a = getA(num);
                 b = getB(num);
                 c = getC(num);
                 d = getD(num);
                 e = getE(num);
                 f = getF(num);
                 g = getG(num);
                 h = getH(num);
                 i = getI(num);

                 boolean isIllegal;

                 if (((a==1&&b==1&&c==1) || (a==1&&d==1&&g==1)||(d==1&&e==1&&f==1)||(b==1&&e==1&&h==1)||
                            (g==1&&h==1&&i==1)||(c==1&&f==1&&i==1) || (a==1&&e==1&&i==1) || (c==1&&e==1&&g==1)) &&
                            ((a==2&&b==2&&c==2) || (a==2&&d==2&&g==2)||(d==2&&e==2&&f==2)||(b==2&&e==2&&h==2)||
                            (g==2&&h==2&&i==2)||(c==2&&f==2&&i==2)|| (a==2&&e==2&&i==2) || (c==2&&e==2&&g==2))) {
                                    return true;
                                }
                 else {
                     return false;
                    }

            }

            //this method determines whether the game has continued once someone has won (which is illegal). if it has
            //continued, it returns true
            public  boolean illegalWin(int num){
                int a,b,c,d,e,f,g,h,i;
                int xCount, oCount;
                 a = getA(num);
                 b = getB(num);
                 c = getC(num);
                 d = getD(num);
                 e = getE(num);
                 f = getF(num);
                 g = getG(num);
                 h = getH(num);
                 i = getI(num);
                 xCount = getXCount(a,b,c,d,e,f,g,h,i);
                 oCount = getOCount(a,b,c,d,e,f,g,h,i);

                 if (((a==1&&b==1&&c==1) || (a==1&&d==1&&g==1)||(d==1&&e==1&&f==1)||(b==1&&e==1&&h==1)||
                            (g==1&&h==1&&i==1)||(c==1&&f==1&&i==1) || (a==1&&e==1&&i==1) || (c==1&&e==1&&g==1)) &&
                            (xCount==oCount)){
                            return true;
                            }
                 else if (((a==2&&b==2&&c==2) || (a==2&&d==2&&g==2)||(d==2&&e==2&&f==2)||(b==2&&e==2&&h==2)||
                            (g==2&&h==2&&i==2)||(c==2&&f==2&&i==2)|| (a==2&&e==2&&i==2) || (c==2&&e==2&&g==2)) &&
                            (xCount>oCount)) {
                            return true;
                                }
                 else {
                     return false;
                            }

                         }

    //returns top left value on board
    public int getA(int num){
        int a = (num/100000000);
        return a;
       }
     //returns top center value on board
    public  int getB(int num){
        int b = ((num/10000000)%10);
         return b;
    }
    //returns top right value on board
    public  int getC(int num){
      int c = ((num/1000000)%10);
      return c;
    }
    //returns middle left value on board
    public  int getD(int num){
      int d = ((num/100000)%10);
      return d;
    }
     //returns middle center value on board
    public  int getE(int num){
      int e = ((num/10000)%10);
      return e;
    }
     //returns middle right value on board
    public  int getF(int num){
      int f = ((num/1000)%10);
      return f;
    }
     //returns bottom left value on board
    public  int getG(int num){
      int g = ((num/100)%10);
      return g;
    }
    //returns bottom center value on board
    public  int getH(int num){
      int h = ((num/10)%10);
      return h;
    }
    //returns bottom right value on board
    public  int getI(int num){
      int i = num%10;
      return i;
    }
    //returns rotated by 90 degree board
    public  int Rotate90 (int num){
        int a,b,c,d,e,f,g,h,i;
          a = getA(num);
          b = getB(num);
          c = getC(num);
          d = getD(num);
          e = getE(num);
          f = getF(num);
          g = getG(num);
          h = getH(num);
          i = getI(num);
        int rotation = c*100000000 + f*10000000 + i*1000000 + b*100000 + e*10000 + h*1000 + a*100 + d*10 + g;
        return rotation;
        } //rotate90
    //flips board horizontally
    public  int Flip (int num){
         int a,b,c,d,e,f,g,h,i;
          a = getA(num);
          b = getB(num);
          c = getC(num);
          d = getD(num);
          e = getE(num);
          f = getF(num);
          g = getG(num);
          h = getH(num);
          i = getI(num);
          int flip = c*100000000 + b*10000000 + a*1000000 + f*100000 + e*10000 + d*1000 + i*100 + h*10 + g;
          return flip;
        } //end flip
    } //end uniqueboards class
