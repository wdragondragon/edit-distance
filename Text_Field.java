package strtmpp;


import javax.swing.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;


class WindowText extends JFrame implements ActionListener
{	
	JTextArea  text1, text2;
	JButton button;
	String str1 = "",str2 ="";
	int keynumber = 0,i=0,sign,tempbefore=0,temp1before=0;
	public static int mistake=0,duo=0,lou=0;
	public static String lookplay;
	ArrayList<String> record;
	WindowText(String s)
	{
		setLayout(null);
		
		text1 = new JTextArea();
		text2 = new JTextArea();
		
		JScrollPane textj1 = new JScrollPane(text1);
		JScrollPane textj2 = new JScrollPane(text2);
		text1.setLineWrap(true);
		text2.setLineWrap(true);
		button = new JButton("提交");
		textj1.setBounds(10,10,300,100);textj2.setBounds(10,120,300,100);
		button.setBounds(10,240,30,30);
		button.addActionListener(this);
		setTitle(s);
		add(textj1);
		add(textj2);
		add(button);
		setBounds(100, 100, 500, 500);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	char []a;
	char []b;
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		compt2();
	}
	
	void compt1(){
		record = new ArrayList<String>();
		str1=text1.getText();
		str2=text2.getText();
		
		String []act = str1.split("，");
		String []dazi = str2.split("，");
		mistake=0;duo=0;lou=0;
		System.out.println();
		for(int k =0;k<act.length;k++){
			tempbefore=0;temp1before=0;
			str1=act[k];
			str2=dazi[k];
			int onlymistake=0;
			int onlylou=0;
			int onlyduo=0;
			while(i<=str2.length()){
				for(int j=str2.length();j>i;j--){
					int temp=str1.indexOf(str2.substring(i,j),tempbefore);
					if(temp!=-1){
						int temp1=str2.indexOf(str2.substring(i,j),temp1before);
						int mistaketmp=0;
						int loutemp=Math.abs(temp-tempbefore);
						int duotemp=Math.abs(temp1-temp1before);
						mistaketmp=loutemp<duotemp?loutemp:duotemp;
						
						onlymistake+=mistaketmp;
						onlylou+=loutemp-mistaketmp;
						onlyduo+=duotemp-mistaketmp;
						
						mistake+=mistaketmp;
						lou+=loutemp-mistaketmp;
						duo+=duotemp-mistaketmp;
						
						tempbefore=temp+j-i;
						temp1before=temp1+j-i;
						
//						System.out.println(temp+":"+tempbefore+":"+str1.substring(temp,tempbefore)+":"+temp1+":"+temp1before);
						i=j-1;
						break;
					}
				}
				i++;
				if(tempbefore<=str1.length()&&str2.length()==temp1before){
					onlylou+=str1.length()-tempbefore;
					lou+=str1.length()-tempbefore;;
					System.out.println("漏补"+str1.length()+":"+tempbefore);
					break;
				}
			}
			if(tempbefore<str1.length()&&str2.length()>temp1before){
				int temp1=str1.length()-tempbefore;
				int temp2=str2.length()-temp1before;
				onlymistake += Math.min(temp1,temp2);
				mistake += Math.min(temp1,temp2);
				int dif = temp1-temp2;
				if(dif>0){
					onlylou+=dif;
					lou+=dif;
				}
				else if(dif<0){
					onlyduo+=-dif;
					lou+=-dif;
				}
			}
			if(onlymistake!=0||onlyduo!=0||onlylou!=0)
				record.add("原文："+str1+"\n跟打："+str2+"\n错："+onlymistake+" 漏："+onlylou+" 多："+onlyduo);
			i=0;
		}
		System.out.println();
		String mistakeall="";
		for(int i=0;i<record.size();i++)
			mistakeall+=record.get(i)+"\n";
		lookplay="错："+mistake+" 漏："+lou+" 多："+duo;
		mistakeall+=lookplay;
		System.out.println(mistakeall);
	}
	
	
	
	
	void compt2(){
		int all=0;
		str1=text1.getText();
		str2=text2.getText();
		mistake=0;duo=0;lou=0;
		String []act = str1.split("，");
		String []dazi = str2.split("，");
		for(int k =0;k<act.length;k++){
			a=act[k].toCharArray();
			b=dazi[k].toCharArray();
			edit_distance();
		}
//		System.out.println(all+":"+"错"+mistake+"漏"+lou+"多"+duo);
	}
	void edit_distance()
	{
	    int lena = a.length;
	    int lenb = b.length;
	    int d[][]=new int[lena+1][lenb+1];
	    int i, j;

	    for (i = 0; i <= lena; i++) {
	        d[i][0] = i;
	    }
	    for (j = 0; j <= lenb; j++) {
	        d[0][j] = j;
	    }

	    for (i = 1; i <= lena; i++) {
	        for (j = 1; j <= lenb; j++) {
	            // 算法中 a, b 字符串下标从 1 开始，c 语言从 0 开始，所以 -1
	            if (a[i-1] == b[j-1]) {
	                d[i][j] = d[i-1][j-1];
	            } else {
	                d[i][j] = min_of_three(d[i-1][j]+1, d[i][j-1]+1, d[i-1][j-1]+1);
	              
	                
	            }
	        }
	    }
	    for(i =0;i<=lena;i++){
	    	System.out.println();
	    	for(j=0;j<=lenb;j++)
	    		System.out.print(d[i][j]);
	    }
//	    return d[lena][lenb];
	}
	int min_of_three(int i,int j,int k){
		int temp = i<j?i:j;
		temp = temp<k?temp:k;
//    	if(temp==i)
//			duo++;
//		else if(temp==j)
//			lou++;
//		else if(temp==k)
//			mistake++;
		return temp;
	}
}
public class Text_Field {
	public static void main(String args[])
	{
		WindowText win = new WindowText("添加了文本框的窗口");
	}
 
}