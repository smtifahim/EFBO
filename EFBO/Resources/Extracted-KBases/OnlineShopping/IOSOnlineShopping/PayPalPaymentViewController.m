//@EFBO: "Click Checkout Button" hasNextEvent "Display Price Summary"
//@EFBO: "Display Price Summary" hasInterface PayPalPaymentViewController
//@EFBO: client-agent interactsWith PayPalPaymentViewController 

//@EFBO: "Display Price Summary" hasNextEvent "Click Send Payment Button"
//@EFBO: "Click Send Payment Button" hasInterface sendPaymentButton
			//@EFBO: user-agent interactsWith sendPaymentButton
			//@EFBO: PayPalPaymentViewController hasElement sendPaymentButton
			
//@EFBO: "Click Send Payment Button" hasNextEvent "Send Payment Verification to Server"
		//@EFBO: PayPalPaymentViewController isInterfaceOf "Send Payment Verification to Server"
		
//@EFBO: "Send Payment Verification to Server" hasNextEvent "Verify Payment"
					//@EFBO: "Verify Payment" hasInterface PayPalServerInterface
					//@EFBO: server-agent interactsWith PayPalServerInterface		
		
		//@EFBO: "Verify Payment" hasNextEvent "Receive Successful Payment Message from Server"
		//@EFBO: "Verify Payment" hasNextEvent "Receive Failed Payment Message from Server"
		
                        //@EFBO: PayPalPaymentViewController isInterfaceOf "Receive Successful Payment Message from Server"
         
            //@EFBO: "Receive Failed Payment Message from Server" isAlternateEventOf "Receive Successful Payment Message from Server"
               
                        //@EFBO: PayPalPaymentViewController isInterfaceOf "Receive Failed Payment Message from Server"      				
                            				
                            				
                        	