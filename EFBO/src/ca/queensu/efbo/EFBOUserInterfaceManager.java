package ca.queensu.efbo;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

import javax.swing.UIManager;
import javax.swing.JProgressBar;

import javax.swing.JPanel;

import java.awt.FlowLayout;
import java.awt.SystemColor;

public class EFBOUserInterfaceManager 
{
	public JFrame efboSystemFrame;
	private EFBOComparatorManager efboCompManager = new EFBOComparatorManager();
	
	private JButton btnStepI   = new JButton("STEP   I. LOAD the FIRST System's Knowledge.");
	private JButton btnStepII  = new JButton("STEP  II. LOAD the SECOND System's Knowledge.");
	private JButton btnStepIII = new JButton("STEP III. LOAD the EFBO-Validation Ontology.");
	private JButton btnStepIV  = new JButton("STEP  IV. MERGE the 1st + 2nd System's Knowledge.");
	private JButton btnStepV   = new JButton("STEP   V. IDENTIFY the MAPPING Events.");
	private Font textFont = new Font(Font.MONOSPACED, Font.BOLD, 14);
	public static JProgressBar progressBar; 
	private final JPanel firstPanel = new JPanel();
	private final JPanel secondPanel = new JPanel();
	
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
		//EFBOUserInterfaceManager.progressBar.setValue(25);
		viewBar();
		
		btnStepI.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				progressBar.setValue(5);
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

		btnStepII.addActionListener(new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				progressBar.setValue(5);
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

		btnStepV.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				System.out.println(btnStepV.getText());
			}
		});
	}
	
	
	public void viewBar() 
	{
		  progressBar.setValue(0);

		  int timerDelay = 10;
		  new javax.swing.Timer(timerDelay, new ActionListener() 
		  {
		     private int index = 0;
		     private int maxIndex = 100;
		     public void actionPerformed(ActionEvent e) 
		     {
		        if (index < maxIndex)
		        {
		           progressBar.setValue(index);
		           index++;
		        } 
		        
		        else 
		        {
		           progressBar.setValue(maxIndex);
		           ((javax.swing.Timer)e.getSource()).stop(); // stop the timer
		        }
		     }
		  }).start();

		  progressBar.setValue(progressBar.getMinimum());
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initializeGUIElements() 
	{
		efboSystemFrame = new JFrame("The EFBO System Interface");
		efboSystemFrame.setBackground(Color.BLACK);
		efboSystemFrame.getContentPane().setBackground(UIManager.getColor("Button.highlight"));
		efboSystemFrame.setBounds(100, 100, 532, 404);
		efboSystemFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		btnStepII.setEnabled(false); 
		btnStepIII.setEnabled(false);
		btnStepIV.setEnabled(false);
		btnStepV.setEnabled(false);
		
		btnStepI.setBounds(43, 54, 432, 39);
		btnStepI.setBackground(SystemColor.menu);
		btnStepI.setFont(textFont);		
		
		btnStepII.setBounds(43, 93, 432, 39);
		btnStepII.setBackground(SystemColor.menu);
		btnStepII.setFont(textFont);
		
		btnStepIII.setBounds(43, 132, 432, 39);
		btnStepIII.setBackground(SystemColor.menu);
		btnStepIII.setFont(textFont);
		
		btnStepIV.setBounds(43, 171, 432, 39);
		btnStepIV.setBackground(SystemColor.menu);
		btnStepIV.setFont(textFont);
		
		btnStepV.setBounds(43, 210, 432, 39);
		btnStepV.setBackground(SystemColor.menu);
		btnStepV.setFont(textFont);
		
		btnStepI.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepII.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepIII.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepIV.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepV.setHorizontalAlignment(SwingConstants.LEFT);
		efboSystemFrame.getContentPane().setLayout(null);
		efboSystemFrame.getContentPane().add(btnStepI);
		efboSystemFrame.getContentPane().add(btnStepII);
		efboSystemFrame.getContentPane().add(btnStepIII);
		efboSystemFrame.getContentPane().add(btnStepIV);
		efboSystemFrame.getContentPane().add(btnStepV);
		FlowLayout flowLayout = (FlowLayout) secondPanel.getLayout();
		flowLayout.setAlignOnBaseline(true);
		secondPanel.setBounds(43, 262, 432, 39);
		secondPanel.setBackground(Color.WHITE);
		secondPanel.setBorder(null);
		
		efboSystemFrame.getContentPane().add(secondPanel);
		firstPanel.setBorder(null);
		secondPanel.add(firstPanel);
		firstPanel.setBackground(Color.WHITE);
		progressBar = new JProgressBar();
		progressBar.setFont(new Font("Tahoma", Font.PLAIN, 12));
		progressBar.setStringPainted(true);
		firstPanel.add(progressBar);
		progressBar.setForeground(Color.GREEN);
		progressBar.setBackground(Color.BLACK);
		
		JLabel lblMmxviiFahim = new JLabel("MMXVII \u00A9 Fahim T. Imam. All Rights Reserved.");
		lblMmxviiFahim.setBounds(43, 301, 432, 39);
		lblMmxviiFahim.setBackground(SystemColor.menu);
		lblMmxviiFahim.setHorizontalAlignment(SwingConstants.CENTER);
		lblMmxviiFahim.setForeground(Color.GRAY);
		lblMmxviiFahim.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		efboSystemFrame.getContentPane().add(lblMmxviiFahim);
	}
	
	
} // End of Class EFBOUserInterfaceManager.
