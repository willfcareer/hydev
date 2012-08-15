package org.wangfy.dev.ftp.client;
/****
 * version 0.01
 * 简单的测试ftp的工作原理的代码，没有使用一些错误检验措施。
 * 使用ftp.njupt.edu.cn作为ftp服务器。
 * 目标一：获得服务器上的文件列表。
 * 用户名是anonymous
 * 密码  ：无
 * 过程是首先建立控制通道，如果进行get,put LIST等操作时，要使用数据通道
 * 过程是，先设置数据传输模式，获得服务器与客户机数据传输的端口，然后建立一个socket绑定此端口，
 * 然后发送LIST命令到控制端口，用数据端口获得返回的数据。
 * *******************/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;


public class Ftpc{
	private  Socket  Sock;
	private  String strip;
	private  int Port;
	private PrintStream out;
	private BufferedReader in;
	
	public Ftpc(String str,int port)throws IOException{
		strip=str;
		Port=port;
		Sock=new Socket(strip,Port);
	}
	public static void main(String args[])throws  IOException{
 
		String strSendCmd;
		String strRec;
 
		try	{ 
			/*
			String strip = ""; 		
			if(args.length > 0){
				strip = args[0]; 
			}else{
				System.out.println("Useage java Ftpc ipAddress");    
				return;	
			}
			*/			
			//String strip=GetIP("ftp.njupt.edu.cn");
			String strip = "202.4.130.193";
			Ftpc ftpClient=new Ftpc(strip,21);
			//初始化 控制通道
			ftpClient.InitSockStream(ftpClient.Sock);
			//获得控制通道的信息
			System.out.println(ftpClient.in.readLine());
			//登陆到ftp
			ftpClient.BuildConnection(ftpClient);
			//获得服务器上的文件或文件夹列表
			ArrayList fileList= ftpClient.GetFileList(ftpClient);  
			//改变目录
			strSendCmd=ftpClient.strInteract();
			while(!strSendCmd.equals("quit")){
				if(strSendCmd.startsWith("down")){
					String souFileName = ftpClient.GetSouFileName(strSendCmd);
					System.out.println("Get the file name will down :"+souFileName);
					ftpClient.Get(ftpClient,souFileName,"temp");
					
				}
				ftpClient.ChDir(ftpClient,strSendCmd);
				//  获得服务器上的文件或文件夹列表
				fileList= ftpClient.GetFileList(ftpClient);  
				strSendCmd=ftpClient.strInteract();
			}
			//退出ftp
			strSendCmd="QUIT";
			strRec=ftpClient.SendCommand(strSendCmd);   
			ftpClient.SockClose(ftpClient.Sock);//关闭控制通道
		}catch(IOException E){
			System.out.println(E.toString());
		}

	}
	//通过域名获得IP
	public static  String GetIP(String strDomainName)throws IOException{
		InetAddress IP=InetAddress.getByName(strDomainName);
		String strip=IP.toString().substring(IP.toString().indexOf("/")+1); 
		return strip;
	}
	//初始化控制通道
	public  void InitSockStream(Socket Sock)throws IOException{
		out=new PrintStream(Sock.getOutputStream());
		in=new BufferedReader(new InputStreamReader(Sock.getInputStream()));  
	}
	//获得服务器上的目录或者文件列表
	public  ArrayList GetFileList(Ftpc ftpClient)throws IOException{
		//设置模式PASV or PORT
		ArrayList FileList=new ArrayList();
		String strSendCmd="PASV";
		String strRec = ftpClient.SendCommand(strSendCmd);
		
		Socket Client = ftpClient.CreateDataSocket(ftpClient.GetPort(strRec));
		
		strSendCmd = "NLST";
		ftpClient.SendCommand(strSendCmd);
		PrintStream outData;
		BufferedReader inData;
		//获得数据通道传出的数据
		outData = new PrintStream(Client.getOutputStream());
		inData = new BufferedReader(new InputStreamReader(Client.getInputStream()));
		String s;
		while ((s = inData.readLine()) != null) {
			System.out.println(s);
			FileList.add(s);
		}
		ftpClient.SockClose(Client);//关闭数据通道
		System.out.println(ftpClient.in.readLine()); //获得控制通道的信息
		return FileList;
		}
	//处理用户输入
	public String strInteract()throws IOException{
		String strCmd;
		InputStreamReader ir;
		BufferedReader in;
		ir=new InputStreamReader(System.in);
		in=new BufferedReader(ir);
		System.out.println("Directory:");
	
		strCmd=in.readLine();
		return strCmd;
	}
	//登陆到ftp
	public  void BuildConnection(Ftpc ftpClient)throws IOException {
	     String strSendCmd;
	     //输入用户名
	     strSendCmd="USER anonymous";
	     ftpClient.SendCommand(strSendCmd);
	     //输入密码
	     strSendCmd="PASS ";
	     ftpClient.SendCommand(strSendCmd);
	     //输入格式控制模式
	     strSendCmd="TYPE A";
	     ftpClient.SendCommand(strSendCmd); 
	}
	//改变目录
	public void ChDir(Ftpc ftpClient,String DirName)throws IOException{
	        String strSendCmd = "CWD " + DirName;
	  ftpClient.SendCommand(strSendCmd);
	}
	//从返回值中得到端口
	public  String [] GetPort(String ss)
	{
	     int iPort=ss.indexOf("(");
	     int ePort=ss.lastIndexOf(")");
	     String tt=ss.substring(iPort+1,ePort);
	     String []kk;
	     kk=tt.split(",");
	     return kk;
	}
	public String GetSouFileName(String strSendCmd){
		String souFileName;
		String []kk;
		kk = strSendCmd.split(" ");
		souFileName = kk[1];
		return souFileName;
	}
	//创建数据连接socket
	public Socket CreateDataSocket(String [] strArgu)throws IOException{
	  
	    int Port=Integer.parseInt(strArgu[4])*256+Integer.parseInt(strArgu[5]);
	    String StrIP=strArgu[0]+"."+strArgu[1]+"."+strArgu[2]+"."+strArgu[3]; 
	    Socket Client=new Socket(StrIP,Port);
	    return Client;
	   
	}
	public void Get(Ftpc ftpClient,String SouFileName,String DistFileName)throws IOException{
	   
		//输入格式控制模式
		String strSendCmd = "TYPE I";
		ftpClient.SendCommand(strSendCmd);
		//设置模式PASV or PORT
		strSendCmd = "PASV";
		String strRec = ftpClient.SendCommand(strSendCmd);	  
		Socket Client = ftpClient.CreateDataSocket(ftpClient.GetPort(strRec));
		PrintStream outData;
		BufferedReader inData;
		//获得数据通道传出的数据
		outData = new PrintStream(Client.getOutputStream());
		inData = new BufferedReader(new InputStreamReader(Client
	    .getInputStream()));
		String s;
		while ((s = inData.readLine()) != null){
			System.out.println(s);
		}
		System.out.println("Document is empty!");
		ftpClient.SockClose(Client);//关闭数据通道
	}
	//向控制通道发送命令
	public String SendCommand(String strcmd)throws IOException{
		out.println(strcmd);//发送控制命令
		String strRec = in.readLine();
		System.out.println(strRec);//输出控制器返回的消息
		return strRec;
	}
	//关闭socket
	public void SockClose(Socket sock)throws IOException{
		if (sock != null){
			sock.close();
			sock = null;
		}
	}
}

