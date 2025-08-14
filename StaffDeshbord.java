package librarySystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class StaffDeshbord extends JFrame implements ActionListener
{
	JPanel cp,bp;JLabel sn;Font fn; JButton usl,libc,uin,bd,ru;
  public StaffDeshbord(String arg,JFrame f,String staffname)
  {
	  super(arg);
	  fn=new Font("Arial",Font.BOLD, 20);
	  setSize(Toolkit.getDefaultToolkit().getScreenSize());
	  addWindowListener(new WindowAdapter()
	  	 {
	  		 @Override
	  		 public void windowClosing(WindowEvent we)
	  		 {
	  			 dispose();
	  			 f.setVisible(true);
	  		 }
	  	 });
	  cp=new JPanel()
	  {
		  @Override
		  public void paintComponent(Graphics g)
		  {
			  g.drawImage(ImageBox.BACKGROUND,0,0,getWidth(),getHeight(), this);
		  }
	 };
	 setContentPane(cp);
			  
//		addMouseListener(new MouseAdapter()
//				{
//			      @Override
//			      public void mouseClicked(MouseEvent me)
//			      {
//			    	  System.out.println(me.getX()+","+me.getY());
//			      }
//				});	  
//			  
		sn= new JLabel(new ImageIcon(ImageBox.FRAME_LOGO.getScaledInstance(50,45, Image.SCALE_SMOOTH)));
		sn.setText(staffname);
		sn.setVerticalTextPosition(SwingConstants.BOTTOM);
		sn.setHorizontalTextPosition(SwingConstants.CENTER);
		sn.setForeground(Color.white);
		sn.setBounds(0,0,115,85);
		sn.setFont(new Font("Arial",Font.BOLD, 15));
		sn.setBackground(Color.white);
		usl= new JButton(new ImageIcon(ImageBox.USER_DATA.getScaledInstance(118, 110,Image.SCALE_SMOOTH)));
		usl.setText("Users List");
		usl.setFont(fn);
		usl.setOpaque(false);
		usl.setBackground(new Color(0,0,0,0));
		usl.setVerticalTextPosition(SwingConstants.BOTTOM);
		usl.setHorizontalTextPosition(SwingConstants.CENTER);
		usl.setForeground(Color.white);
		bd= new JButton(new ImageIcon(ImageBox.USER.getScaledInstance(118, 110,Image.SCALE_SMOOTH)));
		bd.setText("User Data");
		bd.setFont(fn);
		bd.setOpaque(false);
		bd.setBackground(new Color(0,0,0,0));
		bd.setVerticalTextPosition(SwingConstants.BOTTOM);
		bd.setHorizontalTextPosition(SwingConstants.CENTER);
		bd.setForeground(Color.white);
		ru= new JButton(new ImageIcon(ImageBox.REMOVE_USER.getScaledInstance(118, 110,Image.SCALE_SMOOTH)));
		ru.setText("Remove User");
		ru.setFont(fn);
		ru.setOpaque(false);
		ru.setBackground(new Color(0,0,0,0));
		ru.setVerticalTextPosition(SwingConstants.BOTTOM);
		ru.setHorizontalTextPosition(SwingConstants.CENTER);
		ru.setForeground(Color.white);
		uin= new JButton(new ImageIcon(ImageBox.BOOKS_DATA.getScaledInstance(118, 110,Image.SCALE_SMOOTH)));
		uin.setText("Book Data");
		uin.setFont(fn);
		uin.setOpaque(false);
		uin.setBackground(new Color(0,0,0,0));
		uin.setVerticalTextPosition(SwingConstants.BOTTOM);
		uin.setHorizontalTextPosition(SwingConstants.CENTER);
		uin.setForeground(Color.white);
		libc= new JButton(new ImageIcon(ImageBox.LIBRARY_CAPACITY.getScaledInstance(118, 110,Image.SCALE_SMOOTH)));
		libc.setText("Library Capacity");
		libc.setFont(fn);
		libc.setOpaque(false);
		libc.setBackground(new Color(0,0,0,0));
		libc.setVerticalTextPosition(SwingConstants.BOTTOM);
		libc.setHorizontalTextPosition(SwingConstants.CENTER);
		libc.setForeground(Color.white);
		libc.setBorderPainted(false);
		uin.setBorderPainted(false);
		usl.setBorderPainted(false);
		ru.setBorderPainted(false);
		bd.setBorderPainted(false);
		bp=new JPanel();
		bp.setOpaque(false);
		bp.setBackground(new Color(0,0,0,0));
//		bp.requestFocusInWindow();
		bp.setBounds(250, 160, 750,400);
		bp.setLayout(new GridLayout(2,3,20,20));
		bp.add(usl);bp.add(libc);bp.add(uin);bp.add(bd);bp.add(ru);bp.add(new JLabel(""));
		add(bp);
		add(sn);
		libc.addActionListener(this);
		usl.addActionListener(this);
		bd.addActionListener(this);
		ru.addActionListener(this);
		uin.addActionListener(this);
		setLayout(null);	  
	  setIconImage(ImageBox.FRAME_LOGO);
	  setVisible(true);
  }
@Override
public void actionPerformed(ActionEvent e)
{
//	library capacity
	if(e.getSource()==libc)
	{
		try 
		{
			Connection con=DataBase.getConnection();
			PreparedStatement ps= con.prepareStatement("select * from librarycapacity");
			ResultSet rs= ps.executeQuery();
			rs.next();
			JOptionPane.showMessageDialog(this, "Numbre of readers: "+rs.getLong("usercount")+"\nNumber of staff: "+rs.getLong("staffcount")+"\nNumber of books: "+rs.getLong("bookscount"));
			con.close();
			rs.close();
			ps.close();
		} 
		catch (SQLException e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
//	user list
	else if(e.getSource()==usl)
	{
		new UserList(this);
	}
//	user data
	else if(e.getSource()==bd)
	{
		String userid=JOptionPane.showInputDialog(this, "Enter UserId To Continue","USERID", JOptionPane.QUESTION_MESSAGE);
		if(userid!=null && !userid.isEmpty())
		{
			new ActivityStaffSec(userid,this);
		}
	}
//	book data
	else if(e.getSource()==uin)
	{
		new BookList(this);
	}
//	remove user
	else if(e.getSource()==ru)
	{
		String userid;
		userid=JOptionPane.showInputDialog(this, "ENTER USERID TO REMOVE USER","USER REMOVAL", JOptionPane.QUESTION_MESSAGE);
		if(userid!=null && !userid.isEmpty())
		{
			try {
				
				Connection con= DataBase.getConnection();
				PreparedStatement ps= con.prepareStatement("select bookid from inusebooks where userid=? and usetype=?");
				ps.setString(1, userid);
				ps.setInt(2,1);
				ResultSet rs= ps.executeQuery();
			    for(;rs.next();)
			    {
			    	ps= con.prepareStatement("select bookcount from booklist where bookid=?");
			    	ps.setString(1, rs.getString("bookid"));
			    	  ResultSet rs1=ps.executeQuery();
			    	  rs1.next();
			    	  int bcc=rs1.getInt("bookcount");
			    	  ps=con.prepareStatement("update booklist set bookcount=? where bookid=?");
			    		ps.setInt(1, (bcc+1));
			    		ps.setString(2, rs.getString("bookid"));
			    		ps.executeUpdate();
			    		rs1.close();
			    }
			    ps= con.prepareStatement("delete from inusebooks where userid=?" );
			    ps.setString(1, userid);
			    ps.executeUpdate();
			    ps= con.prepareStatement("delete from userdata where userid=?" );
			    ps.setString(1, userid);
			    int iss=0;
			    iss=ps.executeUpdate();
			    if(iss==0)
			    {
			    	 JOptionPane.showMessageDialog(this,"USER NOT FOUND");
			    }
			    else
			    {
			    	ps= con.prepareStatement("select usercount from librarycapacity");
			    	  ResultSet rs1=ps.executeQuery();
			    	  rs1.next();
			    	  int bcc=rs1.getInt("usercount");
			    	  ps=con.prepareStatement("update librarycapacity set usercount=?");
			    		ps.setInt(1, (bcc-1));
			    		ps.executeUpdate();
			    		rs1.close();
			    JOptionPane.showMessageDialog(this,"USER REMOVED SUCCESSFULLY");
			    }
			    ps.close();
			    rs.close();
			    con.close();
			    } 
			catch (Exception e1)
			{
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
}
}
