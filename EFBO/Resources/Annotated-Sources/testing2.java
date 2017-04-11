//AN EXAMPLE OF A STORYBOARD FOR A LOGIN FUNCTIONALITY OF A MOBILE APPLICATION.
//AUTHOR @FAHIM T. IMAM

//DECLARATIONS OF THE SEQUENCE OF EVENTS.
//@EFBO: EVENT_START hasTimePoint 1
//@EFBO: _function_1 hasInitialEvent _tapAppIcon
//@EFBO: _tapAppIcon hasTimePoint 2
//@EFBO:   _launchAppInterface hasTimePoint 3
//@EFBO: 	_presentLoginUI hasTimePoint 4
//@EFBO: 	_enterUserName hasTimePoint 5
//@EFBO: 	_enterPassword hasTimePoint 6
//@EFBO: _tapLoginButton hasTimePoint 7
//@EFBO: _tapLoginButton isFinalEventOf _function_1
//@EFBO: _function_2 hasInitialEvent _verifyUserInfo
//@EFBO: 	_verifyUserInfo hasTimePoint 8
//@EFBO: _welcomeTheUser hasTimePoint 9
//@EFBO: _presentTryAgainUI hasTimePoint 9
//@EFBO: _welcomeTheUser isAlternateEventOf _presentTryAgainUI
//@EFBO:  		_presentTryAgainUI hasNextEvent _presentLoginUI
//@EFBO: 		_welcomeTheUser hasNextEvent EVENT_END
//@EFBO: _welcomeTheUser isFinalEventOf _function_2
		
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

//CONTINUED: DECLARATIONS OF INTERFACES FOR 
//EACH OF THE EVENTS.
//@EFBO: _verifyUserInfo hasInterface _serverInterface
//@EFBO: _welcomeTheUser hasInterface _welcomeUserUI
//@EFBO: 	_welcomeUserUI isElementOf _appInterface
//@EFBO: 	_welcomeUserUI hasElement _welcomeLabel
//@EFBO: _presentTryAgainUI hasInterface _tryAgainUI
//@EFBO: 	_tryAgainUI isElementOf _appInterface
//@EFBO: 	_tryAgainUI hasElement _loginInterfaceUI

//DECLARATIONS OF THE AGENTS INTERACTIONS.
//@EFBO: _userAgent interactsWith _appIcon
//@EFBO: _clientAgent interactsWith _appInterface
//@EFBO: _clientAgent interactsWith _logininterfaceUI
//@EFBO: _userAgent interactsWith _userNameField
//@EFBO: _userAgent interactsWith _passwordField
//@EFBO: _userAgent interactsWith _loginButton
//@EFBO: _serverAgent interactsWith _serverInterface
//@EFBO: _clientAgent interactsWith _welcomeUserUI
//@EFBO: _clientAgent interactsWith _tryAgainUI

//END OF THE STORYBOARD.