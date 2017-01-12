package server;

public class Account 
{
	private String Name;
	private String Address;
	private int AccNum;
	private String Username;
	private String Password;
	private int Credit;
	private double Balance;
	private double lodgement;
	private double accountBalance;
	
	//Empty constructor
	public Account()
	{
		
	}//Account() 
	
	//Constructor containing parameters
	public Account(String name, String address, int accNum, String userName, String password, int credit, double balance)
	{
		this.Name = name;
		this.Address = address;
		this.AccNum = accNum;
		this.Username = userName;
		this.Password = password;
		this.Credit = credit;
		this.Balance = balance;
	}//Account()
	
	//Getters & setter methods
	public String getName()
	{
		return Name;
	}//getName
	
	public void setName(String name)
	{
		Name = name;
	}//setName
//--------------------------------------------------------------
	public String getAddress()
	{
		return Address;
	}//getAddress()
	
	public void setAddress(String address)
	{
		Address = address;
	}//setAddress
//--------------------------------------------------------------
	public int getAccNum()
	{
		return AccNum;
	}//getAccNum()
	
	public void setAccNum(int accNum)
	{
		AccNum = accNum;
	}//setAccNum	
//--------------------------------------------------------------
	public String getUsername()
	{
		return Username;
	}//getUserName()
	
	public void setUsername(String userName)
	{
		Username = userName;
	}//setUsername	
//--------------------------------------------------------------
	public String getPassword()
	{
		return Password;
	}//getPassword()
	
	public void setPassword(String password)
	{
		Password = password;
	}//setAddress
//--------------------------------------------------------------
	public int getCredit()
	{
		return Credit;
	}//getCredit()
	
	public void setCredit(int credit)
	{
		Credit = credit;
	}//setAddress
//--------------------------------------------------------------
	public double getBalance()
	{
		return Balance;
	}//getBalance()
	
	public void setBalance(double balance)
	{
		Balance = balance;
	}//setAddress
//--------------------------------------------------------------
	//ToString method to gather all information from the above methods and output to the console
	@Override
	public String toString() 
	{
		return "[Name= " + Name + ", Address= " + Address + ", AccNum= " + AccNum + ","
				+ "\nUsername= " + Username + ", Password= " + Password + ", Credit= " + Credit + "]";
	}//toString()
	
	public void makeWithdrawal(double withDrawal) 
	{	
		 accountBalance = accountBalance - withDrawal;
			
	}//makeWithdrawal

	public void makeLodgement(double lodgement) 
	{
		accountBalance = accountBalance + lodgement;
	}//makeLodgement
}//Account
