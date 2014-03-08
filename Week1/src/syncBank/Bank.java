package syncBank;

class Bank
{  public Bank(int n, int initialBalance)
   {  accounts = new int[n];
      int i;
      for (i = 0; i < accounts.length; i++)
         accounts[i] = initialBalance;
      ntransacts = 0;
   }

   public synchronized void transfer(int from, int to, int amount)
   {  if (accounts[from] < amount) return;
      accounts[from] -= amount;
      accounts[to] += amount;
      ntransacts++;
      if (ntransacts % NTEST == 0) test();
   }

    /*
     * This method doesn't need to be synchronized since it 
     * is only called from a method that is already synchronized.
     * But it doesn't harm to synchronize it, and may be safer
     * in case you ever call it from somewhere else
     */
   public synchronized void test()
   {  int sum = 0;

      for (int i = 0; i < accounts.length; i++)
         sum += accounts[i];

      System.out.println("Transactions:" + ntransacts
         + " Sum of all account balances: " + sum);
   }

   public int size()
   {  return accounts.length;
   }

   public static final int NTEST = 10;
   private int[] accounts;
   private long ntransacts = 0;
}
