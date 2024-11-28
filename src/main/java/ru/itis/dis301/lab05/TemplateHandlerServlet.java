package ru.itis.dis301.lab05;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Objects;

@WebServlet("*.thtml")
public class TemplateHandlerServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        String contextPath = request.getContextPath();
        String pathInfo = request.getPathInfo();
        String servletPath = request.getServletPath();

        Iterator<String> iter = request.getAttributeNames().asIterator();


        InputStream inputStream = TemplateHandlerServlet
                .class.getClassLoader().getResourceAsStream(request.getServletPath());

        try {
            byte[] content = inputStream.readAllBytes();

            String contentTemplate = new String(content);

            // Заменить параметры значениями

            response.getWriter().write(contentTemplate);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
