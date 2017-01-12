package server;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class EchoServer 
{
	// Main Method
	public static void main(String[] args) throws Exception 
	{
		@SuppressWarnings("resource")
		ServerSocket m_ServerSocket = new ServerSocket(2004, 1);
		
		int id = 0;
		
		//while loop - Connecting to server
		while (true) 
		{
			Socket clientSocket = m_ServerSocket.accept();
			ClientServiceThread cliThread = new ClientServiceThread(clientSocket, id++);
			cliThread.start();
		}//while 
	}//main
}//EchoServer

class ClientServiceThread extends Thread 
{
	//Variables
	Socket clientSocket;
	
	//Strings
	String message;
	String message2;
	String tempString;
	//Integers
	int clientID = -1;
	int userChoice;
	int userChoice2;
	int withdrawchoice;
	int depositChoice;
	int temp;
	//Boolean
	boolean running = true;
	boolean flag=false;
	
	ObjectOutputStream out;
	ObjectInputStream in;
	Random rand = new Random();
	ArrayList<Account> list = new ArrayList<Account>();
	
	//Constructor
 	ClientServiceThread(Socket s, int i) 
	{
		clientSocket = s;
		clientID = i;
	}//ClientServiceThread

	//sendMessage
	void sendMessage(String msg) 
	{
		//try catch
		try 
		{
			out.writeObject(msg);
			out.flush();
			System.out.println("server> " + msg);
		}//try 
		catch (IOException ioException) 
		{
			ioException.printStackTrace();
		}//catch
	}//sendMessage
	
	/*Method used for the choices that user send to the server after server sends first message to client*/
	public void UserInterface() throws ClassNotFoundException, IOException
	{
		switch(userChoice)
		{
			case 1: addUsers();
			break;
			
			case 2: verifyUser();
			break;
			
			case 3:  Deposit();
			break;
			
			case 4: Withdraw();
				
		}//switch statement to execute the menu options from user input
	}//UserInterface
	
	//addUser method - this method is used to add new users
	public void addUsers() throws ClassNotFoundException, IOException
	{
		
		sendMessage("Please enter Name");
		message = (String)in.readObject();
		String name = message;
		
		sendMessage("Please enter Address");
		message = (String)in.readObject();
		String address = message;
		
		int aCnum=rand.nextInt(100) + 1;
		
		//for loop
		for(Account a:list)
		{
			
			int accNum = 0;
			//while loop - 
			while(a.getAccNum()==accNum)
			{
				//Account number is random generated so each account number is different
				accNum=rand.nextInt(100) + 1;
			}//while loop
			break;
		}//for loop
		
		sendMessage("Please enter Username");
		message = (String)in.readObject();
		String username = message;
		
		sendMessage("Please enter Password");
		message = (String)in.readObject();
		String password = message;
		
		int balance=0;
		
		list.add(new Account(name, address, aCnum, username, password, balance, balance));
		
		System.out.println("\n\n"+list);
	}//addUsers
	
	//Method used for authenticating users
	public void verifyUser() throws ClassNotFoundException, IOException 
	{
		
		sendMessage("Please enter Username");
		message = (String)in.readObject();
		System.out.println(message);
			
		sendMessage("Please enter Password");
		message2 = (String)in.readObject();
		System.out.println(message2);
			
		//for loop
		for(Account a:list)
		{
			//if statement
			if(a.getUsername().equals(message) && a.getPassword().equals(message2))
			{
				tempString=a.getName();
				temp = a.getAccNum();
				flag=true;
				break;
			}//if statement
			else if(a.getUsername()!=(message) && a.getPassword()!=(message2))
			{
				System.out.println("Try again");
				break;
			}//else if
		}//for loop
	}//verifyUser
	
	//
	public void UserInterfaceLoggedin() throws ClassNotFoundException, IOException 
	{
		
			//Menu for users that have logged in
			sendMessage("\nWelcome "+tempString+"\n\n"
					+ "" + "\nPress 1: New details"
					+ "\nPress 2: Return to Main Menu"
					+ "\nPress 3: Withdraw from your account"
					+ "\nPress 4: Deposit to your account");
			message=(String)in.readObject();
			userChoice2 = new Integer(message);
			
			//switch statement - menu for logged in users
			switch(userChoice2)
			{
				case 1: updateUser();
				break;
				
				case 2: flag = false;
				break;
				
				case 3: Withdraw();
				break;
				
				case 4: Deposit();
				break;
				
				default: System.out.println("Incorrect input please try agian");
			}//switch
	}//UserInterfaceLoggedin()

