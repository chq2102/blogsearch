package bs.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.wltea.analyzer.lucene.IKAnalyzer;
import org.apache.lucene.store.Directory ;
import bs.index.IndexCreate;
import bs.search.IndexSearch;

public class BlogSearchServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public BlogSearchServlet() {
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

		response.setContentType("text/html");
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8") ;
		String searchContent = request.getParameter("search_content");
		
		List<String> searchResult = new IndexSearch().search(searchContent) ;
		
		request.setAttribute("searchResult", searchResult) ;
		request.setAttribute("pagesize", 0) ;
		request.getRequestDispatcher("searchResult.jsp").forward(request, response);
		
	}

	public void init() throws ServletException {
		// Put your code here
	}

}
