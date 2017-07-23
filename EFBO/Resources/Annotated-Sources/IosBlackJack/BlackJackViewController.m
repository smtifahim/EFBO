
//@EFBO: Event_Start hasNextEvent "Display Lets Play Button"
//@EFBO:      "Display Lets Play Button" hasInterface "Alert Dialog"
//@EFBO:           client-agent interactsWith "Alert Dialog"
              
//@EFBO:         "Display Lets Play Button" hasNextEvent "Click Lets Play Button"      
//@EFBO:              "Click Lets Play Button" hasInterface "Lets Play Button"
//@EFBO:                "Lets Play Button" isElementOf "Alert Dialog"
//@EFBO:                  user-agent interactsWith "Lets Play Button"
                  
//@EFBO:         "Click Lets Play Button" hasNextEvent "Display Dealer Hand"
//@EFBO:                                  "Display Dealer Hand" hasInterface BlackJackViewController
//@EFBO:                                      BlackJackViewController hasInteractionWith client-agent
                 
//@EFBO:                 "Click Lets Play Button" hasNextEvent "Display Dealer Score" 
//@EFBO:                                  "Display Dealer Score" hasInterface  dealerLabel
//@EFBO:                                      dealerLabel hasInteractionWith client-agent
                                      
//@EFBO:                  "Click Lets Play Button" hasNextEvent "Display Player Hand"
//@EFBO:                                        "Display Player Hand" hasInterface BlackJackViewController
                                                                                        
//@EFBO:                   "Click Lets Play Button" hasNextEvent "Display Player Score" 
//@EFBO:                                  "Display Player Score" hasInterface  playerLabel
//@EFBO:                                      playerLabel hasInteractionWith client-agent
                                      
//@EFBO:                   "Click Lets Play Button" hasNextEvent "Hide Lets Play Button"
//@EFBO:                                "Hide Lets Play Button" hasInterface BlackJackViewController                
                   
//@EFBO:                   "Click Lets Play Button" hasNextEvent "Display Hit Button"
//@EFBO:                                  "Display Hit Button" hasInterface BlackJackViewController                                        
                   
                               
//@EFBO:                   "Click Lets Play Button" hasNextEvent "Display Stand Button"
//@EFBO:                                  "Display Stand Button" hasInterface BlackJackViewController
                    
//@EFBO:                   "Display Hit Button" hasNextEvent "Click Hit Button"
//@EFBO:                                    "Click Hit Button" hasInterface hitButton
//@EFBO:                                             hitButton hasInteractionWith user-agent
                    
//@EFBO:                   "Display Stand Button" hasNextEvent "Click Stand Button"
//@EFBO:                                    "Click Hit Button" hasInterface standButton
//@EFBO:                                             standButton hasInteractionWith user-agent
                                             
//@EFBO:                                     "Click Hit Button" isAlternateEventOf "Click Stand Button"
                    
                    
//@EFBO:                    "Click Stand Button" hasNextEvent "Show Dealer Hand"
//@EFBO:                            "Show Dealer Hand" hasInterface BlackJackViewController
                    
//@EFBO:                    "Show Dealer Hand" hasNextEvent "Update Dealer Score" 
//@EFBO:                                    "Update Dealer Score" hasInterface "dealerLabel"
                    
//@EFBO:                    "Update Dealer Score" hasNextEvent "Evaluate Game Outcome"
//@EFBO:                                      "Evaluate Game Outcome" hasInterface BlackJackViewController
                     
//@EFBO:                    "Click Hit Button" hasNextEvent "Deal Another Player Card"
//@EFBO:                                             "Deal Another Player Card" hasInterface BlackJackViewController
                     
//@EFBO:                     "Deal Another Player Card" hasNextEvent "Click Stand Button"

                     
//@EFBO:                     "Deal Another Player Card" hasNextEvent "Update Player Score"
//@EFBO:                                "Update Player Score" hasInterface playerLabel
//@EFBO:                            "Update Player Score" hasNextEvent "Evaluate Player Outcome"
                                        
                                            
//@EFBO:                    "Evaluate Game Outcome" hasNextEvent "Update Player Score"                                           
                                                               
