package view;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import javax.swing.JTextField;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JLabel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.JPasswordField;

import model.Account;

@SuppressWarnings("serial")

//@EFBO: LoginDialog hasInteractionWith client-agent
public class LoginDialog extends JDialog 
implements FreeRegistrationEventListener, RequestAccountEventListener
{
	private final JPanel loginPanel = new JPanel();
	private JLabel lblUsername;
	private JLabel lblPassword;
	private JTextField usernameField;
    private JPasswordField passwordField;
	private JButton btnLogIn; 
	private JButton btnRegFree; 
	private JOptionPane loginSuccessDialog;
	private JOptionPane loginFailedDialog;
	
	private FreeRegistrationDialog freeRegistrationDialog;
	private LogInEventListener loginListener;
	private FreeRegistrationEventListener freeRegListener;
	private RequestAccountEventListener reqlistener;
	@SuppressWarnings("unused")
	private boolean isExistingAccount;

	/**
	 * Create the dialog.
	 */
	
	public LoginDialog(JFrame parent) 
	{
		
		super(parent,"Log In",true);//true sets modal
		freeRegistrationDialog = new FreeRegistrationDialog(this);
		
		setBounds(100, 100, 400, 235);
		getContentPane().setLayout(new BorderLayout());
		loginPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		//@EFBO: LoginDialog hasElement loginPanel
		getContentPane().add(loginPanel, BorderLayout.CENTER);
		GridBagLayout gbl_loginPanel = new GridBagLayout();
		gbl_loginPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0};
		gbl_loginPanel.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gbl_loginPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 1.0, Double.MIN_VALUE};
		gbl_loginPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		loginPanel.setLayout(gbl_loginPanel);
		
		
	
			lblUsername = new JLabel("Username");
			
			lblUsername.setFont(new Font("Tahoma", Font.BOLD, 13));
			GridBagConstraints gbc_lblUsername = new GridBagConstraints();
			gbc_lblUsername.insets = new Insets(0, 0, 5, 5);
			gbc_lblUsername.anchor = GridBagConstraints.EAST;
			gbc_lblUsername.gridx = 2;
			gbc_lblUsername.gridy = 1;
			loginPanel.add(lblUsername, gbc_lblUsername);
		
			//@EFBO: usernameField hasInteractionWith user-agent	
			usernameField = new JTextField();
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 5);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 4;
			gbc_textField.gridy = 1;
			
			//@EFBO: loginPanel hasElement usernameField
			loginPanel.add(usernameField, gbc_textField);
			usernameField.setColumns(10);
		
			lblPassword = new JLabel("Password");
			lblPassword.setFont(new Font("Tahoma", Font.BOLD, 13));
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.gridx = 2;
			gbc_lblPassword.gridy = 2;
			loginPanel.add(lblPassword, gbc_lblPassword);
		
			//@EFBO: passwordField hasInteractionWith user-agent
			passwordField = new JPasswordField();
			GridBagConstraints gbc_passwordField = new GridBagConstraints();
			gbc_passwordField.insets = new Insets(0, 0, 5, 5);
			gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
			gbc_passwordField.gridx = 4;
			gbc_passwordField.gridy = 2;
			
			//@EFBO: loginPanel hasElement passwordField
			loginPanel.add(passwordField, gbc_passwordField);
		
			//@EFBO: btnLogIn hasInteractionWith user-agent
			btnLogIn = new JButton("Log In");
			
			btnLogIn.setFont(new Font("Tahoma", Font.BOLD, 11));
			GridBagConstraints gbc_btnLogIn = new GridBagConstraints();
			gbc_btnLogIn.insets = new Insets(0, 0, 5, 5);
			gbc_btnLogIn.gridx = 4;
			gbc_btnLogIn.gridy = 3;
			
			//@EFBO: loginPanel hasElement btnLogIn
			loginPanel.add(btnLogIn, gbc_btnLogIn);
			
			//@EFBO: btnLogIn isInterfaceOf "Click Login Button"
			btnLogIn.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent arg0) {
					
					//@EFBO: usernameField isInterfaceOf "Enter User Name"
					String username = usernameField.getText();
					//@EFBO: passwordField isInterfaceOf "Enter Password"
					String pass = new String(passwordField.getPassword());
					
					
					//@EFBO: LoginDialog hasElement Account
					//@EFBO: Account isInterfaceOf "Get Entered User Name"
					//@EFBO: Account isInterfaceOf "Get Entered Password"
					Account account = new Account(username,pass,false);
					
					
					if (loginListener!=null)
					//@EFBO: LoginDialog hasElement LoginEventListener
					//@EFBO: LoginEventListener isInterfaceOf "Send Entered User Name"
					//@EFBO: LoginEventListener isInterfaceOf "Send Entered Password"	
					loginListener.loginEventOccurred(account);
				}
			});
		
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
						
				btnRegFree = new JButton("Register Free");
				btnRegFree.addActionListener(new ActionListener() {
					
					@Override
					public void actionPerformed(ActionEvent e) {
						
						freeRegistrationDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
						freeRegistrationDialog.setFreeRegistrationEventOberservers(LoginDialog.this);
						freeRegistrationDialog.setRequestAccountEventListener(LoginDialog.this);
						freeRegistrationDialog.setVisible(true);
					}
				});
				buttonPane.add(btnRegFree);
			
	}
	
	public void validateUserName(){
		
	}
	public void validatePassword(){
		
	}
	
	public boolean isAccountTaken(boolean b){
		freeRegistrationDialog.isAccountTaken(b);

		return b;
	}

	public void setLogInEventListener(LogInEventListener listener) {
		this.loginListener = listener;
	}

	public void setFreeRegEventListener(FreeRegistrationEventListener listener) {
		this.freeRegListener = listener;
	}


	
	//FreeRegistrationDialog calling this method, pass event object to MainFrame
	@Override
	public void FreeRegistrationEventOccurred(FreeRegistrationEvent e) { 
		if (freeRegListener == null){
			System.out.println("In Login: FreeRegListener = null");
		}
		freeRegListener.FreeRegistrationEventOccurred(e);
	}

	public void setRequestEventAccountListener(RequestAccountEventListener listener) {
		this.reqlistener = listener;
	}

	@Override
	public void requestAccountEventOccurred(RequestAccountEvent e) {
		reqlistener.requestAccountEventOccurred(e);
	}
	
	
	
	@SuppressWarnings("static-access")
	public boolean isValidLogin(boolean b) {
		if (b == true)
		{
			//@EFBO: loginSuccessDialog isElementOf LoginDialog
			//@EFBO: loginSuccessDialog isInterfaceOf "Display Successful Login"
			loginSuccessDialog.showMessageDialog(null, "Successful Login. Welcome " 
			                                          + usernameField.getText() + "!");			
		}
		else
		{
			//@EFBO: loginFailedDialog isElementOf LoginDialog
			//@EFBO: loginFailedDialog isInterfaceOf "Display Try Again"
			//@EFBO: "Display Try Again"  isAlternateEventOf "Display Successful Login"
			loginFailedDialog.showMessageDialog(null, "Login Failed. Please Try Again.");
		}
		return false;

	}

}
