package com.criptografie;

import javafx.util.Pair;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "/polybiusServlet")
public class PolybiusServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        String key = request.getAttribute("key").toString().toLowerCase();
        String plainText = request.getAttribute("plainText").toString().toLowerCase();
        String encryptedTextWithCaesar = request.getAttribute("encryptedTextWithCaesar").toString().toLowerCase();
        String decryptedTextWithCaesar = request.getAttribute("decryptedTextWithCaesar").toString().toLowerCase();

        if (encryptedTextWithCaesar.length() > key.length()) {
            out.println("Key length should be at least the length of the plain text!");
            out.println("You will be redirected back to the form in 7 seconds.");
            response.setHeader("Refresh", "7; URL=http://localhost:8080/Criptografie_tema_1_war_exploded/Caesar.jsp");
        } else {
            out.println("Plain text: " + plainText);
            out.println("Encrypted text with Caesar: " + encryptedTextWithCaesar);
            out.println("Decrypted text with Caesar: " + decryptedTextWithCaesar);
            out.println();
            out.println();
            out.println("Plain text for polybius cipher: " + encryptedTextWithCaesar.replace('j', 'i'));
            char[][] polybiusSquare = createPolybiusSquare(key);
            String encryptedTextWithPolybius = encrypt(polybiusSquare, encryptedTextWithCaesar.replace('j', 'i'));
            out.println("Encrypted text with Polybius: " + encryptedTextWithPolybius);
            String decryptedTextWithPolybius = decrypt(polybiusSquare, encryptedTextWithPolybius);
            out.println("Decrypted text with Polybius: " + decryptedTextWithPolybius);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected String encrypt(char[][] square, String plainText) {
        StringBuilder encryptedText = new StringBuilder();

        for (char c : plainText.toCharArray()) {
            if (c == ' ') {
                encryptedText.append(c);
            } else {
                Pair<Integer, Integer> coordinatesOfC = getLineAndColForCharacter(square, c);
                if (coordinatesOfC.getKey() == -1) {
                    continue;
                }
                encryptedText.append(coordinatesOfC.getKey());
                encryptedText.append(coordinatesOfC.getValue());
            }
        }
        return encryptedText.toString();
    }

    protected String decrypt(char[][] square, String encryptedText) {
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < encryptedText.length(); i += 2) {
            while (encryptedText.charAt(i) == ' ') {
                i++;
                decryptedText.append(' ');
            }
            while (encryptedText.charAt(i + 1) == ' ') {
                i++;
                decryptedText.append(' ');
            }
            int col = i + 1;

            char c = square[(int) encryptedText.charAt(i) - 48][(int) encryptedText.charAt(col) - 48];
            decryptedText.append(c);
        }
        return decryptedText.toString();
    }

    private Pair<Integer, Integer> getLineAndColForCharacter(char[][] square, char c) {
        for (int i = 0; i < square.length; i++) {
            for (int j = 0; j < square[i].length; j++) {
                if (c == square[i][j]) {
                    return new Pair<>(i, j);
                }
            }
        }
        return new Pair<>(-1, -1);
    }


    private char[][] createPolybiusSquare(String key) {
        char[][] square = new char[5][5];
        int i = 0;
        int j = 0;
        for (char c : key.toLowerCase().toCharArray()) {
            if (c == ' ') {
                continue;
            }

            if (charIsNotDuplicate(square, c)) {
                if ((c == 'i' || c == 'j') && (charIsNotDuplicate(square, 'i'))) {
                    square[i][j] = 'i';
                } else {
                    square[i][j] = c;
                }
                if (j == 4) {
                    j = 0;
                    i++;
                } else {
                    j++;
                }
            }
        }

        String alphabet = "abcdefghiklmnopqrstuvwxyz";
        for (char c : alphabet.toCharArray()) {
            if (charIsNotDuplicate(square, c)) {
                square[i][j] = c;
                if (j == 4) {
                    j = 0;
                    i++;
                } else {
                    j++;
                }
            }
        }
        return square;
    }

    private boolean charIsNotDuplicate(char[][] square, char c) {
        for (char[] line : square) {
            for (char character : line) {
                if (character == c) {
                    return false;
                }
            }
        }
        return true;
    }
}
