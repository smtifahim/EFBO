package ca.queensu.efbo;

import java.awt.EventQueue;

public class EFBOSystemLauncher 
{
	/**
	 * Launch the EFBO application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{
				try 
				{ 
					new EFBOSystemExecutionConsole();
					EFBOUserInterfaceManager window = new EFBOUserInterfaceManager();
					window.efboSystemFrame.setVisible(true);
				} 
				
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});

	}

}
