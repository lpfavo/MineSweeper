package minesweeper;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;

import javax.swing.*;
import java.util.Timer;
import java.util.TimerTask;

public class Play implements MouseListener{

	private Put put;
	private int buttonRow;
	private int leiNumber;
	private int winofLei;
	private boolean[] checked;
	private boolean[] putFlag;
	private int[] leiPosition;//每个位置周围雷的数量和自己是否是雷
	private boolean StartToPlay;
	private int currentLevel;
	private boolean lost;
	private int win;
	private long begintime;
	private long minutes;
	private long second;
	private long lasttime;
	private boolean stop=false;
	private boolean firstClick;
	private Timer timer1;
	public Play (int n,int num)
	{
		timer1 = new Timer();
		put= new Put(n);
		buttonRow=n;
		winofLei=num;
		leiNumber=num;
		checked= new boolean[n*n];	
		putFlag= new boolean[n*n];	
		leiPosition= new int[n*n];
		StartToPlay=false;
		lost=false;
		win=0;
		minutes=0;
		second=0;
		lasttime=0;
		begintime=0;
		firstClick=false;
		//timer1 = new Timer();
		for(int i=0;i<n*n;i++)
		{
			put.getframe().getButtons()[i].addMouseListener(this);

			checked[i]=false;
			putFlag[i]=false;
			leiPosition[i]=0;
		}
		put.getframe().getTimeText().setText("Time:"+"00"+":00");
		put.getframe().getRecordText().setText(this.winofLei+"");
		put.getframe().getStart().addMouseListener(this);
		for(int i=0;i<leiNumber;i++)
			leiPosition[put.getMinePosition()[i]]=-1;

		this.recordLeiNum();
		if(leiNumber==5)currentLevel=1;
		else if(leiNumber==10)currentLevel=2;
		else if(leiNumber==15)currentLevel=3;
		 put.getframe().getLevel1().addActionListener(new ActionListener() {//10
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					put.getframe().getCard().show(put.getframe().getCardPanel(),"gamepanel");
					replay(5,5);
				}
	        });
		 put.getframe().getLevel2().addActionListener(new ActionListener() {//15
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					put.getframe().getCard().show(put.getframe().getCardPanel(),"gamepanel");
					replay(10,10);
					
				}
	        });
		 put.getframe().getLevel3().addActionListener(new ActionListener() {//20
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					put.getframe().getCard().show(put.getframe().getCardPanel(),"gamepanel");
					replay(15,15);
				}
	        });
		 put.getframe().getexit().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					System.exit(0);
				}
	        });
		 put.getframe().gethelp().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						importHelpFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					put.getframe().getCard().show(put.getframe().getCardPanel(),"helptext");
					for(int i=0;i<put.getframe().getTableModel().getRowCount();i++)
						put.getframe().getTableModel().removeRow(i);
				}
	        });
		 put.getframe().getRecord().addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					try {
						importRecordTable();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					put.getframe().getCard().show(put.getframe().getCardPanel(),"recordtable");
				}
	        });
	}
	
	public void replay(int n,int num)
	{
		timer1 = new Timer();
		if(n!=buttonRow)
			put.reput1(n,num);
		else put.reput2(n,num);
		buttonRow=n;
		winofLei=num;
		leiNumber=num;
		StartToPlay=false;
		lost=false;
		win=0;
		minutes=0;
		second=0;
		lasttime=0;
		begintime=0;
		firstClick=false;
		checked= new boolean[n*n];	
		putFlag= new boolean[n*n];	
		leiPosition= new int[n*n];
		put.getframe().getRecordText().setText(this.leiNumber+"");
		for(int i=0;i<n*n;i++)
		{
			put.getframe().getButtons()[i].addMouseListener(this);
			checked[i]=false;
			putFlag[i]=false;
			leiPosition[i]=0;
		}
		
		if(leiNumber==5)currentLevel=1;
		else if(leiNumber==10)currentLevel=2;
		else if(leiNumber==15)currentLevel=3;
		//put.getframe().getStart().addMouseListener(this);
		for(int i=0;i<leiNumber;i++)
			leiPosition[put.getMinePosition()[i]]=-1;
		this.recordLeiNum();
		for(int i=0;i<n*n;i++)
		{
			System.out.print(leiPosition[i]+"  ");
			if((i+1)%buttonRow==0)
				System.out.println();
		}
	}
	public void showLeiNum(int i)
	{
		if(!checked[i])
		{
			if(i==0)//左上角
			{
				put.getframe().getButtons()[i].setText(this.leiPosition[i]+"");
				put.getframe().getButtons()[i].setBackground(Color.WHITE);
				checked[i]=true;
				if(leiPosition[i]==0)
				{
					this.showLeiNum(i+1);
					this.showLeiNum(i+buttonRow);
					this.showLeiNum(i+buttonRow+1);
				}
			}
			else if(i==buttonRow-1)//右上角
			{
				put.getframe().getButtons()[i].setText(this.leiPosition[i]+"");
				put.getframe().getButtons()[i].setBackground(Color.WHITE);
				checked[i]=true;
				if(leiPosition[i]==0)
				{
					this.showLeiNum(i-1);
					this.showLeiNum(i+buttonRow-1);
					this.showLeiNum(i+buttonRow);
				}
				
			}
			else if(i==buttonRow*(buttonRow-1))//左下角
			{
				put.getframe().getButtons()[i].setText(this.leiPosition[i]+"");
				put.getframe().getButtons()[i].setBackground(Color.WHITE);
				checked[i]=true;
				if(leiPosition[i]==0)
				{
					this.showLeiNum(i+1);
					this.showLeiNum(i-buttonRow+1);
					this.showLeiNum(i-buttonRow);
				}
			}
			else if(i==buttonRow*buttonRow-1)//右下角
			{
				put.getframe().getButtons()[i].setText(this.leiPosition[i]+"");
				put.getframe().getButtons()[i].setBackground(Color.WHITE);
				checked[i]=true;
				if(leiPosition[i]==0)
				{
					this.showLeiNum(i-1);
					this.showLeiNum(i-buttonRow);
					this.showLeiNum(i-buttonRow-1);
				}
			}
			else if(i<buttonRow-1&&i>0)//上侧
			{
				put.getframe().getButtons()[i].setText(this.leiPosition[i]+"");
				put.getframe().getButtons()[i].setBackground(Color.WHITE);
				checked[i]=true;
				if(leiPosition[i]==0)
				{
					this.showLeiNum(i+1);
					this.showLeiNum(i-1);
					this.showLeiNum(i+buttonRow-1);
					this.showLeiNum(i+buttonRow);
					this.showLeiNum(i+buttonRow+1);
				}
			}
			else if(i>buttonRow*(buttonRow-1)&&i<buttonRow*buttonRow-1)//下侧
			{
				put.getframe().getButtons()[i].setText(this.leiPosition[i]+"");
				put.getframe().getButtons()[i].setBackground(Color.WHITE);
				checked[i]=true;
				if(leiPosition[i]==0)
				{
					this.showLeiNum(i+1);
					this.showLeiNum(i-1);
					this.showLeiNum(i-buttonRow+1);
					this.showLeiNum(i-buttonRow);
					this.showLeiNum(i-buttonRow-1);
				}
			}

			else if(i%buttonRow==0&&i/buttonRow>0&&i/buttonRow<buttonRow-1)//左侧
			{
				put.getframe().getButtons()[i].setText(this.leiPosition[i]+"");
				put.getframe().getButtons()[i].setBackground(Color.WHITE);
				checked[i]=true;
				if(leiPosition[i]==0)
				{
					this.showLeiNum(i+1);
					this.showLeiNum(i-buttonRow);
					this.showLeiNum(i-buttonRow+1);
					this.showLeiNum(i+buttonRow);
					this.showLeiNum(i+buttonRow+1);
				}

			}
			else if(i%buttonRow==buttonRow-1&&i>buttonRow-1&&i<buttonRow*buttonRow-1)//右侧
			{
				put.getframe().getButtons()[i].setText(this.leiPosition[i]+"");
				put.getframe().getButtons()[i].setBackground(Color.WHITE);
				checked[i]=true;
				if(leiPosition[i]==0)
				{
					this.showLeiNum(i-1);
					this.showLeiNum(i-buttonRow);
					this.showLeiNum(i-buttonRow-1);
					this.showLeiNum(i+buttonRow);
					this.showLeiNum(i+buttonRow-1);
				}

			}
			else//中央
			{
				put.getframe().getButtons()[i].setText(this.leiPosition[i]+"");
				put.getframe().getButtons()[i].setBackground(Color.WHITE);
				checked[i]=true;
				if(leiPosition[i]==0)
				{
					this.showLeiNum(i+1);
					this.showLeiNum(i-1);
					this.showLeiNum(i-buttonRow+1);
					this.showLeiNum(i-buttonRow);
					this.showLeiNum(i-buttonRow-1);
					this.showLeiNum(i+buttonRow-1);
					this.showLeiNum(i+buttonRow);
					this.showLeiNum(i+buttonRow+1);
				}
			}
		}	
	}
	
	public void recordLeiNum()
	{
		for(int i=0;i<buttonRow*buttonRow;i++)
		{
			if(this.leiPosition[i]==-1)continue;
			if(i==0)//左上角
			{
				if(leiPosition[i+1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow+1]==-1)
					this.leiPosition[i]++;
			}
			else if(i==buttonRow-1)//右上角
			{
				if(leiPosition[i-1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow-1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow]==-1)
					this.leiPosition[i]++;				
			}
			else if(i==buttonRow*(buttonRow-1))//左下角
			{
				if(leiPosition[i+1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow+1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow]==-1)
					this.leiPosition[i]++;
			}
			else if(i==buttonRow*buttonRow-1)//右下角
			{
				if(leiPosition[i-1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow-1]==-1)
					this.leiPosition[i]++;
			}
			else if(i<buttonRow-1&&i>0)//上侧
			{
				if(leiPosition[i+1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow-1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow+1]==-1)
					this.leiPosition[i]++;
			}
			else if(i>buttonRow*(buttonRow-1)&&i<buttonRow*buttonRow-1)//下侧
			{
				if(leiPosition[i+1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow+1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow-1]==-1)
					this.leiPosition[i]++;
			}
			else if(i%buttonRow==0&&i/buttonRow>0&&i/buttonRow<buttonRow-1)//左侧
			{
				if(leiPosition[i+1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow+1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow+1]==-1)
					this.leiPosition[i]++;
			}
			else if(i%buttonRow==buttonRow-1&&i>buttonRow-1&&i<buttonRow*buttonRow-1)//右侧
			{
				if(leiPosition[i-1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow-1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow-1]==-1)
					this.leiPosition[i]++;
			}
			else//中央
			{
				if(leiPosition[i+1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow+1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i-buttonRow-1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow-1]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow]==-1)
					this.leiPosition[i]++;
				if(leiPosition[i+buttonRow+1]==-1)
					this.leiPosition[i]++;
			}
		}
	}
	
	/**
	 * to change the TextField timingTF's time let it like a timer
	 */
	public void changeTime()
	{
		this.lasttime = System.currentTimeMillis()/1000;
		this.lasttime-=this.begintime;

		minutes=this.lasttime/60;
    	second=this.lasttime%60;
    	if(minutes<10&&second<10)
    		put.getframe().getTimeText().setText("Time:"+"0"+minutes+":0"+second);
    	else if(minutes<10&&second>=10)
    		put.getframe().getTimeText().setText("Time:"+"0"+minutes+":"+second);
    	else if(minutes>10&&second<10)
    		put.getframe().getTimeText().setText("Time:"+minutes+":0"+second);
    	else put.getframe().getTimeText().setText("Time:"+minutes+":"+second);
    	pause();
	}
	
	/**
	 * this is the timer which every time begin to time, it will do changeTime() per second. 
	 * If the stop button it will do nothing, the count will not count number.And if player finish or restart
	 * the game. It will cancel the timer task 
	 */
	public void timing()
	{
		timer1.schedule(new TimerTask() {
			public void run() {
				// TODO Auto-generated method stub
				if(stop)
				{
				}
				else if(!StartToPlay||lost) 
				{
					this.cancel();
					pause();
				}
				else 
				{
					changeTime();
				}
			}}, 1000, 10);
	}
	
	/**
	 * to stop program let the timer have enough to cancel 
	 */
	public void pause()
	{
		try { Thread.sleep(1001); }
		catch (Exception e) {};
	}
	
	public void importHelpFile() throws IOException{
		BufferedReader br = new BufferedReader(new FileReader("help.txt"));
		String line =br.readLine();
		while(line!=null)
		{
			put.getframe().getHelptext().append(line);
			line = br.readLine();
		}
		br.close();
	}
	
	/**
	 * import into record table
	 * @throws IOException
	 */
	public void importRecordTable() throws IOException{
		put.getframe().getTableModel().setRowCount(0);
		//System.out.println(put.getframe().getTableModel().getRowCount()+"before");
		BufferedReader br = new BufferedReader(new FileReader("record.txt"));
		String level="";
		String time="";
		String line =br.readLine();
		
		while(line!=null)
		{
			Object[] get=line.split(" ");
			level=String.valueOf(get[0]);
			time=String.valueOf(get[1]);
			Object[] aa = {level,time};
			put.getframe().getTableModel().addRow(aa);
			line = br.readLine();
		}
		//System.out.println(put.getframe().getTableModel().getRowCount()+"added");
		br.close();
	}
	
	/**
	 * import into record file
	 * @throws IOException
	 */
	public void exportTableToFile() throws IOException {
		FileWriter fileWriter =new FileWriter("record.txt");
        fileWriter.write("");
        fileWriter.flush();
        fileWriter.close();
		OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("record.txt",true),"UTF-8");
		String shorttime ="";
        for(int i=0; i< put.getframe().getTableModel().getRowCount(); i++) {
            for(int j=0; j < put.getframe().getTableModel().getColumnCount(); j++) {
            	if((put.getframe().getTableModel().getValueAt(i,j)+"").compareTo("")==0)
            	{
            		out.append("");
            		continue;
            	}
            	if(j==0)
            		out.append("level"+(i+1)+" ");
            	else
            	{
            		if(i+1==currentLevel)
	            	{
	            		shorttime = put.getframe().getTableModel().getValueAt(i,j)+" ";
	            		if(shorttime.compareTo(put.getframe().getTimeText().getText().substring(5))>0)
	            			 out.append(put.getframe().getTimeText().getText().substring(5));
	            		else
	            			out.append(put.getframe().getTableModel().getValueAt(i,j)+" ");
	            	}
	            	else
	        			out.append(put.getframe().getTableModel().getValueAt(i,j)+" ");
            	}
            }
            out.append("\n");
        }
        out.close();
    }
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		if(e.getButton()==e.BUTTON1)
		{
			int i=0;
			for(;i<buttonRow*buttonRow;i++)
			{
				if(e.getSource()==put.getframe().getButtons()[i])
					break;
			}
			if(i!=buttonRow*buttonRow&&!checked[i]&&!putFlag[i]) 
			{
				put.getframe().getStart().setText("Replay");
				StartToPlay=true;
				if(leiPosition[i]==-1&&!lost)//点中地雷
				{
					if(!putFlag[i])
					{
						System.out.println("Explore! Lose the Game!");
						ImageIcon lei1 = new ImageIcon("lei.jpg");
					    Image temp1 =lei1.getImage().getScaledInstance(50, 50, lei1.getImage().SCALE_AREA_AVERAGING);
					    lei1 = new ImageIcon(temp1);
					    for(int m=0;m<this.leiNumber;m++)
					    	put.getframe().getButtons()[put.getMinePosition()[m]].setIcon(lei1);
					    StartToPlay=false;
					    lost=true;
					}
				}
				else if(!lost&&!putFlag[i])
				{
					this.showLeiNum(i);
				}
				if(!firstClick)
					{firstClick=true;this.begintime=System.currentTimeMillis()/1000;this.timing();}
				for(int j=0;j<buttonRow*buttonRow;j++)//检查没有显示数字的方块的数量//没有被点或者被旗子标志
				{
					if(checked[j])
					{
						win++;
					}
				}
				if(!lost&&winofLei==0&&win==buttonRow*buttonRow-leiNumber)
				{
					put.getframe().getRecordText().setText("You won!");
					System.out.println("You won!");
					try {
						importRecordTable();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						exportTableToFile();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						importRecordTable();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					StartToPlay=false;
				}		
			}
			win=0;
			if(e.getSource()==put.getframe().getStart())
			{
				put.getframe().getStart().setText("Play");
				put.getframe().getTimeText().setText("Time:"+"00"+":00");
				put.getframe().getRecordText().setText(this.leiNumber+"");
				//restart
				this.replay(buttonRow,leiNumber);
				for(int j=0;j<buttonRow*buttonRow;j++)
				{
					put.getframe().getButtons()[j].setText(null);
					put.getframe().getButtons()[j].setIcon(null);
					put.getframe().getButtons()[j].setBackground(new Color(220,220,220));
				}
				StartToPlay=false;
			}
		}
		else if(e.getButton()==e.BUTTON3)
		{
			int i=0;
			for(;i<buttonRow*buttonRow;i++)
			{
				if(e.getSource()==put.getframe().getButtons()[i])
					break;
			}
			if(i!=buttonRow*buttonRow)
			{
				
				if(!putFlag[i])
				{
					ImageIcon flag = new ImageIcon("flag.jpg");
				    Image temp2 =flag.getImage().getScaledInstance(50, 50, flag.getImage().SCALE_AREA_AVERAGING);
				    flag = new ImageIcon(temp2);
			    	put.getframe().getButtons()[i].setIcon(flag);
			    	putFlag[i]=true;
			    	if(this.winofLei>0)this.winofLei--;
			    	put.getframe().getRecordText().setText(this.winofLei+"");
				}
				else
				{
					put.getframe().getButtons()[i].setIcon(null);
					putFlag[i]=false;
					this.winofLei++;
			    	put.getframe().getRecordText().setText(this.winofLei+"");
				}
				if(!firstClick&&winofLei<leiNumber)
				{firstClick=true;StartToPlay=true;this.begintime=System.currentTimeMillis()/1000;this.timing();}
			}	
		}
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Play play = new Play(5,5);
	}
}

