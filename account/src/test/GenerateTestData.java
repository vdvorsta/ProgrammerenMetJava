package test;

import java.util.ArrayList;
import java.util.Random;

import business.Account;
import business.AccountFactory;
import business.Amount;
import business.Customer;
import exceptions.NoNegativeAmountAllowed;

public class GenerateTestData
{
	private static Random random = new Random();

	public static ArrayList<Customer> createCustomers(ArrayList<Customer> customers)
	{
		final int MAX_CUSTOMERS = 1 + random.nextInt(5);
		customers = new ArrayList<Customer>();
		Customer customer;
		for (int i = 0; i < MAX_CUSTOMERS; i++)
		{
			customer = new Customer(getRandomName());
			customers.add(createAccounts(customer));
		}
		return customers;
	}

	public static Customer createAccounts(final Customer customer)
	{

		final int MAX_ACCOUNTS = 1 + random.nextInt(10);
		Account account;
		final AccountFactory factory = new AccountFactory();
		for (int i = 0; i < MAX_ACCOUNTS; i++)
		{
			//random account generation
			account = factory.createAccount(AccountFactory.Type.values()[random.nextInt(AccountFactory.Type.values().length)]);
			//0.0 <= Begin saldo < 100.0
			account.setBeginSaldo(new Amount(100.0 * random.nextDouble()));
			//random transaction generation
			account = createTransactions(account);
			customer.addAccount(account);
		}
		return customer;
	}

	public static String getRandomName()
	{
		final String names[] = { "Paesen", "Hameed", "Schoofs", "Westhovens", "Geybels", "Vicari", "Lambrechts" };
		final String fnames[] = { "Mathy", "Shahul", "Odon", "Els", "Johan", "Gino", "Geert" };

		final StringBuffer name = new StringBuffer(names[random.nextInt(names.length)]);
		name.append(", ");
		name.append(fnames[random.nextInt(fnames.length)]);
		return name.toString();
	}

	public static Account createTransactions(final Account account)
	{
		final int MAX_TRANSACTIONS = 1 + random.nextInt(50);
		Amount amount;
		for (int i = 0; i < MAX_TRANSACTIONS; i++)
		{
			//0.0 <= transaction < 20.0
			amount = new Amount(20.0 * random.nextDouble());
			//for every 4th amount invers the sign (CREDIT)
			if (i % 4 == 0)
			{
				amount.inverseSign();
			}
			try
			{
				//-20.0 < amount < 20.0
				account.addTransaction(amount);
			}
			catch (final NoNegativeAmountAllowed e)
			{
				//only error is SavingAccount
				System.err.printf("%s\n", e);
			}
		}
		return account;
	}
}
