package ca.queensu.efbo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTree;
import javax.swing.JList;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EFBOMappingEventsManager extends JFrame {

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EFBOMappingEventsManager frame = new EFBOMappingEventsManager();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public EFBOMappingEventsManager() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 521);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Mapping Selection", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[219px,grow,center][219px,grow][]", "[254px][grow][]"));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		contentPane.add(scrollPane_1, "cell 0 0,grow");
		
		JTree tree = new JTree();
		tree.setBorder(new TitledBorder(null, "System-I Events", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_1.setViewportView(tree);
		tree.setShowsRootHandles(true);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		contentPane.add(scrollPane_2, "cell 1 0,grow");
		
		JTree tree_1 = new JTree();
		tree_1.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "System-II Events", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), "System-II Events", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		scrollPane_2.setViewportView(tree_1);
		tree_1.setShowsRootHandles(true);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "cell 0 1 2 1,grow");
		
		JList list = new JList();
		list.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "List of Mapping Evenets between System-I and System-II", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
		scrollPane.setViewportView(list);
		
		JButton btnNewButton = new JButton("Map Selected Events");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		scrollPane.setColumnHeaderView(btnNewButton);
		
		JList list_1 = new JList();
		contentPane.add(list_1, "cell 2 1");
		
		JButton btnNewButton_1 = new JButton("Add the Mappings Above to the EFBO-V Ontology");
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		contentPane.add(btnNewButton_1, "cell 0 2 2 1,growx");
	}

}
