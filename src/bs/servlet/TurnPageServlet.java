package bs.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TurnPageServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public TurnPageServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8") ;
		//int pagesize = (Integer) request.getAttribute("pagesize") ;
		int pagesize = Integer.parseInt(request.getParameter("pagesize")) ;
		List<String> list = (List<String>) request.getSession().getAttribute("list");
		
		request.setAttribute("searchResult", list) ;
		request.setAttribute("pagesize", pagesize) ;
		request.getRequestDispatcher("searchResult.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}

}