	//updateUser() - this method is used to update the user
	public void updateUser() throws ClassNotFoundException, IOException
	{
		
		sendMessage("Please enter Name");
		message = (String)in.readObject();
		String name = message;
		
		sendMessage("Please enter Address");
		message = (String)in.readObject();
		String address = message;
		
		sendMessage("Please enter Username");
		message = (String)in.readObject();
		String username = message;
		
		sendMessage("Please enter Password");
		message = (String)in.readObject();
		String password = message;
		
		//For loop
		for(Account a:list)
		{
			//if statement
			if(a.getAccNum()==temp)
			{
				a.setName(name);
				a.setAddress(address);
				a.setUsername(username);
				a.setPassword(password);
				System.out.println(list);
			}//if
			
		}//for loop
			userChoice2=0;
			tempString=name;
			sendMessage("Account updated");
	}//updateUser()
	
	
	//Withdraw() - this method is used to withdraw from the account
	public void Withdraw() throws ClassNotFoundException, IOException
	{
		int tempCreditAmount=0;
		
		sendMessage("How much would you like to withdraw");	
		message = (String) in.readObject();
		withdrawchoice = new Integer(message);
		
		//For loop - to go through the list
		for (Account a : list) 
		{
			//if statement - if the account number matches with others
			if (a.getAccNum()==temp)
			{
				
				tempCreditAmount = (int) a.getBalance();
				tempCreditAmount-=withdrawchoice;
				
				//if statement - if the credit balance is over -1000 output this message
				if(tempCreditAmount < -1000)
				{
					System.out.println(tempCreditAmount);
					sendMessage("Unable to complete task because of insuficent credit balance:"+tempCreditAmount+" credit limit is -1000");	
				}
				else
				{
					a.setBalance(tempCreditAmount);
					System.out.println(tempCreditAmount);
					sendMessage("Account balance:"+tempCreditAmount);
				}//else	
			}//if
		}//for loop
	}//Withdraw()
	
	//Deposit() - to deposit money into an account
	public void Deposit() throws ClassNotFoundException, IOException
	{
		int tempamount=0;
		sendMessage("How much would you like to deposit");	
		message = (String) in.readObject();
		depositChoice = new Integer(message);
		
		//loops through list
		for (Account a : list) 
		{
			//if account numbers match do task
			if (a.getAccNum()==temp)
			{	
				tempamount+=depositChoice;
				a.setBalance(tempamount);
				System.out.println(tempamount);
				sendMessage("Account balance:"+tempamount);
			}//if
		}//for loop
	}//Deposit()
	
	//overriding the run method
	public void run() 
	{
		System.out.println("Accepted Client : ID - " + clientID + 
				"\n : Address - " + clientSocket.getInetAddress().getHostName());
		try 
		{
			out = new ObjectOutputStream(clientSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(clientSocket.getInputStream());
			System.out.println("Accepted Client : ID - " + clientID + 
					"\n : Address - " + clientSocket.getInetAddress().getHostName());
			
			//do while - complete the statement while client hasn't sent the message bye to the server
			do 
			{
				//try catch
				try 
				{	
					//Sends message to the client and reads back the response
					sendMessage("\nPress 1: New details"
							+ "\nPress 2: Return to Main Menu"
							+ "\nPress 3: Withdraw from your account"
							+ "\nPress 4: Deposit to your account");
					message = (String) in.readObject();
					userChoice = new Integer(message);
					
					UserInterface();
					
					//if statement
					if(flag==true)
					{
							//do while loop
							do
							{
								//Enter the logged in userinterface
								UserInterfaceLoggedin();
								
							}//do while loop
							while(flag==true);
					}//if statement
				}//try	
				//catch
				catch (ClassNotFoundException classnot) 
				{
					System.err.println("Data received in unknown format");
				}//catch

			}//do while
			while (!message.equals("bye"));
			System.out.println(
					"Ending Client : ID - " + clientID + " : Address - " + clientSocket.getInetAddress().getHostName());
		}//try
		catch (Exception e) 
		{
			e.printStackTrace();
		}//catch
	}//run()
}//EchoServer