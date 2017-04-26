package ca.queensu.efbo;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JTree;
import javax.swing.JList;

import org.semanticweb.owlapi.model.OWLNamedIndividual;

import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionListener;

import java.awt.Color;
import java.awt.Container;
import javax.swing.border.*;

import net.miginfocom.swing.MigLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import java.awt.List;
import java.awt.Button;

public class EFBOMappingEventsManager extends JFrame
{

	private JPanel contentPane;

	DefaultListModel<String> listModel = new DefaultListModel<>();
	private JList mappingList = new JList(listModel);
	
	DefaultListModel<String> firstSystemModel = new DefaultListModel<>();
	JList firstSystemEventList = new JList(firstSystemModel);
	
	
	
	DefaultListModel<String> secondSystemModel = new DefaultListModel<>();
	JList secondSystemEventList = new JList(secondSystemModel);	
	
	
	private Set<OWLNamedIndividual> firstSystemEvents;
	private Set<OWLNamedIndividual> secondSystemEvents;
	
	private EFBOOntologyManager efboOntologyManager;
	private ArrayList <EFBOMappingEvents> mappingEvents = new ArrayList <EFBOMappingEvents>();

	ArrayList <OWLNamedIndividual> events = new ArrayList <OWLNamedIndividual>();
	ArrayList <OWLNamedIndividual> events2= new ArrayList <OWLNamedIndividual>();
	int lm =0;
	int me =0;
	public EFBOMappingEventsManager() 
	{
		firstSystemEvents = null;
		secondSystemEvents = null;
		efboOntologyManager = null;
		//mappingEvents = new ArrayList <EFBOMappingEvents>();
		
	}
	
	public EFBOMappingEventsManager(Set<OWLNamedIndividual> firstSystemEvents,  
									Set<OWLNamedIndividual> secondSystemEvents, 
									EFBOOntologyManager efboOntologyManager ) 
	{
		this.setFirstSystemEvents(firstSystemEvents);
		this.setSecondSystemEvents(secondSystemEvents);
		this.setEFBOOntologyManager(efboOntologyManager);
		populateListElements();
		this.setGUIElements();
		this.setVisible(true);
	}
	
	public void populateListElements()
	{
		int i =0;
		for (OWLNamedIndividual e : firstSystemEvents)
		{ 
			String eventLabel = this.efboOntologyManager.getLabel(e);
			firstSystemModel.add(i, eventLabel);
			events.add(i, e);
			i++;			
		}
		
		int j =0;
		
		for (OWLNamedIndividual e : secondSystemEvents)
		{ 
			String eventLabel = this.efboOntologyManager.getLabel(e);
			secondSystemModel.add(j, eventLabel);
			events2.add(j, e);
			j++;	
		}
			
		this.setGUIElements();
	}
	
	

	private void setGUIElements()
	{
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	setBounds(100, 100, 450, 521);
	contentPane = new JPanel();
	contentPane.setBorder(null);
	setContentPane(contentPane);
	contentPane.setLayout(new MigLayout("", "[219px,grow,center][219px,grow]", "[254px,grow][grow][]"));

	
	JPanel panel = new JPanel();
	panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "System I Events", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
	contentPane.add(panel, "cell 0 0,grow");
	panel.setLayout(new MigLayout("", "[219px,grow,center]", "[254px,grow]"));
	
	JScrollPane scrollPane_1 = new JScrollPane();
	panel.add(scrollPane_1, "cell 0 0,grow");
	
	
	scrollPane_1.setViewportView(firstSystemEventList);
	
	JPanel panel_1 = new JPanel();
	panel_1.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "System II Events", TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
	contentPane.add(panel_1, "cell 1 0,grow");
	panel_1.setLayout(new MigLayout("", "[219px,grow]", "[254px,grow]"));
	
	JScrollPane scrollPane_2 = new JScrollPane();
	panel_1.add(scrollPane_2, "cell 0 0,grow");
	
	scrollPane_2.setViewportView(secondSystemEventList);
	
	JScrollPane scrollPane = new JScrollPane();
	contentPane.add(scrollPane, "cell 0 1 2 1,grow");
	
	
	mappingList.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null), 
			       "List of Mapping Evenets between System-I and System-II", 
			       TitledBorder.CENTER, TitledBorder.TOP, null, new Color(0, 0, 0)));
	
	scrollPane.setViewportView(mappingList);
	
	JButton btnNewButton = new JButton("Map Selected Events");
	
	btnNewButton.addActionListener(new ActionListener() 
	{
		public void actionPerformed(ActionEvent e)
		{
			//String system1Value = firstSystemEventList.getSelectedValue().toString();
			//String system2Value = secondSystemEventList.getSelectedValue().toString();
			
			int i =  firstSystemEventList.getSelectedIndex();
			int j =  secondSystemEventList.getSelectedIndex();
			
			mappingEvents.add(new EFBOMappingEvents(events.get(i), events2.get(j), efboOntologyManager));
					
			listModel.add(lm, mappingEvents.get(me).getEFBOMappingEvents());
			
			//firstSystemModel.setElementAt("mapped", i);
			//secondSystemModel.setElementAt("mapped", j);
			
			firstSystemModel.set(i, "mapped");
			secondSystemModel.set(j, "mapped");
			//secondSystemModel.removeElement(secondSystemEventList.getSelectedValue());
			
			lm++;
			me++;
						
		}
	});
	scrollPane.setColumnHeaderView(btnNewButton);
	
//	JList list_1 = new JList();
//	contentPane.add(list_1, "cell 2 1");
	
	JButton btnNewButton_1 = new JButton("Add the Mappings Above to the EFBO-V Ontology");
	btnNewButton_1.addActionListener(new ActionListener() 
	{
		public void actionPerformed(ActionEvent e) 
		{
			for (EFBOMappingEvents events: mappingEvents)
			{
				events.setMappingEvents();
			}
			
		}
	});
	contentPane.add(btnNewButton_1, "cell 0 2 2 1,growx");
}

	/**
	 * @return the firstSystemEvents
	 */
	public Set<OWLNamedIndividual> getFirstSystemEvents() 
	{
		return firstSystemEvents;
	}

	/**
	 * @param firstSystemEvents2 the firstSystemEvents to set
	 */
	public void setFirstSystemEvents(Set<OWLNamedIndividual> firstSystemEvents)
	{
		this.firstSystemEvents = firstSystemEvents;
	}

	
	
	/**
	 * @return the secondSystemEvents
	 */
	public Set<OWLNamedIndividual> getSecondSystemEvents() 
	{
		return secondSystemEvents;
	}

	/**
	 * @param secondSystemEvents2 the secondSystemEvents to set
	 */
	public void setSecondSystemEvents(Set<OWLNamedIndividual> secondSystemEvents2)
	{
		this.secondSystemEvents = secondSystemEvents2;
	}

	/**
	 * @return the efboOntologyManager
	 */
	public EFBOOntologyManager getEFBOOntologyManager() 
	{
		return efboOntologyManager;
	}

	/**
	 * @param efboOntologyManager the efboOntologyManager to set
	 */
	public void setEFBOOntologyManager(EFBOOntologyManager efboOntologyManager) 
	{
		this.efboOntologyManager = efboOntologyManager;
	}

	/**
	 * @return the mappingEvents
	 */
	public ArrayList <EFBOMappingEvents> getMappingEvents() 
	{
		return mappingEvents;
	}

	
}


