package librarySystem;

import java.awt.Button;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class RentalDialog extends JDialog
{
	String userid;JPanel dp;JDialog jd;JPanel pd;
	Font fn;JButton bd;ImageIcon im1,im2;
  public RentalDialog(String str,String userid,JFrame f)
  {
	  super(f,str);
	  jd=this; 
	  im2=new ImageIcon(ImageBox.RETURN_BOOK.getScaledInstance(60, 60,Image.SCALE_SMOOTH));
		 im1=new ImageIcon(ImageBox.SEARCHLOGO.getScaledInstance(60, 40,Image.SCALE_SMOOTH));
	  fn=new Font("Arial",Font.BOLD, 20);
	  pd= new JPanel();
	  Thread th= new Thread("Data2")
	   {
		  @Override
		  public void run()
		  {
			  JPanel pd1;JLabel dat=new JLabel("MORE INFORMATION: ");
			  try 
			  {
				  
				Connection con= DataBase.getConnection();
				PreparedStatement ps= con.prepareStatement("select * from inusebooks where userid=? and usetype=?");
				ps.setString(1, userid);
				ps.setInt(2,1);
				
				ResultSet rs= ps.executeQuery();
				if(!rs.next())
				{
				  JOptionPane.showMessageDialog( jd, "NO RENTALS AVAILABLE!");
				   JPanel dai= new JPanel()
					{
						@Override
						public void paintComponent(Graphics g)
						{
							g.drawImage(ImageBox.DATA_ISSUE, 0, 0,getWidth(),getHeight(), this);
						}
				     };
				     jd.setContentPane(dai);
				}
				else
				{
					do
					{
					    Button l1,l2,l3,l4;
						
						l1= new Button("BOOK-ID: "+rs.getString("bookid"));
						l2= new  Button("BORROW DATE: "+rs.getString("borrowdate"));
						l3= new  Button("RETURN DATE: "+rs.getString("borrowcomp"));
						if(rs.getInt("usetype")==1)
						{
						l4= new  Button("STATUS: Active");
						l4.setForeground(Color.GREEN);
						l4.setBackground(Color.white);
						}
						else
						{
					    l4= new  Button("STATUS: Returned");
					    l4.setForeground(Color.BLUE);
					    l4.setBackground(Color.white);
						}
						l1.setFont(fn);
						l2.setFont(fn);
						l3.setFont(fn);
						l4.setFont(fn);
						l1.setBackground(Color.white);
						l2.setBackground(Color.white);
						l3.setBackground(Color.white);
						
						dat.setFont(fn);
//						Image imm=ImageBox.SEARCHLOGO.getScaledInstance(50,40, Image.SCALE_SMOOTH);
						bd= new JButton(rs.getString("bookid").substring(2),im1);
						bd.setForeground(new Color(0,0,0,0));
						bd.setBackground(Color.white);
						pd1= new JPanel();
						pd1.add(l1);pd1.add(l2);pd1.add(l3);pd1.add(l4);pd1.add(bd);
//						pd1.setLayout(new GridLayout(1,5,30,20));
						pd1.setBackground(new Color(150,75,0));
						bd.setPreferredSize(new Dimension(60,50));
						pd.add(pd1);
						bd.addActionListener(new ActionListener() 
						{
							@Override
							public void actionPerformed(ActionEvent e)
							{
								JButton b11=(JButton)e.getSource();
								PreparedStatement ps1;
								try
								{
								ps1 = con.prepareStatement("select bookimage from booklist where bookid=?");
								ps1.setString(1,"B-"+b11.getText());
								ResultSet rs1=ps1.executeQuery();
								rs1.next();
//								String book,String id,JFrame f,ImageIcon bi
								URL ibp=new URL(rs1.getString("bookimage"));
								new SelectionDialog(b11.getText(), userid, f,new ImageIcon(ibp));
								} 
								catch (Exception e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
						});
					}
					while(rs.next());
				}
				
			  }
			  catch (SQLException e) 
			  {
				// TODO Auto-generated catch block
				e.printStackTrace();
			  }
		  }
	   };
	   th.run();
	  this.userid=userid;
	  setSize(900,700);
	  setLocationRelativeTo(f);
	  setModal(true);
	  setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	  setBackground(Color.white);
	  try 
	  {
		th.join();
	  } 
	  catch (InterruptedException e) 
	  {
		// TODO Auto-generated catch block
		e.printStackTrace();
	  }
	  pd.setLayout(new BoxLayout(pd,BoxLayout.PAGE_AXIS));
	  pd.setBackground(new Color(150,75,0));
	  setResizable(false);
	  JScrollPane jsp= new JScrollPane(pd);
	  jsp.setBackground(new Color(150,75,0));
	  jsp.setForeground(new Color(150,75,0));
	  add(jsp);
	  setVisible(true);
  }
}
