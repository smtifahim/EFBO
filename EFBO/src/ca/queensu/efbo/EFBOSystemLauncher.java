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
					EFBOInterfaceGUI window = new EFBOInterfaceGUI();
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
