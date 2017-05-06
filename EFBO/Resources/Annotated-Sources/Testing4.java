//DECLARATIONS OF THE SEQUENCE OF EVENTS.
//@EFBO: EVENT_START hasTimePoint 1
//@EFBO: _function_1 hasInitialEvent _tapAppIcon
//@EFBO: _tapAppIcon hasTimePoint 2
//@EFBO:   _launchAppInterface hasTimePoint 3

//@EFBO: _tapAppIcon hasInterface _appIcon
//@EFBO: _launchAppInterface hasInterface _appInterface 
//@EFBO: _presentLoginUI hasInterface _loginInterfaceUI
//@EFBO: 	_loginInterfaceUI isElementOf _appInterface


//@EFBO: user-agent interactsWith _appIcon
//@EFBO: client-agent interactsWith _appInterface 
//@EFBO: user-agent interactsWith _loginInterfaceUI
