package view;

import model.Account;

//@EFBO: LogInEventListener hasInteractionWith client-agent
public interface LogInEventListener {

	public void loginEventOccurred(Account account);
}
