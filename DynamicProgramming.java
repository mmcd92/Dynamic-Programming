import java.util.*;

public class DynamicProgramming
{
     private final int MATCH = 2;
     private final int MISMATCH = 0;
     private final int GAP = -1;
     
     private Scanner in;
     
     private int[][] array;
     private int[][] path;
     
     private int left;
     private int up;
     private int diag;
     
     private String n;
     private String k;
     
     private String solutionN;
     private String solutionK;
     
     public DynamicProgramming()
     {
          in = new Scanner(System.in);
          
          setN();
          setK();
          
          initializeArray();
          initializePath();
          
          left = 0;
          up = 0;
          diag = 0;
          
          solutionN = "";
          solutionK = "";
     }
     
     
     public void setN()
     {
          System.out.println("Enter the string for n");
          n = in.next();
     }
     
     
     public void setK()
     {
          System.out.println("Enter the string for k");
          k = in.next();
     }
     
     
     public void initializeArray()
     {
          array = new int[n.length()+1][k.length()+1];
          
          for(int i = 0; i <= n.length(); i++)
          {
               for(int j = 0; j <= k.length(); j++)
               {
                    if(i == 0)
                         array[i][j] = j*(-1);
                    if(j == 0)
                         array[i][j] = i*(-1);
               }
          }
     }
     
     
     public void initializePath()
     {
          path = new int[n.length()+1][k.length()+1];
          
          for(int i = 0; i <= n.length(); i++)
          {
               for(int j = 0; j <= k.length(); j++)
               {
                    path[i][j] = 0;
               }
          }
     }
     
     
     public int calculateLeft(int x, int y)
     {
          if(x >= 1 && y >= 1)
          {
               int toLeft = array[x][y-1];
               
               int curr = toLeft + GAP;
               
               left = curr;
          }
          
          return left;
     }
     
     
     public int calculateUp(int x, int y)
     {
          if(x >= 1 && y >= 1)
          {
               int toUp = array[x-1][y];
               
               int curr = toUp + GAP;
               
               up = curr;
          }
          
          return up;
     }
     
     
     public int calculateDiag(int x, int y)
     {
          if(x >= 1 && y >= 1)
          {
               int toDiag = array[x-1][y-1];
               
               int curr = 0;
               
               if(n.charAt(x-1) == k.charAt(y-1))
                    curr = toDiag + MATCH;
               else
                    curr = toDiag + MISMATCH;
               
               diag = curr;
          }
          
          return diag;
     }
     
     
     public void fillTable()
     {
          for(int i = 1; i <= n.length(); i++)
          {
               for(int j = 1; j <= k.length(); j++)
               {
                    calculateLeft(i,j);
                    calculateUp(i,j);
                    calculateDiag(i,j);
                    
                    array[i][j] = findMax();
               }
          }
     }
     
     
     public int findMax()
     {
          int max = 0;
          
          if(left > up)
               max = left;
          else
               max = up;
          
          if(diag > max)
               max = diag;
          
          return max;
     }
     
     
     public void printArray()
     {
          System.out.printf("%7s"," ");
          for(int i = 0; i < k.length(); i++)
          {
               System.out.printf("%4s",k.charAt(i));
          }
          System.out.println();
          for(int i = 0; i <= n.length(); i++)
          {
               if(i > 0)
                    System.out.printf("%4s",n.charAt(i-1));
               else
                    System.out.printf("%4s"," ");
               
               for(int j = 0; j <= k.length(); j++)
               {
                    System.out.printf("%4s",array[i][j] + " ");
               }
               System.out.println();
          }
     }
     
     
     public void printPath()
     {
          System.out.printf("%7s"," ");
          for(int i = 0; i < k.length(); i++)
          {
               System.out.printf("%4s",k.charAt(i));
          }
          System.out.println();
          for(int i = 0; i <= n.length(); i++)
          {
               if(i > 0)
                    System.out.printf("%4s",n.charAt(i-1));
               else
                    System.out.printf("%4s"," ");
               
               for(int j = 0; j <= k.length(); j++)
               {
                    System.out.printf("%4s",path[i][j] + " ");
               }
               System.out.println();
          }
     }
     
     
     public void traceBack()
     {
          int currN = n.length();
          int currK = k.length();
          
          path[currN][currK] = 1;
          
          while(currN > 0 && currK > 0)
          {
               if(array[currN][currK-1] > array[currN-1][currK] && array[currN][currK-1] > array[currN-1][currK-1] && n.charAt(currN-1)!=k.charAt(currK-1))
               {
                    path[currN][currK-1] = 1;
                    currK--;
                    solutionN+="-";
                    solutionK+=k.charAt(currK);
               }
               else if(array[currN-1][currK] > array[currN][currK-1] && array[currN-1][currK] > array[currN-1][currK-1] && n.charAt(currN-1)!=k.charAt(currK-1))
               {
                    path[currN-1][currK] = 1;
                    currN--;
                    solutionN+=n.charAt(currN);
                    solutionK+="-";
               }
               else
               {
                    path[currN-1][currK-1] = 1;
                    currN--;
                    currK--;
                    solutionN+=n.charAt(currN);
                    solutionK+=k.charAt(currK);
                    
               }
          }
     }
     
     
     public String getSolutionN()
     {
          String temp = "";
          
          for(int i = solutionN.length(); i > 0; i--)
          {
               temp+=solutionN.charAt(i-1);
          }
          
          solutionN = temp;
          
          return solutionN;
     }
     
     
     public String getSolutionK()
     {
          String temp = "";
          
          for(int i = solutionK.length(); i > 0; i--)
          {
               temp+=solutionK.charAt(i-1);
          }
          
          solutionK = temp;
          return solutionK;
     }
     
     
     public void getSolution()
     {
          System.out.println("\nThe optimal score is " + array[n.length()][k.length()] + "\n");
     }
     
     
     public static void main(String guitar[])
     {
          DynamicProgramming test = new DynamicProgramming();
          
          test.fillTable();
          
          test.printArray();
          
          test.getSolution();
          
          test.traceBack();
          
          System.out.println(test.getSolutionN());
          System.out.println(test.getSolutionK());
          
          System.out.println("\n");
          
          test.printPath();
     }
     
     
     
}


/*
 * Sample output:

Enter the string for n
CACGTA
Enter the string for k
CCGTA
          C   C   G   T   A
      0  -1  -2  -3  -4  -5 
   C -1   2   1   0  -1  -2 
   A -2   1   2   1   0   1 
   C -3   0   3   2   1   0 
   G -4  -1   2   5   4   3 
   T -5  -2   1   4   7   6 
   A -6  -3   0   3   6   9 

The optimal score is 9

CACGTA
C-CGTA


          C   C   G   T   A
      1   0   0   0   0   0 
   C  0   1   0   0   0   0 
   A  0   1   0   0   0   0 
   C  0   0   1   0   0   0 
   G  0   0   0   1   0   0 
   T  0   0   0   0   1   0 
   A  0   0   0   0   0   1 


 */
