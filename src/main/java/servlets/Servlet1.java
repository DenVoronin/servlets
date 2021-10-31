package servlets;


import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class Servlet1 extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        httpServletResponse.setContentType("application/json");

        JSONParser parser = new JSONParser();

        JSONObject data = null;


        String fileName = httpServletRequest.getParameter("fileName");

        if (fileName == null) {
            fileName = "example";
        }
        try {
            data = (JSONObject) parser.parse(
                    new FileReader("./src/main/java/resources/" + fileName + ".json"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String json = data.toJSONString();

        try (PrintWriter writer = httpServletResponse.getWriter()) {
            writer.println(json);

            writer.flush();
        }


    }
    }



