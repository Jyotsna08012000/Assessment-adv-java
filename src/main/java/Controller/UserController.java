package Controller;

import java.io.IOException;
import java.util.Random;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import Dao.UserDao;

import Model.User;
import services.Services;

/**
 * Servlet implementation class UserController
 */
@WebServlet("/UserController")
public class UserController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String action = request.getParameter("action");

		if (action.equalsIgnoreCase("sign-up")) {
			
			String email = request.getParameter("email");
			System.out.println(email);
			User u = UserDao.checkEmail(email);
			
		if ( u!=null) {
				request.setAttribute("msg", "Account registered already");
				request.getRequestDispatcher("sign-up.jsp").forward(request, response);
				
	 } else {
			User u1 = new User();
			u1.setFirstname(request.getParameter("firstname"));
			u1.setLastname(request.getParameter("lastname"));
			u1.setEmail(request.getParameter("email"));
			u1.setMobile(Long.parseLong(request.getParameter("mobile")));
			u1.setAddress(request.getParameter("address"));
			u1.setGender(request.getParameter("gender"));
			u1.setPassword(request.getParameter("password"));
		    String p = request.getParameter("password");
			String cp = request.getParameter("confirmpassword");
		    if(p.equals(cp)) {
	        	  UserDao.insertUser(u1);
	        	request.setAttribute("msg", "Account signup sucessfully");
	 			request.getRequestDispatcher("login.jsp").forward(request, response);	         }
	         else   
	         {
	            request.setAttribute("msg1","password and confirm password are not same");
	            request.getRequestDispatcher("sign-up.jsp").forward(request, response);
	         }
				}
	            }
		 else if (action.equalsIgnoreCase("login")) {
				User u = new User();
				u.setEmail(request.getParameter("email"));
				u.setPassword(request.getParameter("password"));
				User u1 = UserDao.loginUser(u);
				if (u1 == null) {
					request.setAttribute("msg", "password is incorrect");
					request.getRequestDispatcher("login.jsp").forward(request, response);
				} else {
					HttpSession session = request.getSession();
					session.setAttribute("data", u1);
					request.getRequestDispatcher("user-home.jsp").forward(request, response);
				}	
	            }
	           
         else if (action.equalsIgnoreCase("changepassword")) {
			User u = new User();
			int id = Integer.parseInt(request.getParameter("userid"));
			String op = request.getParameter("oldpassword");
			String np = request.getParameter("newpassword");
			String cnp = request.getParameter("confirmpassword");
			boolean flag = UserDao.checkOldPassword(id, op);
			if (flag == true) {
				if (np.equals(cnp)) {
					UserDao.changePasswrod(id, np);
					response.sendRedirect("user-home.jsp");
				} else {
					request.setAttribute("msg1", "old password and new password not matched");
					request.getRequestDispatcher("change-Password.jsp").forward(request, response);
				}
			} else {
				
			}

		} else if (action.equalsIgnoreCase("getotp")) {
			String email = request.getParameter("email");
			boolean flag = UserDao.checkEmailfromDB(email);
			System.out.println(flag);
			if (flag == true) {
				Services s = new Services();
				Random r = new Random();
				int num = r.nextInt(999999);
				System.out.println(num);
				s.sendMail(email, num);
				request.setAttribute("email", email);
				request.setAttribute("otp", num);
				request.getRequestDispatcher("enter-otp.jsp").forward(request, response);
               }
			 else {
					request.setAttribute("msg", "incorrect email id incorrect");
					request.getRequestDispatcher("forgot-password.jsp").forward(request, response);
				}
		} else if (action.equalsIgnoreCase("verify")) {
			String email = request.getParameter("email");
			int otp1 = Integer.parseInt(request.getParameter("otp1"));
			int otp2 = Integer.parseInt(request.getParameter("otp2"));
			if (otp1 == otp2) {
				request.setAttribute("email", email);
				request.getRequestDispatcher("new-password.jsp").forward(request, response);
			}

			else {
				request.setAttribute("email", email);
				request.setAttribute("otp", otp1);
				request.setAttribute("msg", "otp not matched");
				request.getRequestDispatcher("enter-otp.jsp").forward(request, response);
			}

		} else if (action.equalsIgnoreCase("Change Password")) {
			String email = request.getParameter("email");
			String newp = request.getParameter("newp");
			String confirmp = request.getParameter("confirmp");
			System.out.println("confirm new password"+confirmp);
			System.out.println("new password"+newp);
			System.out.println("email"+email);
			if (newp.equals(confirmp)) {
				UserDao.changeNewPassword(email, newp);
				response.sendRedirect("login.jsp");

			} else {
				request.setAttribute("msg", "newp and cnofirmp not matched");
			}
		}
	}
}



