package Web;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

import domain.user;
import jdbcUtils.JDBCUtils;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//��ȡ�û���������
		String name = request.getParameter("username");
		String pwd = request.getParameter("password");
		System.out.println(name+"|���룺"+pwd);
		//�����ݿ��в�ѯ��û�и��û�
		QueryRunner qr = new QueryRunner(JDBCUtils.getDataSource());
		String sql = "select * from user where username=? and password=?";
		user u = null;
		try {
			 u = qr.query(sql, new BeanHandler<>(user.class),name,pwd);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		response.setContentType("text/html;charset=utf-8");
		//�ж���û��ֵ
		if (u != null) {
			response.getWriter().write("��½�ɹ�");
			//�����û����ĸ��� ��session��
			HttpSession session = request.getSession();
			session.setAttribute("user",u); 
			//��ת����ҳ��
			response.sendRedirect("/Mystore-RegisterAndLogin/index.jsp");
		}else {
			response.getWriter().write("��¼ʧ��");
			//��ת��ע��ҳ��
			response.sendRedirect("/Mystore-RegisterAndLogin/login.jsp");
		}
	}
}