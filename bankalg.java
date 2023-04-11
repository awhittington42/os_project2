class bankalg
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

        //Test to ensure there are two cmd line args
        if (args.length > 0)
        {
            if (args.length > 2)
            {
                System.out.println("Error - More than two command line arguments detected. Please ensure you only  enter an n value and m value in the 1st and 2nd cmd line args, respectively.");
                System.exit(-1);
            }
            else if (args.length == 1)
            {
                System.out.println("Error - Only one cmd line argument detected. Please ensure you enter an n value and m value in the 1st and 2nd cmd line args, respectively.");
                System.exit(-1);
            }
            else
            {
                System.out.println("Welcome to the Banker's Algorithm, n is " + args[0] + ", m is " + args[1] + ".");
            }
        }
        else
        {
            System.out.println("Error - No arguments detected. Please ensure you enter an n value and m value in the 1st and 2nd cmd line args, respectively.");
            System.exit(-1);
        }

        int n = Integer.parseInt(args[0]);
        int m = Integer.parseInt(args[1]);

        System.out.println("n and m captured.");

    }
}
