//@EFBO: "Click Add to Cart Button" hasNextEvent "Add Selected Item to the Cart"
//@EFBO:"Add Selected Item to the Cart" hasInterface CartViewController
//@EFBO:client-agent interactsWith CartViewController

//@EFBO: "Add Selected Item to the Cart" hasNextEvent "Display Item Details"
//@EFBO: "Display Item Details" hasInterface ItemDisplayInterface
				//@EFBO: client-agent interactsWith ItemDisplayInterface
				//@EFBO: ItemDisplayInterface isElementOf CartViewController
				//@EFBO: ItemDisplayInterface hasElemnt deleteButton

//@EFBO: "Display Item Details" hasNextEvent "Click Checkout Button"
			//@EFBO: "Click Checkout Button" hasInterface checkoutButton
			//@EFBO: CartViewController hasElement checkoutButton
						//@EFBO: user-agent interactsWith checkoutButton
						
//@EFBO: "Click Add to Cart Button" hasNextEvent "Click Products Icon"
		//@EFBO: "Click Products Icon" hasNextEvent "Display Product List"

//@EFBO: "Click Add to Cart Button" hasNextEvent "Click Cart Icon"
		//@EFBO: "Click Add to Cart Button" hasNextEvent "Display Price Summary"						
						
//@EFBO: CartViewController hasElement productsIcon
//@EFBO: user-agent interactsWith productsIcon

//@EFBO: CartViewController hasElement cartIcon
//@EFBO: user-agent interactsWith cartIcon