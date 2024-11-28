package ru.itis.dis301.lab05;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

@WebServlet("*.thtml")
public class TemplateHandlerServlet extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response) {

        InputStream inputStream = TemplateHandlerServlet
                .class.getClassLoader().getResourceAsStream(request.getServletPath());

        try {
            byte[] content = inputStream.readAllBytes();
            //Делаем StringBuilder из считанной странички
            StringBuilder contentTemplate = new StringBuilder(new String(content));
            //Проходим по всем аттрибутам
            Iterator<String> attributeNames = request.getAttributeNames().asIterator();
            while(attributeNames.hasNext()){
                String attributeName = attributeNames.next();
                String attributeVariable = String.format("${%s}", attributeName);
                String attributeValue = "";
                //Ищем, есть ли у нас в HTML странице переменные, которые можно заменить на значения аттрибутов
                if (contentTemplate.indexOf(attributeVariable) != -1) {
                    attributeValue = (String) request.getAttribute(attributeName);
                }
                //Меняем все переменные на значения аттрибутов
                while(contentTemplate.indexOf(attributeVariable) != -1){

                    contentTemplate.replace(contentTemplate.indexOf(attributeVariable),
                            contentTemplate.indexOf(attributeVariable) + attributeVariable.length(),
                            attributeValue);
                }
            }
            response.getWriter().write(contentTemplate.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
