package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JViewport;

import thread.ThreadA;

public class UserInterface extends JFrame implements ActionListener{
	private JPanel principalPanel;
	private JPanel prepareButtonPanel;
	private JPanel interfacePanel;
	private JPanel addRemoveButtonPanel;
	private JPanel upDownButtonPanel;
	private JScrollPane pluginsPane;
	private JScrollPane selectedPluginsPane;
	private JButton addBtn;
	private JButton removeBtn;
	private JButton upBtn;
	private JButton downBtn;
	private JButton prepareBtn;
	private JButton refreshBtn;
	private JList<String> availablePluginsList;
	private JList<String> selectedPluginsList;
	private DefaultListModel<String> modelAvailable;
	private DefaultListModel<String> modelSelected;
	private ArrayList<String> sequence;
	private ThreadA thread;
	
	public UserInterface() {
		this.setTitle("Pizzaria Armengue");
		this.principalPanel = new JPanel(new BorderLayout());
		this.prepareButtonPanel = new JPanel();
		this.prepareButtonPanel.setLayout(new BoxLayout(prepareButtonPanel, BoxLayout.LINE_AXIS));
		this.interfacePanel = new JPanel();
		this.modelAvailable = new DefaultListModel<String>();
		this.modelSelected = new DefaultListModel<String>();
		this.availablePluginsList = new JList<String>(modelAvailable);
		this.selectedPluginsList = new JList<String>(modelSelected);
		this.addRemoveButtonPanel = new JPanel(new GridLayout(0,1));
		this.upDownButtonPanel = new JPanel(new GridLayout(0,1));
		this.pluginsPane = new JScrollPane(availablePluginsList);
		this.pluginsPane.setBorder(BorderFactory.createTitledBorder("Disponiveis"));
		this.selectedPluginsPane = new JScrollPane(selectedPluginsList);
		this.selectedPluginsPane.setBorder(BorderFactory.createTitledBorder("Selecionados"));
		this.addBtn = new JButton("PUT");
		this.removeBtn = new JButton("REMOVE");
		this.upBtn = new JButton("UP");
		this.downBtn = new JButton("DOWN");
		this.prepareBtn = new JButton("PREPARE");
		this.refreshBtn = new JButton("REFRESH");
		this.sequence = new ArrayList<String>();
		
		this.addBtn.addActionListener(this);
		this.removeBtn.addActionListener(this);
		this.upBtn.addActionListener(this);
		this.downBtn.addActionListener(this);
		this.prepareBtn.addActionListener(this);
		this.refreshBtn.addActionListener(this);
		this.upDownButtonPanel.add(upBtn);
		this.upDownButtonPanel.add(downBtn);
		this.addRemoveButtonPanel.add(addBtn);
		this.addRemoveButtonPanel.add(removeBtn);
		this.prepareButtonPanel.add(refreshBtn);
		this.prepareButtonPanel.add(Box.createHorizontalGlue());
		this.prepareButtonPanel.add(prepareBtn);
		
	
		this.interfacePanel.add(pluginsPane);
		this.interfacePanel.add(addRemoveButtonPanel);
		this.interfacePanel.add(selectedPluginsPane);
		this.interfacePanel.add(upDownButtonPanel);
		
		this.principalPanel.add(interfacePanel, BorderLayout.CENTER);
		this.principalPanel.add(prepareButtonPanel, BorderLayout.PAGE_END);
		this.availablePluginsList.setPreferredSize(new Dimension(200, 300));
		this.selectedPluginsList.setPreferredSize(new Dimension(200, 300));
		this.add(principalPanel);
		
		this.pack();
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	public void addList(String[] values) {
		for(int i = 0; i< values.length; i++)
			this.modelAvailable.addElement(values[i].split("Decorator")[0]);		
	}
	public void setThread(ThreadA t) {
		this.thread=t;
	}
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource() == this.addBtn) {
			if(this.availablePluginsList.getSelectedValue() != null) {
				this.modelSelected.addElement(this.availablePluginsList.getSelectedValue());
			}
		}
		if(e.getSource() == this.removeBtn) {
			if(this.selectedPluginsList.getSelectedValue() != null) {
				this.modelSelected.removeElement(this.selectedPluginsList.getSelectedValue());
				this.selectedPluginsList.setSelectedIndex(this.selectedPluginsList.getSelectedIndex()+1);
			}
		}
		if(e.getSource() == this.upBtn) {
			if(this.selectedPluginsList.getSelectedValue()!= null
					&& this.selectedPluginsList.getSelectedIndex() > 0) {
				
				int index = this.selectedPluginsList.getSelectedIndex() - 1;
				this.modelSelected.insertElementAt(this.selectedPluginsList.getSelectedValue(), 
						index);
				this.modelSelected.removeElementAt(this.selectedPluginsList.getSelectedIndex());
				this.selectedPluginsList.setSelectedIndex(index);
			}
		}
		if(e.getSource() == this.downBtn) {
			if(this.selectedPluginsList.getSelectedValue()!= null
					&& this.selectedPluginsList.getSelectedIndex() < this.modelSelected.getSize()-1) {
				
				int index = this.selectedPluginsList.getSelectedIndex() + 1;
				String element = this.selectedPluginsList.getSelectedValue();
				this.modelSelected.removeElementAt(this.selectedPluginsList.getSelectedIndex());
				this.modelSelected.insertElementAt(element, index);
				this.selectedPluginsList.setSelectedIndex(index);
			}
		}
		if(e.getSource() == this.prepareBtn) {
			this.sequence.removeAll(this.sequence);
			if(this.modelSelected.size() > 0) {
				for(int i = 0; i < this.modelSelected.size(); i++) {
					this.sequence.add(this.modelSelected.get(i));
				}
				this.thread.release();
				this.dispose();
			}
		}
		if(e.getSource() == this.refreshBtn) {
			this.modelAvailable.removeAllElements();;
			this.modelSelected.removeAllElements();;
			this.sequence.add("Refresh");
			this.thread.release();
		}
	}
	public ArrayList<String> getSequence(){
		return this.sequence;
	}
}
