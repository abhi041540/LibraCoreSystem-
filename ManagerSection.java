package librarySystem;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;

public class ManagerSection extends JFrame implements ActionListener
{
	JPanel cp,data;Label l1,l2,l3;
	TextField t1,t2; JPasswordField pf;
	JButton b1; Font fn;
  public ManagerSection(String str)
  {
	  super(str);
	  setSize(Toolkit.getDefaultToolkit().getScreenSize());
	  setDefaultCloseOperation(EXIT_ON_CLOSE);
	  cp= new JPanel() 
	  {
		  @Override
		  public void paintComponent(Graphics g)
		  {
			 g.drawImage(ImageBox.MANAGER_WINDOW, 0, 0, getWidth(), getHeight(), this); 
		  }
	  };
	  setContentPane(cp);
	  fn=new Font("Arial",Font.BOLD, 20);
	  setLayout(null);
	  data=new JPanel();
	  data.setOpaque(false);
	  data.setBackground(new Color(0,0,0,0));
	  l1= new Label("Name: ");
	  l2= new Label("New LoginID: ");
	  l3= new Label("New Password: ");
	 
	  
	  l1.setForeground(Color.white);
	  l2.setForeground(Color.white);
	  l3.setForeground(Color.white);
	  l1.setFont(fn);
	  l2.setFont(fn);
	  l3.setFont(fn);
	  t1= new TextField(30);
	  t2= new TextField(30);
	  pf=new JPasswordField(30);
	  b1= new JButton("SAVE");
	  t1.setFont(fn);
	  t2.setFont(fn);
	  pf.setFont(fn);
	  b1.setFont(fn);
	  data.add(l1); data.add(t1); data.add(l2); data.add(t2); data.add(l3); data.add(pf);
//	  addMouseListener(new MouseAdapter()
//			  {
//		  @Override
//		  public void mouseClicked(MouseEvent me)
//		  {
//			  	System.out.println(me.getX()+","+me.getY());
//		  }
//			 });
//	  
	  data.setLayout(new GridLayout(3,2,10,60));
	  data.setBounds(79, 238, 521, 230);
	  b1.setBounds(99, 510, 472, 36);
	  b1.setBackground(Color.white);
	  add(data);add(b1);
	  b1.addActionListener(this);
	  setVisible(true);
	  setResizable(true);
	  setIconImage(ImageBox.FRAME_LOGO);
  }
  public static void main(String args[])
  {
	  new ManagerSection("NEW STAFF REGESTRATION");
  }
@Override
public void actionPerformed(ActionEvent e)
{
    if(t1.getText()==null || t1.getText().isEmpty() || t1.getText().equalsIgnoreCase("") || t2.getText()==null || t2.getText().isEmpty() || t2.getText().equalsIgnoreCase("") || pf.getText()==null || pf.getText().isEmpty() || pf.getText().equalsIgnoreCase(""))	
    {
    	JOptionPane.showMessageDialog(this, "PLEASE ENTER ALL REQURED DATA TO CONTINUE");
    	t1.setText(null);
    	t2.setText(null);
    	pf.setText(null);
    }
    else
    {
    	String name=t1.getText();
    	String id=t2.getText();
    	String pass=pf.getText();
    	try 
    	{
			Connection con= DataBase.getConnection();
			Statement s= con.createStatement();
		 
			ResultSet rs=s.executeQuery("select * from staffdata where loginid='"+id+"'");
			if(rs.next()==false)
			{
			   rs=s.executeQuery("select * from libraryCapacity");
			   rs.next();
			   long l= rs.getLong("staffcount");
			  PreparedStatement ps= con.prepareStatement("insert into staffdata(name,loginid,password,staffid) values(?,?,?,?)");
			  ps.setString(1, name);
			  ps.setString(2, id);
			  ps.setString(3, pass);
			  int m=0;
			  for(;m!=1;)
			  {
				  int in=name.indexOf(" ");
				  if(in==-1)
				  {
					  m=1;
					  break;
				  }
				  name=name.substring(0, in)+ name.substring(in+1);
			  }
			  ps.setString(4, name+l);
			  ps.executeUpdate();
			
			  ps= con.prepareStatement("update librarycapacity set staffcount=?");
			 
              l+=1;
			  ps.setLong(1,l);
			  ps.executeUpdate();
			  JOptionPane.showMessageDialog(data,"YOUR STAFFID IS: "+ name+(l-1));
			  t1.setText(null);
		      t2.setText(null);
		      pf.setText(null);
			}
			else
			{
				JOptionPane.showMessageDialog(this, "LoginId Not Available (Already Taken)");
		    	t1.setText(null);
		    	t2.setText(null);
		    	pf.setText(null);
			}
		} 
    	catch (SQLException e1) 
    	{
			e1.printStackTrace();
		}
    }
}
}
