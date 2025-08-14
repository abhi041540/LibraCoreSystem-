package librarySystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class SelectionDialog extends JDialog implements Runnable,ActionListener
{
	JSplitPane js;JPanel lp,rp,dp;JButton b1,b2,b3;
	Font fn;ImageIcon bi;String userid,book;
    public SelectionDialog(String book,String id,JFrame f,ImageIcon bi) 
    {
    	super(f,"BOOK DETAILS");
    	this.bi=bi;
    	this.book=book;
    	this.userid=id;
    	setSize(900,700);
    	setLocationRelativeTo(f);
    	setModal(true);
    	fn= new Font("arial", Font.BOLD,20);
    	dp=new JPanel();
    	Thread th= new Thread(this);
    	th.start();
    	setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    	js= new JSplitPane();
    	js.setEnabled(false);
    	js.setOrientation(JSplitPane.VERTICAL_SPLIT);
    	setBackground(Color.white);
    	b1=new JButton("Favorite",new ImageIcon(ImageBox.LIKE.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
    	b1.setBackground(new Color(255,102,102));
    	b1.setForeground(Color.white);
    	b1.setFont(fn);
    	b2=new JButton("Borrow",new ImageIcon(ImageBox.BORROW.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
    	b2.setBackground(new Color(150,75,0));
    	b2.setForeground(Color.white);
    	b2.setFont(fn);
    	b3=new JButton("Availablity",new ImageIcon(ImageBox.AVAILABLE.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
    	b3.setBackground(Color.gray);
    	b3.setForeground(Color.white);
    	b3.setFont(fn);
    	JPanel bp= new JPanel();
    	bp.setOpaque(false);
    	bp.setBackground(new Color(0,0,0,0));
    	dp.setOpaque(false);
    	dp.setBackground(Color.white);
    	dp.setLayout(new GridLayout(2,1,10,0));
    	bp.setLayout(new GridLayout(1,3,5,10));
    	bp.add(b3);bp.add(b1);bp.add(b2);
    	js.setForeground(Color.white);
    	js.setBackground(Color.white);
    	js.setLeftComponent(bp);
    	setResizable(false);
    	b1.addActionListener(this);
    	b2.addActionListener(this);
    	b3.addActionListener(this);
    	  try 
    	  {
			th.join();
		} 
    	  catch (InterruptedException e) 
    	  {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	JScrollPane jsp=new JScrollPane(dp);
    	jsp.setBackground(Color.white);
    	js.setRightComponent(jsp);
    	setBackground(Color.white);
    	add(js);
    	setIconImage(ImageBox.FRAME_LOGO);
    	setVisible(true);
    	
    }
    @Override
	public void actionPerformed(ActionEvent e)
	{
		Connection con;
//		 System.out.println("abhi");
		try 
		{
			con=DataBase.getConnection();
		
	   JButton br=(JButton)e.getSource();
	   if(br==b1)//fav.
	   {
		   PreparedStatement ps=con.prepareStatement("select bookcount from booklist where bookid=?");
		   ps.setString(1, "B-"+book);
		   ResultSet rs=ps.executeQuery();
//		   System.out.println("abhi");
		  if(rs.next())
		  {
			  int bco=rs.getInt("bookcount");
			  if(bco!=0)
			  {
				  ps=con.prepareStatement("select usetype from inusebooks where bookid=? and userid=?");
				   ps.setString(1, "B-"+book);
				   ps.setString(2, userid);
				   ResultSet rs1=ps.executeQuery();
				   if(rs1.next()==false)
				   {
				 ps=con.prepareStatement("insert into inusebooks(userid,bookid,usetype) values(?,?,?)");
				 ps.setString(1,userid);
				 ps.setString(2, "B-"+book);
				 ps.setInt(3,2);
				 ps.executeUpdate();
				 JOptionPane.showMessageDialog(this, "ADDED SUCCESSFULLY");
				   }
				   else
				   {
					   int ac=0,bt=0;
					   do
					   {
						  if(rs1.getInt("usetype")==2)
						  {
							  ac=1;
							  bt=2;
							  break;
						  }
						  else if(rs1.getInt("usetype")==1)
						  {
							  bt=1;  
						  }
					   }
					   while(rs1.next());
					  
					   if(bt==2)
					   {
					   JOptionPane.showMessageDialog(this,"BOOK IS ALREADY ADDED IN YOUR FAVOURITE LIST");
					   }
					   else
					   {
						   if(bt==0) 
						   {
							     ps=con.prepareStatement("insert into inusebooks(userid,bookid,usetype) values(?,?,?)");
								 ps.setString(1,userid);
								 ps.setString(2, "B-"+book);
								 ps.setInt(3,2);
								 ps.executeUpdate();
								 JOptionPane.showMessageDialog(this, "ADDED SUCCESSFULLY");
						   }
						   else if(bt==1)
						   {
						    int conf=JOptionPane.showConfirmDialog(this, "YOU ALREADY BORROWED THIS BOOK DO YOU STILL WANT TO ADD IT IN YOUR FAVOURITE LIST?","PEASE COMFORM!", JOptionPane.OK_CANCEL_OPTION);
						    if(conf==JOptionPane.OK_OPTION)
						    {
						    	 ps=con.prepareStatement("insert into inusebooks(userid,bookid,usetype) values(?,?,?)");
								 ps.setString(1,userid);
								 ps.setString(2, "B-"+book);
								 ps.setInt(3,2);
								 ps.executeUpdate();
								 JOptionPane.showMessageDialog(this, "ADDED SUCCESSFULLY");
						    }
						   }
					   }
				   }
				   rs1.close();
			  }
			  else
			  {
				  JOptionPane.showMessageDialog(this,"BOOK NOT AVAILABLE!");
			  }
		  }
		  ps.close();
		  rs.close();
		   
	   }
	   else if(br==b2)//borrow
	   {
		   PreparedStatement ps=con.prepareStatement("select bookcount from booklist where bookid=?");
		   ps.setString(1, "B-"+book);
		   ResultSet rs=ps.executeQuery();
//		   System.out.println("abhi");
		  if(rs.next())
		  {
			  int bco=rs.getInt("bookcount");
			  if(bco!=0)
			  {
//				   System.out.println("abhi");
				  ps=con.prepareStatement("select * from inusebooks where bookid=? and usetype=?");
				   ps.setString(1, "B-"+book);
				   ps.setInt(2,1);
				   ResultSet rs1=ps.executeQuery();
				   if(rs1.next()==false)
				   {
					 Date d=new Date(); 
					 SimpleDateFormat sd= new SimpleDateFormat("yyyy-MM-dd");
					String date= sd.format(d);
				 ps=con.prepareStatement("insert into inusebooks(userid,bookid,usetype,borrowdate,borrowcomp) values(?,?,?,?,?)");
				 ps.setString(1,userid);
				 ps.setString(2, "B-"+book);
				 ps.setInt(3,1);
				 ps.setString(4, date);
				 String yer,mon,dat;
				 yer=date.substring(0,4);
				 mon=date.substring(5,7);
				 dat=date.substring(8);
				
				 int y1,m1,d1;
				 y1=Integer.parseInt(yer);
				 m1=Integer.parseInt(mon);
				 d1=Integer.parseInt(dat);
				 
				 if(m1==2)
				 {
					 if((d1+7)>28)
					 {
						 d1=28-(d1+7);
						 m1+=1;
						 if(m1>12)
						 {
							 m1=1;
							 y1+=1;
						 }
					 }
					 else
					 {
						 d1=d1+7;
					 }
			
				 }
				 else if((m1%2)!=0)
				 {
					 if(d1+7>31)
					 {
						 d1=31-(d1+7);
						 m1+=1;
						 if(m1>12)
						 {
							 m1=1;
							 y1+=1;
						 }
					 }
					 else
					 {
						 d1=d1+7;
					 }
				 }
				 else if((m1%2)==0 &&(d1%2)!=2)
				 {
					 if(d1+7>30)
					 {
						 d1=30-(d1+7);
						 m1+=1;
						 if(m1>12)
						 {
							 m1=1;
							 y1+=1;
						 }
					 } 
					 else
					 {
						 d1=d1+7;
					 }
				 }
				 if(m1>=1 && m1<=9)
				 {
					 mon="0"+m1;
				 }
				 else
				 {
					 mon=String.valueOf(m1);
				 }
				 if(d1>=1 && d1<=9)
				 {
					 dat="0"+d1;
				 }
				 else
				 {
					 dat=String.valueOf(d1);
				 }
				 yer=String.valueOf(y1);
				 
				 ps.setString(5,yer+"-"+mon+"-"+dat);
				 ps.executeUpdate();
				 ps=con.prepareStatement("update booklist set bookcount=? where bookid=?");
				 ps.setInt(1, (bco-1));
				 ps.setString(2, "B-"+book);
				 ps.executeUpdate();
				 JOptionPane.showMessageDialog(this, "BORROWED SUCCESSFULLY.");
				   }
				   else
				   {
					   int bt=rs1.getInt("usetype");
					 
					   if(bt==1)
					   {
						  JOptionPane.showMessageDialog(this, "YOU ALREADY HAVE THE COPY OF THAT BOOK");
				       }
					 }
			  }
			  else
			  {
				  JOptionPane.showMessageDialog(this,"BOOK NOT AVAILABLE!");
			  }
		  }
		  ps.close();
		  rs.close();  
	   }
	   else if(br==b3)//avai.
	   {
		   PreparedStatement ps=con.prepareStatement("select bookcount from booklist where bookid=?");
		   ps.setString(1, "B-"+book);
		   ResultSet rs=ps.executeQuery();
//		   System.out.println("abhi");
		  if(rs.next())
		  {
			  int bco=rs.getInt("bookcount");
			  if(bco!=0)
			  {
			   JOptionPane.showMessageDialog(this, "BOOK IS AVAILABLE!");
			  }
			  else
			  {
				  JOptionPane.showMessageDialog(this, "BOOK IS NOT AVAILABLE!");
			  }
		  }
		  rs.close();
		  ps.close();
		  
	   }
		} 
		catch (Exception e1) 
		{
			e1.printStackTrace();
		}
	}
	@Override
	public void run()
	{
		   try
		   {
			Connection con=DataBase.getConnection();
			JPanel bc= new JPanel();
//			bc.setOpaque(false);
			JLabel bl=new JLabel("BOOK INFORMATION:-",bi,JLabel.CENTER);
			bl.setFont(fn);
			
			bc.setBackground(Color.white);
			bc.add(bl);
//			bl.add(Box.createHorizontalGlue());
			bl.setAlignmentX(RIGHT_ALIGNMENT);
			Statement st=con.createStatement();
			ResultSet rs= st.executeQuery("select bookdata,aurthordata,aurthorimage from booklist where bookid='B-"+book+"'");
			String bdat,adat,aurl;
			if(rs.next())
			{
			bdat=rs.getString("bookdata");
			adat=rs.getString("aurthordata");
			aurl=rs.getString("aurthorimage");
			JTextArea ta= new JTextArea(4,6);
			ta.setText(bdat);
			ta.setBackground(Color.white);
			ta.setLineWrap(true);
			ta.setWrapStyleWord(true);
			ta.setFont(fn);
			bc.add(ta);
			JPanel ap=new JPanel();
//			ap.setOpaque(false);
			ap.setBackground(Color.white);
			JLabel al=new JLabel("AUTHOR INFORMATION:-",new ImageIcon(new URL(aurl)),JLabel.CENTER);
			al.setFont(fn);
			
			ap.add(al);
//			al.add(Box.createHorizontalGlue());
			al.setAlignmentX(RIGHT_ALIGNMENT);
			JTextArea tb= new JTextArea(4,6);
			tb.setText(adat);
			tb.setBackground(Color.white);
			tb.setLineWrap(true);
			tb.setWrapStyleWord(true);
			tb.setFont(fn);
			tb.setEditable(false);
			ta.setEditable(false);
			ap.add(tb);
			bc.setLayout(new BoxLayout(bc, BoxLayout.PAGE_AXIS));
			ap.setLayout(new BoxLayout(ap, BoxLayout.PAGE_AXIS));
			dp.add(bc);dp.add(ap);
			}
			con.close();
			rs.close();
		   } 
		 catch (Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
}
