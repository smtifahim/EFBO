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
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.semanticweb.owlapi.model.OWLNamedIndividual;

import javax.swing.border.EtchedBorder;
import java.awt.Color;
import net.miginfocom.swing.MigLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.awt.event.ActionEvent;

public class EFBOMappingEventsManager extends JFrame
{

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	
	private JTree firstTree;
	DefaultMutableTreeNode firstTreeRoot = new DefaultMutableTreeNode("System-I Event");
	
	private JTree secondTree;
	DefaultMutableTreeNode secondTreeRoot = new DefaultMutableTreeNode("System-II Event");
	
	private JList mappingList;
	
	private Map<OWLNamedIndividual, String> firstSystemEvents = new HashMap <OWLNamedIndividual, String>();
	private Map<OWLNamedIndividual, String> secondSystemEvents = new HashMap <OWLNamedIndividual, String>();

	
//	public static void main(String [] args) 
//	{
//		EventQueue.invokeLater(new Runnable() 
//		{
//			public void run() 
//			{
//				try 
//				{
//					EFBOMappingEventsManager frame = new EFBOMappingEventsManager();
//					frame.setVisible(true);
//				} 
//				
//				catch (Exception e) 
//				{
//					e.printStackTrace();
//				}
//			}
//		});
//	}

	public EFBOMappingEventsManager() 
	{
	 //this.setTreeElements();	
	 //this.setGUIElements();
		firstSystemEvents = null;
		secondSystemEvents=null;
	 //this.setVisible(true);
	}
	
	
	public void populateTreeElements ()
	{
		for (Entry<OWLNamedIndividual, String> e : firstSystemEvents.entrySet())
		{ 
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(e.getValue());
			firstTreeRoot.add(childNode);
		}
		
		for (Entry<OWLNamedIndividual, String> e : secondSystemEvents.entrySet())
		{ 
			DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(e.getValue());
			secondTreeRoot.add(childNode);
		}
				
		firstTree = new JTree(firstTreeRoot);
		secondTree = new JTree(secondTreeRoot);
	
		this.setGUIElements();
	}
	
	private void setGUIElements()
	{
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 521);
	contentPane = new JPanel();
	contentPane.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), "Mapping Selection", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
	setContentPane(contentPane);
	contentPane.setLayout(new MigLayout("", "[219px,grow,center][219px,grow][]", "[254px][grow][]"));
	
	JScrollPane scrollPane_1 = new JScrollPane();
	contentPane.add(scrollPane_1, "cell 0 0,grow");
	
	
	
	
	//firstTree.setBorder(new TitledBorder(null, "System-I Events", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	scrollPane_1.setViewportView(firstTree);
	firstTree.setShowsRootHandles(true);
	
	JScrollPane scrollPane_2 = new JScrollPane();
	contentPane.add(scrollPane_2, "cell 1 0,grow");
	
//	secondTree.setBorder(new TitledBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
//			             "System-II Events", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)), 
//			             "System-II Events", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	scrollPane_2.setViewportView(secondTree);
	secondTree.setShowsRootHandles(true);
	
	JScrollPane scrollPane = new JScrollPane();
	contentPane.add(scrollPane, "cell 0 1 2 1,grow");
	
	mappingList = new JList();
	mappingList.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
			       "List of Mapping Evenets between System-I and System-II", 
			       TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
	
	scrollPane.setViewportView(mappingList);
	
	JButton btnNewButton = new JButton("Map Selected Events");
	btnNewButton.addActionListener(new ActionListener() 
	{
		public void actionPerformed(ActionEvent e)
		{
		}
	});
	scrollPane.setColumnHeaderView(btnNewButton);
	
	JList list_1 = new JList();
	contentPane.add(list_1, "cell 2 1");
	
	JButton btnNewButton_1 = new JButton("Add the Mappings Above to the EFBO-V Ontology");
	btnNewButton_1.addActionListener(new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
		}
	});
	contentPane.add(btnNewButton_1, "cell 0 2 2 1,growx");
}

	/**
	 * @return the firstSystemEvents
	 */
	public Map<OWLNamedIndividual, String> getFirstSystemEvents() 
	{
		return firstSystemEvents;
	}

	/**
	 * @param firstSystemEvents2 the firstSystemEvents to set
	 */
	public void setFirstSystemEvents(Map<OWLNamedIndividual, String> firstSystemEvents2)
	{
		this.firstSystemEvents = firstSystemEvents2;
	}

	/**
	 * @return the secondSystemEvents
	 */
	public Map<OWLNamedIndividual, String> getSecondSystemEvents() 
	{
		return secondSystemEvents;
	}

	/**
	 * @param secondSystemEvents2 the secondSystemEvents to set
	 */
	public void setSecondSystemEvents(Map<OWLNamedIndividual, String> secondSystemEvents2)
	{
		this.secondSystemEvents = secondSystemEvents2;
	}
	
}
