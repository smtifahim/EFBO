package controller;

import java.sql.SQLException;

import model.Account;
import model.Database;
//import model.FreeAccount;
//@EFBO: Controller hasInteractionWith client-agent
public class Controller {
	
	//@EFBO: Database isInterfaceOf "Check Login Validity"
	private Database db = new Database();
	
	public Controller(){
		
	}
	
	//@EFBO: Controller isInterfaceOf "Connect with Database Server"
	public void connect() throws Exception{
		
		db.connect();
	}
	
	public void disconnect(){
		db.disconnect();
	}
	
	public boolean isExistingAccount(Account account){
		boolean w = db.isAccountTaken(account);
		System.out.println("isExistingAccount ="+w);
		return w;
	}
	
	//@EFBO: Controller isInterfaceOf "Receive Account Validity Status From Server"
	public boolean isValidLogin(Account account){
		
		//@EFBO: Controller isInterfaceOf "Send Login Account To Server"
		boolean w = db.isValidLogin(account);
		System.out.println("isValidLogin ="+w);
		return w;

	}
	
	public void addAccount(Account account){
		try {
			db.addAccount(account);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
