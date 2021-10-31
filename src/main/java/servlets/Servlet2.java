package servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;


public class Servlet2 extends HttpServlet
{
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        httpServletResponse.setContentType("text/html");
        String pathToFolder = "./src/main/java/resources/";
        File dir = new File(pathToFolder);
        File[] arrFiles = dir.listFiles();
        List<File> lst = Arrays.asList(arrFiles);
        List<String> names = lst.stream().map(e -> e.getName()).toList();
        PrintWriter writer = httpServletResponse.getWriter();
        writer.print("<h1>Files:</h1>");
        names.stream().forEach(writer::println);

    String fileName = httpServletRequest.getParameter("fileName");

        writer.print("<h1>Create new file:</h1>");
        writer.print("<form action=\"/second\" method=\"post\">\n" +
                "  File name ('.json' add auto):<br>\n" +
                "  <input type=\"text\" name=\"fName\" value=\"\">\n" +
                "  <br>\n" +
                "  content:<br>\n" +
                "  <input type=\"text\" name=\"content\" value={\"name\":\"...\",\"age\":...}>\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" value=\"Submit\">\n" +
                "</form> ");
    }
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        PrintWriter writer = httpServletResponse.getWriter();
        writer.print("<h1>Ok</h1>");
        writer.print("<a href=\"/second\">to list files</a>");
        String fileName = httpServletRequest.getParameter("fName");
        if (!fileName.isEmpty()) {
            String content = httpServletRequest.getParameter("content");
            File dir1 = new File("./src/main/java/resources/" + fileName + ".json");
            dir1.createNewFile();
            FileWriter fileWriter = new FileWriter(dir1);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
        }
    }

    }

