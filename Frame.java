package minesweeper;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Frame {

	private JFrame a; // Create a blank window
	private JPanel panel; // Create a panel
	private JPanel recordPanel;//put Two textfiled
	private JTextField restLabelLei;//the number of rest mine
	private JTextField time;//Ê±¼ä
	private JPanel playPanel;
	private JButton[] button;
	private JButton start;
	private int buttonNum;
	
	private JMenuBar menuBar;
    private JMenuItem level1;
    private JMenuItem level2;
    private JMenuItem level3;
    private JMenuItem record;
    private JMenuItem exitMenuItem;
    private JMenuItem helpMenuItem;
    
    private CardLayout card;
    private JPanel cardpanel;
    private JTable table;
    private DefaultTableModel tableModel;
    private JScrollPane recordjsp;//the fourth level's table's scroll bar
	private Object[][] data= {};//the array is to store three data types: game level and game time
	private String[] cloumnName={"level","Time"};//to name the table header
    private JTextArea helptext;
    private JScrollPane helpSP;
	public Frame(int n)
	{
		a = new JFrame(); // Create a blank window
		panel = new JPanel(new BorderLayout()); // Create a panel
		panel.setPreferredSize(new Dimension(50*n,50*n+50));
		recordPanel=new JPanel(new GridLayout(1,3));
		recordPanel.setPreferredSize(new Dimension(50*n,50));
		
		restLabelLei = new JTextField();
		restLabelLei.setPreferredSize(new Dimension(50*n-15,50));
		restLabelLei.setFont(new Font("Arial",Font.BOLD,15));
		restLabelLei.setHorizontalAlignment(JTextField.CENTER);
		restLabelLei.setEditable(false);
		recordPanel.add("West",restLabelLei);
		start = new JButton("Play");
		start.setPreferredSize(new Dimension(30,50));
		recordPanel.add("Center",start);
		time = new JTextField();
		time.setPreferredSize(new Dimension(50*n-15,50));
		time.setFont(new Font("Arial",Font.BOLD,13));
		time.setHorizontalAlignment(JTextField.CENTER);
		time.setEditable(false);
		recordPanel.add("East",time);
		
		panel.add("North",recordPanel);
		playPanel=new JPanel(new GridLayout(n,n));
		playPanel.setPreferredSize(new Dimension(50*n,50*n));
		buttonNum=n*n;
		button = new JButton[buttonNum];
		for(int i=0;i<n*n;i++)
		{
			button[i]=new JButton();
			button[i].setBackground(new Color(220,220,220));
			button[i].setPreferredSize(new Dimension(50,50));
			playPanel.add(button[i]);
		}
		panel.add("Center",playPanel);
		
		menuBar = new JMenuBar();
		JMenu begin = new JMenu("Menu");
        JMenu help = new JMenu("Help");
        menuBar.add(begin);
        menuBar.add(help);
        level1 = new JMenuItem("Level1");
        level2 = new JMenuItem("Level2");
        level3 = new JMenuItem("Level3");
        record = new JMenuItem("Record");
        exitMenuItem = new JMenuItem("Exit");
        helpMenuItem = new JMenuItem("Help");
        level1.setHorizontalAlignment(SwingConstants.CENTER);
        level2.setHorizontalAlignment(SwingConstants.CENTER);
        level3.setHorizontalAlignment(SwingConstants.CENTER);
        record.setHorizontalAlignment(SwingConstants.CENTER);
        exitMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
        helpMenuItem.setHorizontalAlignment(SwingConstants.CENTER);
        begin.add(level1);
        begin.add(level2);
        begin.add(level3);
        begin.add(record);
        begin.add(exitMenuItem);
        help.add(helpMenuItem);      
        a.setJMenuBar(menuBar);
        
        card = new CardLayout();
        cardpanel = new JPanel(card);

        tableModel=new DefaultTableModel(data,cloumnName);
		table= new JTable(tableModel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setEnabled(false);
		table.setAutoCreateRowSorter(true);
		table.setShowGrid(false);
		DefaultTableCellRenderer render = new DefaultTableCellRenderer();     
		render.setHorizontalAlignment(JLabel.CENTER);// to let the data display in the center   
	    table.setDefaultRenderer(Object.class, render);
	    table.setFont(new Font("Arial",Font.BOLD,15));
	    table.setRowHeight(n*4);
	    recordjsp=new JScrollPane(table);//to set scroll car
		recordjsp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        helptext = new JTextArea();
        helptext.setFont(new Font("Arial",Font.BOLD,13));
        helptext.setEditable(false);
        helpSP= new JScrollPane(helptext);//make TextArea warningTA has a scroll bar
    	helpSP.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    	helptext.setLineWrap(true);
    	helptext.setWrapStyleWord(true);
        cardpanel.add(panel,"gamepanel");
        cardpanel.add(recordjsp,"recordtable");
        cardpanel.add(helpSP,"helptext");
        
		a.setContentPane(cardpanel); // Use panel on Window
		a.setTitle("Saolei"); // Change window title
		a.setSize(50*n,50*n+50); // Change window size
		a.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		a.setLocationRelativeTo(null);
		a.setVisible(true);
	}
	public void reset(int n)
	{
		panel.setPreferredSize(new Dimension(50*n,50*n+50));
		recordPanel.setPreferredSize(new Dimension(50*n,50));
		restLabelLei.setPreferredSize(new Dimension(50*n-15,50));
		time.setPreferredSize(new Dimension(50*n-15,50));
		table.setRowHeight(n*4);
		playPanel.setLayout(new GridLayout(n,n));
		playPanel.setPreferredSize(new Dimension(50*n,50*n));
		for(int i=0;i<buttonNum;i++)
		{
			playPanel.remove(button[i]);
		}
		buttonNum=n*n;
		button=null;
		button = new JButton[buttonNum];
		for(int i=0;i<buttonNum;i++)
		{
			button[i]=new JButton();
			button[i].setBackground(new Color(220,220,220));
			button[i].setPreferredSize(new Dimension(50,50));
			playPanel.add(button[i]);
		}
		a.setSize(50*n,50*n+50); // Change window size
	}
	public JButton[] getButtons()
	{
		return button;
	}
	public JButton getStart()
	{
		return start;
	}
	public JTextField getTimeText()
	{
		return time;
	}
	public JTextField getRecordText()
	{
		return this.restLabelLei;
	}
	public JMenuItem getLevel1()
	{
		return level1;
	}
	public JMenuItem getLevel2()
	{
		return level2;
	}
	public JMenuItem getLevel3()
	{
		return level3;
	}
	public JMenuItem getRecord()
	{
		return record;
	}
	public CardLayout getCard() {
		return card;
	}
	public JPanel getCardPanel() {
		return cardpanel;
	}
	public JTextArea getHelptext(){
		return helptext;
	}
	public JMenuItem getexit()
	{
		return this.exitMenuItem;
	}
	public JMenuItem gethelp()
	{
		return this.helpMenuItem;
	}
	public DefaultTableModel getTableModel(){
		return tableModel;
	}
}
