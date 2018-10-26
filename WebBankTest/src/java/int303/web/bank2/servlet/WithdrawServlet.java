/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package int303.web.bank2.servlet;

import int303.web.bank2.controllers.AccountJpaController;
import int303.web.bank2.controllers.HistoryJpaController;
import int303.web.bank2.controllers.exceptions.NonexistentEntityException;
import int303.web.bank2.controllers.exceptions.RollbackFailureException;
import int303.web.bank2.model.Account;
import int303.web.bank2.model.History;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.UserTransaction;

/**
 *
 * @author Mild-TN
 */
public class WithdrawServlet extends HttpServlet {
    @PersistenceUnit(unitName = "WebBank102PU")
    EntityManagerFactory emf;
    
    @Resource 
    UserTransaction utx;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session =  request.getSession();
        String withdrawStr = request.getParameter("withdraw");
        Account accountSession = (Account)session.getAttribute("account");
        if(accountSession != null && withdrawStr.length()>0){
            AccountJpaController accountJpaController =  new AccountJpaController(utx, emf);
            HistoryJpaController historyJpaController = new HistoryJpaController(utx, emf);
            Account account = accountJpaController.findAccount(accountSession.getAccountid());
            if(account != null){
                int withdraw = Integer.parseInt(withdrawStr);
                boolean checkWith = account.withdraw(withdraw);
                if(checkWith){
                    History history = new  History("withdraw", account.getBalance(), new Date(), withdraw, account);
                    try {
                        accountJpaController.edit(account);
                        session.setAttribute("massage", "Withdraw successful !!");
                        historyJpaController.create(history);
                        session.setAttribute("account", account);
//                        getServletContext().getRequestDispatcher("/MyAccount.jsp").forward(request, response);
                    response.sendRedirect("MyAccount");
                    return;
                    } catch (NonexistentEntityException ex) {
                        Logger.getLogger(WithdrawServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (RollbackFailureException ex) {
                        Logger.getLogger(WithdrawServlet.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (Exception ex) {
                        Logger.getLogger(WithdrawServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }else{
                    session.setAttribute("massage", "Withdraw  Unsuccessful !!");
                    response.sendRedirect("MyAccount");
                }
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
