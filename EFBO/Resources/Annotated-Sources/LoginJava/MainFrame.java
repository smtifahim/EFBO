package view;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.xml.bind.annotation.XmlRootElement;

import model.FreeAccount;
import controller.Controller;
import model.Account;


//@EFBO: MainFrame hasInteractionWith client-agent
@SuppressWarnings({ "unused", "serial" })
public class MainFrame extends JFrame implements LogInEventListener, FreeRegistrationEventListener, RequestAccountEventListener {
	
	private LoginDialog loginDialog;
	private Controller controller;
	
	public MainFrame(){
		setVisible(true);
		controller = new Controller();
		connectDb();
		
		//@EFBO: MainFrame hasElement loginDialog
		loginDialog = new LoginDialog(this);

		loginDialog.setLogInEventListener(this);
		loginDialog.setFreeRegEventListener(this);
		loginDialog.setRequestEventAccountListener(this);
		
		//@EFBO: "Display Login Dialog" hasInterface loginDialog 
		loginDialog.setVisible(true);
				
		setMinimumSize(new Dimension(500, 400));
		setSize(600, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	public void connectDb(){
		if (controller!=null){
			try {
				controller.connect();
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
	
	public void disconnectDb(){
		controller.disconnect();
	}

	@Override
	public void FreeRegistrationEventOccurred(FreeRegistrationEvent e) {
		 String username = e.getUserName();
		 String password = e.getPassword();
		 boolean member = e.isMember();
		 String fName = e.getfName();
		 String lName = e.getlName();
		 String email = e.getEmail();
		 FreeAccount fa = new FreeAccount(username,password,fName,lName,email);
		 controller.addAccount(fa);
	}

	
	@Override
	public void requestAccountEventOccurred(RequestAccountEvent e) {
		Account account = e.getAccount();
		boolean b = controller.isExistingAccount(account);
		loginDialog.isAccountTaken(b);
	}

	//@EFBO: MainFrame isInterfaceOf "Send Account Info"
	@Override
	public void loginEventOccurred(Account account) {
		if (controller.isValidLogin(account)){
			loginDialog.isValidLogin(true);
		}else{
			loginDialog.isValidLogin(false);
		}
	}
}
