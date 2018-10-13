package minesweeper;

import java.awt.Color;

public class Put {

	private Frame frame;
	private int[] minePosition;
	private boolean resetMinePosition=false;//出现重复的位置需重新取数
	public Put(int n)
	{
		frame=new Frame(n);
		minePosition = new int[n];
		for(int i=0;i<n;i++)
			minePosition[i]=-1;
		
		for(int i=0;i<n;i++)
		{
			resetMinePosition=false;
			int temp=(int)(Math.random()*n*n); 
			for(int j=i-1;j>=0;j--)
			{
				if(minePosition[j]==temp)
				{
					i--;
					resetMinePosition=true;
					break;
				}
			}
			if(!resetMinePosition)minePosition[i]=temp;
		}
		/*for(int i=0;i<n;i++)
			System.out.println(minePosition[i]);
		System.out.println("------------");*/
	}
	/**
	 * 
	 * @param n
	 * @param num
	 */
	public void reput1(int n,int num)
	{
		frame.reset(n);
		frame.getStart().setText("Play");
		frame.getTimeText().setText("Time:"+"00"+":00");
		frame.getRecordText().setText(num+"");
		minePosition = new int[num];
		for(int i=0;i<num;i++)
			minePosition[i]=-1;
		
		for(int i=0;i<num;i++)
		{
			resetMinePosition=false;
			int temp=(int)(Math.random()*n*n); 
			for(int j=i-1;j>=0;j--)
			{
				if(minePosition[j]==temp)
				{
					i--;
					resetMinePosition=true;
					break;
				}
			}
			if(!resetMinePosition)minePosition[i]=temp;
		}
		/*for(int i=0;i<num;i++)
			System.out.println(minePosition[i]);
		System.out.println("********");*/
	}
	public void reput2(int n,int num)
	{
		frame.getStart().setText("Play");
		frame.getTimeText().setText("Time:"+"00"+":00");
		frame.getRecordText().setText(num+"");
		
		for(int j=0;j<n*n;j++)
		{
			frame.getButtons()[j].setText(null);
			frame.getButtons()[j].setIcon(null);
			frame.getButtons()[j].setBackground(new Color(220,220,220));
		}
		for(int i=0;i<num;i++)
			minePosition[i]=-1;
		
		for(int i=0;i<num;i++)
		{
			resetMinePosition=false;
			int temp=(int)(Math.random()*n*n); 
			for(int j=i-1;j>=0;j--)
			{
				if(minePosition[j]==temp)
				{
					i--;
					resetMinePosition=true;
					break;
				}
			}
			if(!resetMinePosition)minePosition[i]=temp;
		}
		/*for(int i=0;i<num;i++)
			System.out.println(minePosition[i]);
		System.out.println("********");*/
	}
	public int[] getMinePosition()
	{
		return minePosition;
	}
	public Frame getframe()
	{
		return frame;
	}
}

