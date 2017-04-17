package ca.queensu.efbo;

import java.awt.EventQueue;

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

public class EFBOSystemInterface {

	private JFrame frmTheEfboSystem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EFBOSystemInterface window = new EFBOSystemInterface();
					window.frmTheEfboSystem.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EFBOSystemInterface() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTheEfboSystem = new JFrame();
		frmTheEfboSystem.setBackground(Color.BLACK);
		frmTheEfboSystem.getContentPane().setBackground(UIManager.getColor("Button.highlight"));
		frmTheEfboSystem.setTitle("The EFBO System Interface");
		frmTheEfboSystem.setBounds(100, 100, 633, 383);
		frmTheEfboSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnStepI = new JButton("STEP I.    LOAD the FIRST System's Knowledge.");
		btnStepI.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStepI.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		JButton btnStepIi = new JButton("STEP II.   LOAD the SECOND System's Knowledge.");
		btnStepIi.setEnabled(false);
		btnStepIi.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStepIi.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepIi.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		JButton btnStepIv = new JButton("STEP IV.  MERGE the 1st + 2nd System's Knowledge.");
		btnStepIv.setEnabled(false);
		btnStepIv.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepIv.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnStepIii = new JButton("STEP III.  LOAD the EFBO-Validation Ontology.          ");
		btnStepIii.setEnabled(false);
		btnStepIii.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepIii.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStepIii.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		btnStepIv.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		JButton btnStepV = new JButton("STEP V.   IDENTIFY the MAPPING Events.");
		btnStepV.setEnabled(false);
		btnStepV.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStepV.setFont(new Font("Lucida Grande", Font.BOLD, 16));
		
		JLabel label = new JLabel("MMXVII © Fahim T. Imam. All Rights Reserved.");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(UIManager.getColor("Button.darkShadow"));
		label.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GroupLayout groupLayout = new GroupLayout(frmTheEfboSystem.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(47)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnStepV, GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(btnStepI, GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(1)
							.addComponent(btnStepIi, 0, 538, Short.MAX_VALUE)
							.addGap(1))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(btnStepIii, 0, 0, Short.MAX_VALUE)
							.addGap(1))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(2)
							.addComponent(btnStepIv, 0, 0, Short.MAX_VALUE)
							.addGap(2))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED, 158, GroupLayout.PREFERRED_SIZE)
							.addComponent(label)
							.addGap(157)))
					.addGap(46))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, groupLayout.createSequentialGroup()
					.addGap(39)
					.addComponent(btnStepI, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStepIi, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStepIii, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStepIv, GroupLayout.PREFERRED_SIZE, 47, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(btnStepV, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addGap(12)
					.addComponent(label)
					.addContainerGap(39, Short.MAX_VALUE))
		);
		frmTheEfboSystem.getContentPane().setLayout(groupLayout);
	}
}
