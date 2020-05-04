package com.criptografie;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "/caesarServlet")
public class CaesarServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String plainText = request.getParameter("plainText");
        String k = request.getParameter("K");
        String key = request.getParameter("key");

        String encryptedText = encrypt(plainText, Integer.parseInt(k));
        String decryptedText = decrypt(encryptedText, Integer.parseInt(k));

        request.setAttribute("plainText", plainText);
        request.setAttribute("encryptedTextWithCaesar", encryptedText);
        request.setAttribute("decryptedTextWithCaesar", decryptedText);
        request.setAttribute("key", key);

        RequestDispatcher rd = request.getRequestDispatcher("/polybiusServlet");
        rd.forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }


    protected String encrypt(String plainText, int k) {
        return computeResult(plainText, k);
    }


    protected String decrypt(String encryptedText, int k) {
        k = 26 - k;
        return computeResult(encryptedText, k);
    }

    private String computeResult(String text, int k) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            if (Character.isSpaceChar(text.charAt(i))) {
                result.append(" ");
            } else if (Character.isUpperCase(text.charAt(i))) {
                char ch = (char) (((int) text.charAt(i) +
                        k - 65) % 26 + 65);
                result.append(ch);
            } else {
                char ch = (char) (((int) text.charAt(i) +
                        k - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result.toString();
    }
}
