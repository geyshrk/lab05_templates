package ru.itis.dis301.lab05;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/coolname")
public class CoolNamePageServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        String osname = (String) request.getHeader("Sec-Ch-Ua-Platform");
        String coolname;
        switch (osname) {
            case ("\"Windows\""): {
                coolname = "Оконщик";
                break;
            }
            case ("\"macOS\""): {
                coolname = "Маковод";
                break;
            }
            case ("\"Linux\""): {
                coolname = "Линуксовод";
                break;
            }
            default: coolname = "Неизвестный субъект";

        }
        request.setAttribute("name", coolname);
        try {
            request.getRequestDispatcher("/template/coolname.thtml").forward(request, response);
        } catch (ServletException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
