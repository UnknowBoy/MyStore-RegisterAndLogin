package Web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;

import domain.user;
import jdbcUtil.JDBCUtil;


@WebServlet("/RegistServlet")
public class RegistServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("RegistServlet");
		/**
		 * �ж��������֤���Ƿ���ȷ
		 * �������в���
		 * �Ѳ�����װ��user����
		 * ����uid
		 * д�뵽���ݿ�
		 * */
		//�����������
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		
		//��ȡ����   ��֤��
		String code = request.getParameter("code");
		System.out.println("code="+code);
		//��ȡ���������ɵ���֤��
		String word = (String) this.getServletContext().getAttribute("checkCode");
		//�ж��������֤
		if (code.equals(word)) {
			//�����ȷ
			//1.�������в���
			Map<String, String[]> parameterMap = request.getParameterMap();
			
			for(Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
				System.out.println("----------");
				System.out.println(entry.getKey()+":"+Arrays.toString(entry.getValue()));
			}

			user u = new user();
			//2.�ѽ��յĲ�����װ��user����
			try {
				BeanUtils.populate(u, parameterMap);
			} catch (IllegalAccessException | InvocationTargetException e) {
				e.printStackTrace();
			}
			System.out.println(u);
			
			//3.����uid
			u.setUid(UUID.randomUUID().toString());
			//4.д�����ݿ�
			QueryRunner qr = new QueryRunner(JDBCUtil.getDataSource());
			String sql="insert into user value(?,?,?,?)";
			try {
				qr.update(sql,u.getUid(),u.getUsername(),u.getPassword(),u.getPhone());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//��ת����¼ҳ��
			response.getWriter().write("ע��ɹ�");
			response.setHeader("refresh", "3,url=/Mystore-RegisterAndLogin/login.jsp");
		} else {
			//����ȷ���û���֤�������ת��ע��ҳ
			response.getWriter().write("��֤�����");
			response.setHeader("refresh", "3;url=/Mystore-RegisterAndLogin/regist.jsp");
		}
	}

}
