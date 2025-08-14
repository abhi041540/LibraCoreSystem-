package librarySystem;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Label;
import java.awt.TextField;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class UserPortal extends JFrame implements Runnable,ActionListener
{
	JSplitPane js,rps; JPanel lp,rp,ul,dp,dbp;ImageIcon il;
	Image im;String id;JLabel li;Font fn;JButton b1,b2,b3,b4,b5;
	TextField tf;JButton bs,bf;Thread th;int cou=1,ic=0;
   public UserPortal(String str,String id)
   {
	   super(str);
	   this.id=id;
	   rps= new JSplitPane();
	   fn=new Font("Arial",Font.BOLD, 20);
	   setIconImage(ImageBox.FRAME_LOGO);
	   dbp= new JPanel();
	   
	   th= new Thread(this);
	   th.start();
	   setSize(Toolkit.getDefaultToolkit().getScreenSize());
	   setDefaultCloseOperation(EXIT_ON_CLOSE);
	   js=new JSplitPane();
	   js.setOrientation(JSplitPane.HORIZONTAL_SPLIT);
	   js.enable(false);
	   js.setBackground(Color.white);
	   lp= new JPanel();
	   lp.setBackground(new Color(150,75,0));
	  
	   ul=new JPanel();
	   ul.setOpaque(false);
	   ul.setBackground(new Color(0,0,0,0));
	   im=ImageBox.FRAME_LOGO.getScaledInstance(80, 80,Image.SCALE_SMOOTH);
	   il= new ImageIcon(im);
	    b1= new JButton("My Rentals",new ImageIcon(ImageBox.BORROW.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
	    b2= new JButton("Returned",new ImageIcon(ImageBox.RETURNED.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
	    b3= new JButton("Activity",new ImageIcon(ImageBox.HISTORY.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
	    b4= new JButton("Due Soon",new ImageIcon(ImageBox.DEWDATE.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
	    b5= new JButton("SignOut",new ImageIcon(ImageBox.SIGNOUT.getScaledInstance(50, 50, Image.SCALE_SMOOTH)));
	    b1.setFont(fn);
	    b2.setFont(fn);
	    b3.setFont(fn);
	    b4.setFont(fn);
	    b5.setFont(fn);
//	    b1.setOpaque(false);
	    b1.setBackground(Color.white);
//	    b2.setOpaque(false);
	    b2.setBackground(Color.white);
//	    b3.setOpaque(false);
	    b3.setBackground(Color.white);
//	    b4.setOpaque(false);
	    b4.setBackground(Color.white);
//	    b5.setOpaque(false);
	    b5.setBackground(Color.white);
	   ul.add(new JLabel(il));
	   li=new JLabel("USER ID: "+id+"  ");
	   li.setForeground(Color.white);
	   li.setFont(fn);
	   ul.add(li);
	   lp.add(ul);
	   lp.add(new JLabel(""));
	   lp.add(b1);
	   lp.add(b2);
	   lp.add(b3);
	   lp.add(b4);
	   lp.add(new JLabel(""));
	   lp.add(b5);
	   lp.setLayout(new GridLayout(8,1,5,15));
	   rp= new JPanel();
	   rp.setBackground(Color.white);
	   rp.addMouseListener(new MouseAdapter()
		  {
	        @Override
	       public void mouseClicked(MouseEvent me)
	        {
		  	System.out.println(me.getX()+","+me.getY());
	        }
		  });
	     dp= new JPanel();
	     dp.setOpaque(false);
	     dp.setBackground(new Color(0,0,0,0));
	     js.setForeground(Color.white);
	      
	      rps.setOrientation(JSplitPane.VERTICAL_SPLIT);
		   rps.enable(false);
		   rps.setForeground(Color.white);
		   rps.setBackground(Color.white);
		   tf=new TextField(20);
		   tf.setFont(fn);
		   bs=new JButton(new ImageIcon(ImageBox.SEARCHLOGO.getScaledInstance(40, 30, Image.SCALE_SMOOTH)));
		   JLabel ls=new JLabel("Search Books: ");
		   ls.setFont(fn);
		   bs.setBackground(Color.white);
		   bs.setPreferredSize(new Dimension(40,30));
		   rp.setLayout(new BorderLayout());
		   bf=new JButton(new ImageIcon(ImageBox.LIKE.getScaledInstance(40, 30, Image.SCALE_SMOOTH)));
		   bf.setBackground(new Color(255,102,102));
		   bf.setPreferredSize(new Dimension(40,30));
		   dp.add(ls);dp.add(tf);dp.add(bs);dp.add(bf);
		  
//		    dbp.setBackground(new Color(0,0,0,0));
		   dbp.setBackground(Color.white);
		    
		    JScrollPane jsp1=new JScrollPane(dbp);
		    rps.setLeftComponent(dp);
		    js.setLeftComponent(lp);
		     js.setRightComponent(rp);
		   add(js);
		   rp.add(rps);
		   setVisible(true);
//		    try 
//		    {
//				th.join();
//			} 
//		    catch (InterruptedException e)
//		    {
//				e.printStackTrace();
//			}
		      if(ic!=1)
		      {
			   rps.setRightComponent(jsp1);
		      }
			   dbp.setLayout(new GridLayout(12,3, 10, 10));
	        b1.addActionListener(this);
	        b2.addActionListener(this);
	        b3.addActionListener(this);
	        b4.addActionListener(this);
	        b5.addActionListener(this);
	        bs.addActionListener(this);
	        bf.addActionListener(this);
	   
   }
  @Override
  public void run()
  {
	  String url="https://en.m.wikipedia.org/wiki/List_of_English-language_books_considered_the_best";
	  Document dc;
	 
	try 
	{
//		java.sql.Connection con= DataBase.getConnection();
		dc = Jsoup.connect(url).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3").get();
        Element e1=dc.selectFirst("table.wikitable");
        Element e2=e1.selectFirst("tbody");
        Elements et=e2.select("tr");
        for(int i=1;i<=et.size()-1;i++)
        {
        Element rt1=et.get(i);
        Element td1=rt1.selectFirst("td");
        if(td1==null)
	      {
	    	  continue;
	      }
        Element i1=td1.selectFirst("i");
        if(i1==null)
	      {
	    	  continue;
	      }
        Element a=i1.selectFirst("a");
		if(a==null)
		  {
	    	  continue;
	      }

        String np=a.attr("href");
        URL iu=new URL("https://en.m.wikipedia.org/wiki/");
		URL fiu= new URL(iu,np);
		 Elements el1= rt1.select("td");
		 if(el1==null)
		 {
			 continue;
		 }
         Element td= el1.get(1);
         if(td==null)
		 {
			 continue;
		 }
         Element td4=td.selectFirst("span");
         if(td4==null)
		 {
			 continue;
		 }
         td=td4.selectFirst("span");
         if(td==null)
		 {
			 
			 continue;
		 }
         td4=td.selectFirst("span");
         if(td4==null)
		 {
			 continue;
		 }
         td=td4.selectFirst("a");
         if(td==null)
		 {
			 continue;
		 }
         Document dc1=Jsoup.connect(fiu.toString()).userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3").get();
		if(dc1==null)
	      {
	    	  continue;
	      }
		
		Elements ad1=dc1.select("p");
		Element p4=ad1.get(1);
		Element sec=dc1.selectFirst("section.mf-section-0");
	      if(sec==null)
	      {
	    	  continue;
	      }
	      Element tbl=dc1.selectFirst("table.infobox");
	      if(tbl==null)
	      {
	    	  continue;
	      }
	      Element tbd=tbl.selectFirst("tbody");
	      if(tbd==null)
	      {
	    	  continue;
	      }
	      Element ftr=tbd.selectFirst("tr");
	      if(ftr==null)
	      {
	    	  continue;
	      }
	      Element td2=ftr.selectFirst("td.infobox-image");
	      if(td2==null)
	      {
	    	  continue;
	      }
	      Element span=td2.selectFirst("span.mw-default-size");
	      if(span==null)
	      {
	    	  continue;
	      }
	      Element tda=span.selectFirst("a");
	      if(tda==null)
	      {
	    	  continue;
	      }
	      Element img=tda.selectFirst("img");
	      if(img==null)
	      {
	    	  continue;
	      }
	      String iurl=img.attr("src");
			fiu= new URL(iu,iurl);
			ImageIcon ic= new ImageIcon(fiu);
			
			Image im=ic.getImage();
			im.getScaledInstance(ic.getIconWidth()-100, ic.getIconHeight()-100, Image.SCALE_SMOOTH);
			JButton bi=new JButton(""+cou,new ImageIcon(im));
			cou+=1;
			bi.setHorizontalTextPosition(SwingConstants.LEFT);
			bi.setBackground(Color.white);
			bi.setFont(fn);
			dbp.add(bi);
			bi.addActionListener(this);
			revalidate();
			repaint();
			
			
        }
	} 

	catch (Exception e) 
	{
         e.printStackTrace();
		ic=1;
		JPanel dai= new JPanel()
		{
			@Override
			public void paintComponent(Graphics g)
			{
				g.drawImage(ImageBox.DATA_ISSUE, 0, 0,getWidth(),getHeight(), this);
			}
	     };
	     rps.setRightComponent(dai);
	     revalidate();
	     repaint();
	}
	 
	 
	  
  }
@Override
public void actionPerformed(ActionEvent e) 
{
   JButton b1=(JButton)e.getSource();
   if(b1.getText().equalsIgnoreCase("My Rentals"))
   {
	   new RentalDialog("Rentals Window",id,this);
   }
   else if(b1.getText().equalsIgnoreCase("Returned"))
   {
	   new Returned(id,this);
   }
   else if(b1.getText().equalsIgnoreCase("Activity"))
   {
	  new Activity(id,this); 
   }
   else if(b1==bs)
   {
	  String bn=tf.getText();
	  try 
	  {
		  String book1,burl;ImageIcon bi1;URL icu;
		java.sql.Connection con=DataBase.getConnection();
		PreparedStatement ps= con.prepareStatement("select * from booklist where bookname like '%"+bn+"%'");
//		ps.setString(1, bn);
		ResultSet rs= ps.executeQuery();
		if(!rs.next())
		{
			JOptionPane.showMessageDialog(this, "NO BOOK AVAILABLE!");
		}
		else
		{
		
//			System.out.println("abhi");
			book1=rs.getString("bookid");
			burl=rs.getString("bookimage");
			icu= new URL(burl);
			bi1= new ImageIcon(icu);
			new SelectionDialog(book1.substring(2),id,this,(ImageIcon)bi1);
			
		tf.setText(null);
		ps.close();
		con.close();
		rs.close();
		}
	  }
	  catch (Exception e1) 
	  {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	  }
   }
   else if(b1.getText().equalsIgnoreCase("Due Soon"))
   {
	   new DueSoon(id,this);
   }
   else if(b1.getText().equalsIgnoreCase("SignOut"))
   {
	   dispose();
	   new UserLogin("User Login");
   }
   else if(b1==bf)
   {
	   new LikeList(id,this);
   }
   else 
   {
	  new SelectionDialog(b1.getText(),id,this,(ImageIcon)b1.getIcon()); 
   }
   	
}
  
}
