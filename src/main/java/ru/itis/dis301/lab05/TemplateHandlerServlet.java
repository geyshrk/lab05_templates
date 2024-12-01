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
import java.util.Map;
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
            //Проходим по всем параметрам
            Map<String, String[]> paramMap = request.getParameterMap();
            for(String paramName : paramMap.keySet()) {
                String paramVariable = String.format("${%s}", paramName);
                String attributeValue = paramMap.get(paramName)[0];

                //Меняем все переменные на значения аттрибутов
                while(contentTemplate.indexOf(paramVariable) != -1){

                    contentTemplate.replace(contentTemplate.indexOf(paramVariable),
                            contentTemplate.indexOf(paramVariable) + paramVariable.length(),
                            attributeValue);
                }
            }
            response.getWriter().write(contentTemplate.toString());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

}
