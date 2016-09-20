 
import java.text.DecimalFormat;
import java.io.*;
import java.util.*;

 // @author Laura Wheatley, Nikki Kitner
 
public class  TicTacToe1
{

        static DecimalFormat df = new DecimalFormat("000000000");
        static UniqueBoards boards = new UniqueBoards(); //new object to get all unique boards
        static int combinations[] = boards.allGenes(); //array stores all possible board combinations


        public static final double pc = 1.0;                // Probability of crossover
        public static double pm = 0.01;                     // Probability of mutation
        public static final int population = 500;           // Population size (must be even)
        public static final int chromosomes = 382;          // Chromosome length
        public static final int generations = 2000;         // Number of generations
        public static final int elite = (int)(0.05 * population); // Percentage of solutions to clone

        public static int[][] solutions = new int[population][chromosomes]; // 2D array storing a chromosome for each member of the "population"
        public static double[] fitness = new double[population];            // 1D array storing the "fitness" of each member in the "population"//main method


        //main method
        public static void main(String[]args){

        boards.Print(false); //prints boards

            // Initialize population randomly
        for (int i = 0; i < population; i++)
        {

            for (int j = 0; j < chromosomes; j++)
            {
                double bit = Math.random();
                if (bit < 1./9)
                    solutions[i][j] = 0;
                else if (bit < 2./9)
                    solutions[i][j] = 1;
                else if (bit < 3./9)
                    solutions[i][j] = 2;
                else if (bit < 4./9)
                    solutions[i][j] = 3;
                else if (bit < 5./9)
                    solutions[i][j] = 4;
                else if (bit < 6./9)
                    solutions[i][j] = 5;
                else if (bit < 7./9)
                    solutions[i][j] = 6;
                else if (bit < 8./9)
                    solutions[i][j] = 7;
                else
                    solutions[i][j] = 8;

                //System.out.print(solutions[i][j]);
            }

            fitness[i] = 0.;
        }


       //prints first randomly generated solution
       for (int j = 0; j < chromosomes; j++){
        System.out.print(solutions[1][j]);
        if (j < chromosomes-1){
                    System.out.print(",");
                }
       }

       // Evaluate initial fitness
        fitness();

        // Keep track of best solutions in a given generation
        int[] best = new int[elite];
        for (int i = 0; i < elite; i++)
            best[i] = 0;

        // Start generation loop
        for (int i = 1; i < generations; i++)
        {
            // Algorithm seems to work best if the probability of mutation descreases as the population evolves
            if (i > 100)
                pm = 0.005;
            else if (i > 500)
                pm = 0.001;

            // Temporary memory allocation for next generation
            int[][] tmp = new int[population][chromosomes];

            // Create new population
            int count = 0;
            while (count < population)
            {
                // Selection by roulette wheel
                int[] parents = rouletteWheel();

                //printChromosome(solutions,parents[0]);
                //printChromosome(solutions,parents[1]);

                // Crossover
                if (Math.random() < pc)
                {

                    int cut1, cut2, cup;

                    cut1 = (int)(Math.random()*chromosomes);
                    cut2 = (int)(Math.random()*chromosomes);

                if (cut1 > cut2){
                    cup = cut1;
                    cut1 = cut2;
                    cut2 = cup;
                }

                for (int j = 0; j < cut1; j++){
                tmp[count][j] = solutions[parents[0]][j];
                tmp[count+1][j] = solutions[parents[1]][j];
                }

                for (int j = cut1; j < cut2; j++){
                tmp[count][j] = solutions[parents[1]][j];
                tmp[count+1][j] = solutions[parents[0]][j];
                }

                for (int j = cut2; j < chromosomes; j++){
                    tmp[count][j] = solutions[parents[0]][j];
                    tmp[count+1][j] = solutions[parents[1]][j];
                }




                // Mutation
                    for (int j = 0; j < chromosomes; j++){
                    if (Math.random()<pm){
                        tmp[count][j] = (int)(Math.random()*9);
                    }
                    if (Math.random() < pm){
                        tmp[count+1][j] = (int)(Math.random()*9);
                    }
                  }


                // Advance count by 2 as we have added two children i.e. two new
                // rows in tmp[][].
                count = count + 2;
            }

        }


        //copy tmp to solutions
        for (int j = 0; j < population; j++)
            {
                if (fitness[j] < fitness[best[elite-1]]) // Keep elites
                {
                    System.arraycopy(tmp[j], 0, solutions[j], 0, chromosomes);
                }
            }

            // Update objective function
            fitness();

            // Calculate average fitness of population and output
            double sumFitness = 0.0;
            for (int j = 0; j < population; j++){
                sumFitness += fitness[j];
            }

            double avgFitness = sumFitness / population;

            System.out.println();
            System.out.println("Generation " + i);
            System.out.println("Avg fitness = " + avgFitness);

            // Find elite solutions and output best
            double maxFitness = -1e3;

            for (int j = 0; j < population; j++){
                if (fitness[j] > maxFitness)
                {
                    maxFitness = fitness[j];
                    best[0] = j;
                }
            }
            for (int getBest = 1; getBest < elite; getBest++)
            {
                maxFitness = -1e3;
                for (int j = 0; j < population; j++)
                {
                    if (fitness[j] > maxFitness && fitness[j] < fitness[best[getBest-1]])
                    {
                        maxFitness = fitness[j];
                        best[getBest] = j;
                    }
                }
            }
            System.out.println("Max fitness = " + fitness[best[0]]);
            for (int print = 0; print < elite; print ++)
            {
                System.out.println(best[print] +" " +fitness[best[print]]);
            }

            // Output to file
            /*outputFile.printf("%d\t%1.6e\t%1.6e\r\n",i,avgFitness,fitness[best[0]]);
            for(int p = 0; p < chromosomes; p++)
                currentBestAsList.printf("%d\t%d\n",p,solutions[best[0]][p]);
            currentBestAsList.printf("\n");

            currentBestAsArray.printf("\n{");
            for(int p = 0; p < chromosomes; p++)
            {
                currentBestAsArray.printf("%d",solutions[best[0]][p]);
                if (p < chromosomes-1)
                    currentBestAsArray.printf(",");
            }
            currentBestAsArray.printf("}\n");

            outputFile.flush();
            currentBestAsList.flush();
            currentBestAsArray.flush();
      }

        /*outputFile.close();
        currentBestAsList.close();
        currentBestAsArray.close();*/
       //System.out.println();
        /*for (int j = 0; j < chromosomes; j++){
        System.out.print(solutions[1][j]);
       }*/



    }

    //prints best solutions in newest generations
    for(int p = 0; p < chromosomes; p++)
     {
      System.out.print(solutions[best[0]][p]);
      if (p < chromosomes-1)
                    System.out.print(",");

      }

  }//end main method



