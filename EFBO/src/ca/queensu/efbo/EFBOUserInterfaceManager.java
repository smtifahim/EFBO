package ca.queensu.efbo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;

public class EFBOUserInterfaceManager 
{

	public JFrame efboSystemFrame;
	private EFBOComparatorManager efboCompManager = new EFBOComparatorManager();
	
	private JButton btnStepI   = new JButton("STEP I.   LOAD the FIRST System's Knowledge.");
	private JButton btnStepII  = new JButton("STEP II.  LOAD the SECOND System's Knowledge.");
	private JButton btnStepIII = new JButton("STEP III. LOAD the EFBO-Validation Ontology.          ");
	private JButton btnStepIV  = new JButton("STEP IV.  MERGE the 1st + 2nd System's Knowledge.");
	private JButton btnStepV   = new JButton("STEP V.   IDENTIFY the MAPPING Events.");
	private Font textFont = new Font("DialogInput", Font.BOLD, 16);
	
	/**
	 * Create the application.
	 */
	public EFBOUserInterfaceManager() throws Exception
	{
		this.initializeGUIElements();
		this.setActionListeners();
	}

	private void setActionListeners()
	{
		btnStepI.setFont(textFont);		
		btnStepI.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					System.out.println(btnStepI.getText());
					efboCompManager.loadFirstSystem();
					btnStepI.setEnabled(false);
					btnStepII.setEnabled(true);
				} 
				catch (Exception e1) 
				{
					e1.printStackTrace();
				}
			}
		});
		
		btnStepII.setFont(textFont);
		btnStepII.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					System.out.println(btnStepII.getText());
					efboCompManager.loadSecondSystem();
					btnStepII.setEnabled(false);
					btnStepIII.setEnabled(true);
				} 
				
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnStepIII.setFont(textFont);
		btnStepIII.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e)
			{
				try 
				{
					System.out.println(btnStepIII.getText());
					efboCompManager.loadEFBOValidatorOntology();
					btnStepIII.setEnabled(false);
					btnStepIV.setEnabled(true);
				} 
				catch (Exception e1) 
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		btnStepIV.setFont(textFont);
		btnStepIV.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					System.out.println(btnStepIV.getText());
					efboCompManager.mergeLoadedKBases();
					btnStepIV.setEnabled(false);
					btnStepV.setEnabled(true);
				} 
				
				catch (Exception e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		
		btnStepV.setFont(textFont);
		btnStepV.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println(btnStepV.getText());
			}
		});
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeGUIElements() 
	{
		efboSystemFrame = new JFrame();
		efboSystemFrame.setBackground(Color.BLACK);
		efboSystemFrame.getContentPane().setBackground(UIManager.getColor("Button.highlight"));
		efboSystemFrame.setTitle("The EFBO System Interface");
		efboSystemFrame.setBounds(100, 100, 713, 383);
		efboSystemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnStepII.setEnabled(false); btnStepIII.setEnabled(false);
		btnStepIV.setEnabled(false); btnStepV.setEnabled(false);
		
		btnStepI.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepII.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepIII.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepIV.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepV.setHorizontalAlignment(SwingConstants.LEFT);
		
		JLabel lblMmxviiFahim = new JLabel("MMXVII \u00A9 Fahim T. Imam. All Rights Reserved.");
		lblMmxviiFahim.setHorizontalAlignment(SwingConstants.CENTER);
		lblMmxviiFahim.setForeground(UIManager.getColor("Button.darkShadow"));
		lblMmxviiFahim.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GroupLayout groupLayout = new GroupLayout(efboSystemFrame.getContentPane());
		groupLayout.setHorizontalGroup
		(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(47)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(btnStepI, GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(btnStepII, 0, 522, Short.MAX_VALUE)
							.addGap(1))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(btnStepIII, 0, 0, Short.MAX_VALUE)
							.addGap(1))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 136, GroupLayout.PREFERRED_SIZE)
							.addComponent(lblMmxviiFahim)
							.addGap(157))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnStepV, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 520, Short.MAX_VALUE)
								.addComponent(btnStepIV, Alignment.LEADING, 0, 0, Short.MAX_VALUE))
							.addGap(2)))
					.addGap(46))
		);
		
		groupLayout.setVerticalGroup
		(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(39)
					.addComponent(btnStepI, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStepII, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStepIII, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStepIV, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStepV, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(lblMmxviiFahim)
					.addContainerGap(21, Short.MAX_VALUE))
		);
		efboSystemFrame.getContentPane().setLayout(groupLayout);
	}
	
}
