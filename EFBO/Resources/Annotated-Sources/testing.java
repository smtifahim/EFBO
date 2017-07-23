//AN EXAMPLE OF A STORYBOARD FOR A LOGIN FUNCTIONALITY OF A MOBILE APPLICATION.
//DECLARATIONS OF THE SEQUENCE OF EVENTS.

//      @EFBO: EVENT_START hasNextEvent _tapAppIcon
//@EFBO: _tapAppIcon hasNextEvent _launchAppInterface
//@EFBO:   _launchAppInterface hasNextEvent _presentLoginUI
//@EFBO: 	 _presentLoginUI hasNextEvent _enterUserName

/* @EFBO: 	 _presentLoginUI hasNextEvent _enterPassword 
*/

//@EFBO: 	 _enterUserName hasNextEvent _enterPassword
!/
//@EFBO: 	 _enterPassword hasNextEvent _tapLoginButton
//@EFBO:   _tapLoginButton hasNextEvent _verifyUserInfo
//@EFBO: 	_verifyUserInfo hasNextEvent _presentTryAgainUI
//@EFBO:  		_presentTryAgainUI hasNextEvent _presentLoginUI
//@EFBO:       _verifyUserInfo hasNextEvent _welcomeTheUser
//@EFBO: _welcomeTheUser isAlternateEventOf _presentTryAgainUI
//@EFBO: 		_welcomeTheUser hasNextEvent EVENT_END

//DECLARATIONS OF FUNCTIONALITIES.
//@EFBO: _loginFunctionality hasInitialEvent _tapAppIcon
//@EFBO: _loginFunctionality hasFinalEvent _welcomeTheUser

//DECLARATIONS OF INTERFACES FOR EACH OF THE EVENTS.
//@EFBO: _tapAppIcon hasInterface _appIcon
//@EFBO: _launchAppInterface hasInterface _appInterface 
//@EFBO: _presentLoginUI hasInterface _loginInterfaceUI
//@EFBO: 	_loginInterfaceUI isElementOf _appInterface
//@EFBO: 	_loginInterfaceUI hasElement _userNameField
//@EFBO: 		_userNameField isInterfaceOf _enterUserName
//@EFBO: 	_loginInterfaceUI hasElement _passwordField
//@EFBO: 		_passwordField isInterfaceOf _enterPassword
//@EFBO: 	_loginInterfaceUI hasElement _loginButton
//@EFBO: 		_loginButton isInterfaceOf _tapLoginButton
//@EFBO: _verifyUserInfo hasInterface _serverInterface
//@EFBO: _welcomeTheUser hasInterface _welcomeUserUI
//@EFBO: 	_welcomeUserUI isElementOf _appInterface
//@EFBO: 	_welcomeUserUI hasElement _welcomeLabel
//@EFBO: _presentTryAgainUI hasInterface _tryAgainUI
//@EFBO: 	_tryAgainUI isElementOf _appInterface
//@EFBO: 	_tryAgainUI hasElement _loginInterfaceUI

//DECLARATIONS OF THE AGENTS INTERACTIONS.
//@EFBO: user-agent interactsWith _appIcon
//@EFBO: client-agent interactsWith _appInterface
//@EFBO: client-agent interactsWith _logininterfaceUI
//@EFBO: user-agent interactsWith _userNameField
//@EFBO: user-agent interactsWith _passwordField
//@EFBO: user-agent interactsWith _loginButton
//@EFBO: server-agent interactsWith _serverInterface
//@EFBO: client-agent interactsWith _welcomeUserUI
//@EFBO: client-agent interactsWith _tryAgainUI
//END OF THE STORYBOARD.