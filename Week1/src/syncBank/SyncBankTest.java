package syncBank;

public class SyncBankTest
{  
	public static void main(String[] args)
   {  
	
		Bank b = new Bank(NACCOUNTS, INITIAL_BALANCE);
        int i;
        for (i = 0; i < NACCOUNTS; i++)
        {  TransferThread t = new TransferThread(b, i,
              INITIAL_BALANCE);
           System.out.println("New thread starting!");
           t.start();
        }
   }

   public static final int NACCOUNTS = 10;
   public static final int INITIAL_BALANCE = 10000;
}