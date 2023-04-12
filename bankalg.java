import java.util.*;

class BankersAlgorithm
{
    public static void main(String[] args)
    {
    /*
         * Variables:
         *
         * n
         *   -number of processes
         *   -args[1]
         *
         * m
         *   -number of resource types
         *   -args[2]
         *
         * max
         *   -n x m matrix that stores the max demand of each process
         *   -If max[i][j] = k, then Process Pi may request at most k instances of resource Rj
         *
         * Allocation
         *   -n x m matrix that stores the # of each resource type currently allocated to each process
         *   -If allocation[i][j] = k, then process Pi is currently allocated k instances of resource Rj
         *
         * Need
         *   -n x m matrix that stores the remaining resource need of each process
         *   -if need[i][j] = k, then process Pi may need k more instances of resource type Rj to complete task.
         *   -need[i][j] = max[i][j] - allocation[i][j]
         *
         *   ***************************************************************************************************
         *
         * Algorithm:
             -Main algorithm is Resource Request algorithm, which depends on safety algorithm.
         *     -Safety algorithm determines if the current state is a safe state
         *   -Resource Request algorithm determines where a request can be safely granted.
    */
        if(args.length != 1)
        {
            System.out.println("Missing request flag command line argument. Enter 0 or 1 and try again.");
            System.exit(-1);
        }
        int option = Integer.parseInt(args[0]);
        //available array
        int[] available =  {1, 5, 2, 0};

        //max matrix
        int[][] max = {
            {0, 0, 1, 2},
            {1, 7, 5, 0},
            {2, 3, 5, 6},
            {0, 6, 5, 2},
            {0, 6, 5, 6}
        };

        //allocation matrix
        int[][] allocation = {
            {0, 0, 1, 2},
            {1, 0, 0, 0},
            {1, 3, 5, 4},
            {0, 6, 3, 2},
            {0, 0, 1, 4}
        };


        //need matrix
        int[][] need = new int[5][4];
        for (int x = 0; x < 5; x++)
            for (int y = 0; y < 4; y++)
            {
                need[x][y] = max[x][y] - allocation[x][y];
                //System.out.println("Need[" + x + "][" + y + "] - " + need[x][y]);
            }
        int request[] = new int[4];
        if (option == 0)
        {
            request[0] = 0;
            request[1] = 4;
            request[2] = 2;
            request[3] = 0;
        }
        else
        {
            request[0] = 1;
            request[1] = 4;
            request[2] = 2;
            request[3] = 0;

            for (int i = 0; i < 5; i++)
                for (int j = 0; j < 4; j++)
                {
                    need[i][j] += 15;
                }
            //need[1][0] = 20;
            //need[1][1] = 20;
            //need[1][2] = 20;
            //need[1][3] = 20;
        }
        //step 1

        System.out.println("Step 1 - Checking if request <= need");
        boolean requestFlag = true;
        boolean reqAvailFlag = true;

        for (int x = 0; x < request.length; x++)
        {
            if (request[x] > need[1][x])
                requestFlag = false;

            if (request[x] > available[x])
                reqAvailFlag = false;
        }

        if (requestFlag)
        {
            System.out.println("request[i] <= need[i]!");
        }
        else
        {
            System.out.println("Error - process[i] has exceeded its claim!");
            System.exit(-1);
        }

        if (reqAvailFlag)
            System.out.println("request[i] <= available[i]!");
        else
        {
            System.out.println("Error - Request > available, process has to wait.");
            System.exit(0);
        }

        //save old available and allocation and need, and then check if state is safe with new ones.
        int[] oldAvailable = available;
        int[][] oldAllocation = allocation;
        int[][] oldNeed = need;
        for(int x = 0; x < available.length; x++)
        {
            //System.out.println("available[" + x + "] = " + available[x]);
            available[x] -= request[x];
            //System.out.println("New available[" + x + "] = " + available[x]);
            allocation[1][x] += request[x];
            need[1][x] -= request[x];
        }

        //Call safety algorithm here to check if state is safe.

        int[] work = new int[4];
        for(int i = 0; i < 4; i++)
            work[i] = available[i];
        boolean[] finished = new boolean[5];
        int count = 0;

        while (count < 5)
        {
            boolean found = false;
            for (int i = 0; i < 5; i++)
            {
                if (!finished[i])
                {
                    boolean enough = true;
                    for (int j = 0; j < 4; j++)
                    {
                        if(need[i][j] > work[j])
                        {
                            enough = false;
                            break;
                        }
                    }
                    if (enough)
                    {
                        for (int j = 0; j < 4; j ++)
                        {
                            work[j] += allocation[i][j];
                        }
                        finished[i] = true;
                        found = true;
                        count++;
                    }
                }
            }
            if (!found)
                break;
        }
        boolean result;
        if (count == 5)
        {
            result = true;
            //System.out.println("result is true");
        }
        else
        {
            result = false;
            //System.out.println("result is false");
        }

        if(result)
        {
            System.out.print("Request for Process 1 is: (");
            for(int x = 0; x < request.length; x++)
                if (x == request.length - 1)
                    System.out.print(request[x]);
                else
                    System.out.print(request[x] + " ");
            System.out.print(")\n");
            System.out.println("Request generates a safe state, request is granted!.");
            System.out.println("Need Matrix:");
            for(int i = 0; i < 5; i++)
            {
                System.out.print("[");
                for(int j = 0; j < 4; j++)
                {
                    if(j == 3)
                        System.out.print(need[i][j]);
                    else
                        System.out.print(need[i][j] + ", ");
                }
                System.out.print("]\n");
            }

            System.out.print("Available: (");
            for(int x = 0; x < available.length; x++)
                if(x == available.length - 1)
                    System.out.print(available[x]);
                else
                    System.out.print(available[x] + ", ");
            System.out.println(")");
            //print out need matrix and available array.
        }
        else
        {
            System.out.print("Request for Process 1 is: (");
             for(int x = 0; x < request.length; x++)
                 if(x == request.length - 1)
                     System.out.print(request[x]);
                 else
                     System.out.print(request[x] + " ");
            System.out.print(")\n");
            System.out.println("Request doesn't generate a safe state, request cannot be granted!");
            System.out.println("Need Matrix:");
            for(int i = 0; i < 5; i++)
            {
                System.out.print("[");
                for(int j = 0; j < 4; j++)
                {
                    if(j == 3)
                        System.out.print(need[i][j]);
                    else
                        System.out.print(need[i][j] + ", ");
                }
                System.out.print("]\n");
            }

            System.out.print("Available: (");
            for(int x = 0; x < available.length; x++)
                if(x == available.length - 1)
                    System.out.print(available[x]);
                else
                    System.out.print(available[x] + ", ");
            System.out.println(")");

        }
    }
}

