package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientRequester 
{
	Socket requestSocket;
	ObjectOutputStream out;
	ObjectInputStream in;
	String message = "";
	String userinput;
	String ipaddress;
	int choice,userinput2;
	Scanner stdin;

	ClientRequester()
	{
		
	}//ClientRequester()

	//run()
	void run() 
	{
		stdin = new Scanner(System.in);
		
		//try catch
		try 
		{
			// 1. creating a socket to connect to the server
			System.out.println("Please Enter your IP Address: ");
			ipaddress = stdin.next();//user enters ip
			requestSocket = new Socket(ipaddress, 2004);
			System.out.println("Connected to " + ipaddress + " in port 2004");
			
			// 2. get Input and Output streams
			out = new ObjectOutputStream(requestSocket.getOutputStream());
			out.flush();
			in = new ObjectInputStream(requestSocket.getInputStream());
			
			// 3: Communicating with the server
			do 
			{
				try 
				{
					
					//Reads the message from the server
					message = (String) in.readObject();
					System.out.println(message);
					
					//Sends message to the server
					message = stdin.next();
					sendMessage(message);
				}//try
				catch (ClassNotFoundException classNot) 
				{
					System.err.println("data received in unknown format");
				}//catch
			}//do while 
			while (!message.equals("bye"));
		}//try
		catch (UnknownHostException unknownHost) 
		{
			System.err.println("You are trying to connect to an unknown host!");
		}//catch
		catch (IOException ioException) 
		{
			ioException.printStackTrace();
		}//catch
		finally 
		{	
			// 4: Closing connection
			try 
			{
				in.close();
				out.close();
				requestSocket.close();
			}//try
			catch (IOException ioException) 
			{
				ioException.printStackTrace();
			}//catch
		}//finally
	}//run()

	//sendMessage
	void sendMessage(String msg) 
	{
		//try catch
		try 
		{
			out.writeObject(msg);
			out.flush();
			System.out.println("client>" + msg);
		}//try 
		catch (IOException ioException)
		{
			ioException.printStackTrace();
		}//catch
	}//sendMessage
	//Main method
	public static void main(String args[]) 
	{
		ClientRequester client = new ClientRequester();
		client.run();
	}//main
}//ClientRequester