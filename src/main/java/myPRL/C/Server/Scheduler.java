package myPRL.C.Server;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.LinkedBlockingQueue;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import win.betty35.myPRL.C.CrawlerApp;

public class Scheduler extends HttpServlet 
{
	private static final long serialVersionUID = -7775753145367322923L;
	private static LinkedBlockingQueue<String[]> crawList=new LinkedBlockingQueue<String[]>(); 
	
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		CrawlerApp.init();
		request.setCharacterEncoding("UTF-8"); 
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/json; charset=utf-8");
		String output=null;
		String query=request.getParameter("query");
		String[] searchedWords=query.split(" ");
		if(!CrawlerApp.inQueue(query))
			CrawlerApp.addToList(searchedWords);
		PrintWriter out=response.getWriter();
		out.print(output);
		out.close();
		if(!CrawlerApp.running)
			try {
				CrawlerApp.run();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
