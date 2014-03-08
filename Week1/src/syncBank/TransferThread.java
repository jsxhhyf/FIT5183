package syncBank;

class TransferThread extends Thread

{
	private Bank bank;
	private int fromAccount;
	private int maxAmount;
	public TransferThread(Bank b, int from, int max)
	{ 
		bank = b;
		fromAccount = from;
		maxAmount = max;
	}

	public void run()
	{ 
	    int i = 0;
		try
		{  
			while (!interrupted() && i<=20)
			{
				int toAccount = (int)(bank.size() * Math.random());
				int amount = (int)(maxAmount * Math.random());
				bank.transfer(fromAccount, toAccount, amount);
				System.out.println("Account " + fromAccount + " transfer " + amount + " to " + toAccount);
				sleep(1000);
				i++;
			}
		}
		catch(InterruptedException e) {}
	}

 
}