//@EFBO:                     "Evaluate Game Outcome" hasNextEvent "Outcome is Draw"
//@EFBO:                                      "Outcome is draw" hasInterface BlackJackViewController 
//@EFBO:                                        "Outcome is Draw" hasNextEvent "Display Game Draw Message"
//@EFBO:                                             "Display Game Draw Message" hasInterface "Alert Dialog"
                    
//@EFBO:                    "Evaluate Game Outcome" hasNextEvent "Outcome is Player Busted"
//@EFBO:                                      "Outcome is Player Busted" hasInterface BlackJackViewController 
//@EFBO:                                        "Outcome is Player Busted" hasNextEvent "Display Player Busted Message"
//@EFBO:                                              "Display Player Busted Message" hasInterface "Alert Dialog"
                    
//@EFBO:                   "Evaluate Game Outcome" hasNextEvent "Outcome is Dealer Busted"
//@EFBO:                                      "Outcome is Dealer Busted" hasInterface BlackJackViewController 
//@EFBO:                                        "Outcome is Dealer Busted" hasNextEvent "Display Dealer Busted Message"
//@EFBO:                                              "Display Dealer Busted Message" hasInterface "Alert Dialog"
                    
                    
//@EFBO:                    "Evaluate Game Outcome" hasNextEvent "Outcome is Player Wins"
//@EFBO:                                     "Outcome is Player Wins" hasInterface BlackJackViewController 
//@EFBO:                                        "Outcome is Player Wins" hasNextEvent "Display Player Wins Message"
//@EFBO:                                              "Display Player Wins Message" hasInterface "Alert Dialog"
                    
//@EFBO:                    "Evaluate Game Outcome" hasNextEvent "Outcome is Dealer Wins"
//@EFBO:                                      "Outcome is Dealer Wins" hasInterface BlackJackViewController 
//@EFBO:                                        "Outcome is Dealer Wins" hasNextEvent "Display Dealer Wins Message"
//@EFBO:                                              "Display Dealer Wins Message" hasInterface "Alert Dialog"
                    
//@EFBO:                    "Display Player Busted Message" hasNextEvent "Click OK Button"
//@EFBO:                    "Display Player Busted Message" isAlternateEventOf "Display Game Draw Message"
                    
//@EFBO:                    "Display Dealer Busted Message" hasNextEvent "Click OK Button"
//@EFBO:                    "Display Player Busted Message" isAlternateEventOf "Display Dealer Busted Message"
                    
//@EFBO:                    "Display Player Wins Message" hasNextEvent "Click OK Button"
//@EFBO:                    "Display Dealer Busted Message" isAlternateEventOf "Display Player Wins Message"
                    
//@EFBO:                    "Display Dealer Wins Message" hasNextEvent "Click OK Button"
//@EFBO:                    "Display Dealer Wins Message" isAlternateEventOf "Display Player Wins Message"
                    
                    
//@EFBO:                    "Display Game Draw Message" hasNextEvent "Click OK Button"
//@EFBO:                                               "Click OK Button" hasInterface okButton
//@EFBO:                                                      user-agent interactsWith okButton
                     
//@EFBO:                     "Click OK Button" hasNextEvent "Hide Stand Button"
//@EFBO:                                "Hide Stand Button" hasInterface BlackJackViewController
                     
//@EFBO:                     "Click OK Button" hasNextEvent "Hide Hit Button" 
//@EFBO:                                 "Hide Hit Button" hasInterface BlackJackViewController         
                       
//@EFBO:                      "Click OK Button" hasNextEvent "Update Game Stats"                      
//@EFBO:                                 "Update Game Stats" hasInterface BlackJackViewController 
                                 
//@EFBO:                      "Click OK Button" hasNextEvent "Display New Game Button"
//@EFBO:                                "Display New Game Button" hasInterface BlackJackViewController
                       
//@EFBO:                       "Display New Game Button" hasNextEvent "Click New Game Button"
//@EFBO:                                 "Click New Game Button" hasInterface restartButton
//@EFBO:                                         restartButton hasInteractionWith user-agent