     //fitness method
        public static void fitness(){
        //loops through population
        for (int m = 0; m < population; m++){

            int score = 0;
            double avgScore;

            //loops through each gene in population m
            for (int n = 0; n < chromosomes; n++){
            //beginning of fitness loop

            boolean continueLoop = true, xWon, oWon, oBlocked, gameTied;
            int x = solutions[m][n]; //x = spot on board to place x
            int numGA = placeX(combinations[n], x); //gets new board with newly placed x
            int numReturned = placeO(numGA); //randomly places an O and returns board
            int y = getGene(numReturned); //gets gene of newly returned board numReturned

            //determines if X was placed on a space that was not empty
             if (numGA == 400) {
                score += -50;
                continueLoop = false;
                }


                while (continueLoop == true){

             int z = solutions[m][y]; //z = spot on board to place x
             numGA = placeX(combinations[y],z); //gets new board with newly placed x
             numReturned = placeO(numGA); //randomly places an O and returns board
             y = getGene(numReturned);  //gets gene of newly returned board numReturned

             xWon = xWon(numGA);
             oWon = oWon(numReturned);
             oBlocked = blockO(numGA);
             gameTied = gameTied(numGA);

             //if statements to evaluate fitness

             if (numGA == 400) { //if x is placed on invalid space
                score += -50;
                continueLoop = false;
                }
             else if (numGA != 400){ //if x is placed on valid space
                 score += 5;
                }

             if (xWon == true){ //if x wins the game
                 score += 30;
                 continueLoop = false;
                }

             if (oWon == true){ //if o wins the game
                 score += -20;
                 continueLoop = false;
                }

             if (oBlocked == true){ //if x blocks o from winning
                 score += 20;
                }

             if (gameTied == true){ //if game is tied (no one wins)
                 score += 5;
                }

            }


          }

          avgScore = (double)score/(double)chromosomes;
          fitness[m] = avgScore; //adds avg fitness of population to fitness[m]

            //System.out.println("Fitness: " + fitness[m]);
        }
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

       //method where an O is randomly placed on any blank spot on the given board and returned
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

       //method that takes new board and finds the gene it corresponds to in the chromosome array
        public static int getGene(int num){

        int array[] = new int[1];
        //symmetry of num
        int sym1 = boards.Rotate90(num);
        int sym2 = boards.Rotate90(sym1);
        int sym3 = boards.Rotate90(sym2);
        int sym4 = boards.Flip(num);
        int sym5 = boards.Rotate90(sym4);
        int sym6 = boards.Rotate90(sym5);
        int sym7 = boards.Rotate90(sym6);

        for (int n = 0; n < combinations.length;n++){

            if (combinations[n] == num || combinations[n] == sym1 || combinations[n] ==  sym2 || combinations[n] ==  sym3 || combinations[n] ==  sym4 ||
                            combinations[n] ==  sym5 || combinations[n] ==  sym6 || combinations[n] ==  sym7){
                               array[0] = n;
                            }

            }

            int x = array[0];
            return x;

    }

    //method that determines if the game has been tied
    public static boolean gameTied(int num){

                int a,b,c,d,e,f,g,h,i;
                a = boards.getA(num);
                b = boards.getB(num);
                c = boards.getC(num);
                d = boards.getD(num);
                e = boards.getE(num);
                f = boards.getF(num);
                g = boards.getG(num);
                h = boards.getH(num);
                i = boards.getI(num);

                int ctrX = 0; //counts amount of x's
                int ctrO = 0; //counts amount of o's

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

               if (ctrX  == 5 && ctrO == 4){ //if game is tied, amount of x's = 5, amount of o's = 4
                 return true;
                }
                else{
                return false;
                }


    }
    //method that determines if the x placed has blocked O from winning
    public static boolean blockO(int num){
                int a,b,c,d,e,f,g,h,i;
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
                          return true;
                        }
                 else {
                            return false;
                    }

    }
    //method that determines if x has won
    public static boolean xWon(int num){
                int a,b,c,d,e,f,g,h,i;
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
                                return true;
                            }
               else{
                   return false;
                }
    }
    //method that determines if O has won
    public static boolean oWon(int num){
                int a,b,c,d,e,f,g,h,i;

                 a = boards.getA(num);
                 //System.out.println(a);
                 b = boards.getB(num);
                 //System.out.println(b);
                 c = boards.getC(num);
                 //System.out.println(b);
                 d = boards.getD(num);
                 e = boards.getE(num);
                 f = boards.getF(num);
                 g = boards.getG(num);
                 h = boards.getH(num);
                 i = boards.getI(num);

               if  ((a==2&&b==2&&c==2) || (a==2&&d==2&&g==2)||(d==2&&e==2&&f==2)||(b==2&&e==2&&h==2)||
                            (g==2&&h==2&&i==2)||(c==2&&f==2&&i==2)|| (a==2&&e==2&&i==2) || (c==2&&e==2&&g==2)) {
                             //System.out.println("poop");
                             return true;
                            }
               else{
                   //System.out.println("pee");
                   return false;
                }

        }
    //rouletteWheel method
        public static int[] rouletteWheel()
    {
        double minFitness = 500;
        for (int i = 0; i < population; i++)
        {
            if (fitness[i] < minFitness)
                minFitness = fitness[i];
        }
        minFitness = -1000;

        double sum = 0.0;
        for (int i = 0; i < population; i++){
            sum += fitness[i] - minFitness;
        }

        int[] indices = new int[2];
        double luckyNumber, findParent;
        int search;

        // Spin number 1 to get first parent
        luckyNumber = Math.random();
        findParent = 0.0;
        search = 0;
        while (findParent < luckyNumber && search < fitness.length)
        {
            findParent += (fitness[search] - minFitness) / sum;
            search ++;
        }

        indices[0] = search - 1;

        //System.out.println("Parent 1 = " + indices[0]);

        // Spin number 2 to get second parent
        luckyNumber = Math.random();
        findParent = 0.0;
        search = 0;
        while (findParent < luckyNumber && search < fitness.length)
        {
            findParent += (fitness[search] - minFitness) / sum;
            search ++;
        }

        indices[1] = search - 1;

        return indices;
    }


}
