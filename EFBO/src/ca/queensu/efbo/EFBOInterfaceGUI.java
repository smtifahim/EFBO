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

public class EFBOInterfaceGUI {

	private JFrame frmTheEfboSystem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EFBOInterfaceGUI window = new EFBOInterfaceGUI();
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
	public EFBOInterfaceGUI() {
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
		frmTheEfboSystem.setBounds(100, 100, 713, 383);
		frmTheEfboSystem.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JButton btnStepI = new JButton("STEP I.   LOAD the FIRST System's Knowledge.");
		btnStepI.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepI.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStepI.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JButton btnStepII = new JButton("STEP II.  LOAD the SECOND System's Knowledge.");
		btnStepII.setEnabled(false);
		btnStepII.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStepII.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepII.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JButton btnStepIV = new JButton("STEP IV.  MERGE the 1st + 2nd System's Knowledge.");
		btnStepIV.setEnabled(false);
		btnStepIV.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepIV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton btnStepIII = new JButton("STEP III. LOAD the EFBO-Validation Ontology.          ");
		btnStepIII.setEnabled(false);
		btnStepIII.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepIII.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStepIII.setFont(new Font("DialogInput", Font.BOLD, 16));
		btnStepIV.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JButton btnStepV = new JButton("STEP V.\t   IDENTIFY the MAPPING Events.");
		btnStepV.setIcon(null);
		btnStepV.setEnabled(false);
		btnStepV.setHorizontalAlignment(SwingConstants.LEFT);
		btnStepV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnStepV.setFont(new Font("DialogInput", Font.BOLD, 16));
		
		JLabel lblMmxviiFahim = new JLabel("MMXVII \u00A9 Fahim T. Imam. All Rights Reserved.");
		lblMmxviiFahim.setHorizontalAlignment(SwingConstants.CENTER);
		lblMmxviiFahim.setForeground(UIManager.getColor("Button.darkShadow"));
		lblMmxviiFahim.setFont(new Font("Lucida Grande", Font.PLAIN, 10));
		GroupLayout groupLayout = new GroupLayout(frmTheEfboSystem.getContentPane());
		groupLayout.setHorizontalGroup(
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
		groupLayout.setVerticalGroup(
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
		frmTheEfboSystem.getContentPane().setLayout(groupLayout);
	}
}
