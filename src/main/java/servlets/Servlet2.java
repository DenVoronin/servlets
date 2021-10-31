package servlets;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
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


        writer.print("<h1>Read file:</h1>");
        writer.print("<form action=\"/first\" method=\"get\">\n" +
                "  File name ('.json' add auto, write only name):<br>\n" +
                "  <input type=\"text\" name=\"fileName\" value=\"\">\n" +
                "  <br>\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" value=\"Read\">\n" +
                "</form> ");
        writer.print("<h1>Create new file:</h1>");
        writer.print("<form action=\"/second\" method=\"post\">\n" +
                "  File name ('.json' add auto):<br>\n" +
                "  <input type=\"text\" name=\"fName\" value=\"\">\n" +
                "  <br>\n" +
                "  content :<br>\n" +
                "  <input type=\"text\" name=\"content\" value={\"name\":\"...\",\"age\":\"...\"}>\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" value=\"Create new file\">\n" +
                "</form> ");
        writer.print("<h1>Delete file:</h1>");
        writer.print("<form action=\"/second\" method=\"post\">\n" +
                "  File name ('.json' add auto, write only name):<br>\n" +
                "  <input type=\"text\" name=\"fileNameDelete\" value=\"\">\n" +
                "  <br>\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" value=\"Delete\">\n" +
                "</form> ");
        writer.print("<h1>Edit file:</h1>");
        writer.print("<form action=\"/second\" method=\"post\">\n" +
                "  File name ('.json' add auto, write only name):<br>\n" +
                "  <input type=\"text\" name=\"fileReName\" value=\"\"><br>\n" +
                "  New \"name\" :<br>\n" +
                "  <input type=\"text\" name=\"reName\" value=\"\"><br>\n" +
                "  New \"age\" :<br>\n" +
                "  <input type=\"text\" name=\"reAge\" value=\"\"><br>\n" +
                "  <br>\n" +
                "  <br><br>\n" +
                "  <input type=\"submit\" value=\"Edit\">\n" +
                "</form> ");
    }
    public void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        PrintWriter writer = httpServletResponse.getWriter();
        writer.print("<h1>Ok</h1>");
        writer.print("<a href=\"/second\">to list files</a>");

        if (httpServletRequest.getParameter("fName") != null) {
            String fileName = httpServletRequest.getParameter("fName");
            String content = httpServletRequest.getParameter("content");
            File dir1 = new File("./src/main/java/resources/" + fileName + ".json");
            dir1.createNewFile();
            FileWriter fileWriter = new FileWriter(dir1);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();
            writer.print("<h1>New file is created</h1>");
        }

        if (httpServletRequest.getParameter("fileNameDelete") != null) {
            String fileNameDelete = httpServletRequest.getParameter("fileNameDelete");
            File f = new File("./src/main/java/resources/" + fileNameDelete + ".json");
            if (f.isFile() && !f.isDirectory()) {
                f.delete();
                writer.print("<h1>File " + fileNameDelete + ".json deleted</h1>");
            }
        }

        if (httpServletRequest.getParameter("fileReName") != null) {
            String fileName = httpServletRequest.getParameter("fileReName");
            String name = httpServletRequest.getParameter("reName");
            String age = httpServletRequest.getParameter("reAge");
            File f = new File("./src/main/java/resources/" + fileName + ".json");
            if (f.isFile() && !f.isDirectory()) {
              

                Object obj = null;
                try {
                    obj = new JSONParser().parse(new FileReader("./src/main/java/resources/" + fileName + ".json"));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                JSONObject jo = (JSONObject) obj;
                    String jName = (String) jo.get("name");
                    String jAge = (String) jo.get("age");
                String jString = null;

                if (!name.equals(jName)) {
                    jString = "{\"name\":\"" + name + "\",";
                } else {
                    jString = "{\"name\":\"" + jName + "\",";
                }

                if (!age.equals(jAge)) {
                    jString = jString + "\"age\":\"" + age + "\"}";
                } else {
                    jString = jString + "\"age\":\"" + jAge + "\"}";
                }
                String fName = httpServletRequest.getParameter("fileReName");
                String content = jString;
                File dir1 = new File("./src/main/java/resources/" + fName + ".json");
                FileWriter fileWriter = new FileWriter(dir1);
                fileWriter.write(content);
                fileWriter.flush();
                fileWriter.close();

            }
        }
    }
    public void doDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        String fileName = httpServletRequest.getParameter("fName");
        File f = new File("./src/main/java/resources/" + fileName + ".json");
        if(f.isFile() && !f.isDirectory()) {
            f.delete();
        }
    }
    public void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {
        String fileName = httpServletRequest.getParameter("fileReName");
        String name = httpServletRequest.getParameter("reName");
        String age = httpServletRequest.getParameter("reAge");
        File f = new File("./src/main/java/resources/" + fileName + ".json");
        if (f.isFile() && !f.isDirectory()) {


            Object obj = null;
            try {
                obj = new JSONParser().parse(new FileReader("./src/main/java/resources/" + fileName + ".json"));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            JSONObject jo = (JSONObject) obj;
            String jName = (String) jo.get("name");
            String jAge = (String) jo.get("age");
            String jString = null;

            if (!name.equals(jName)) {
                jString = "{\"name\":\"" + name + "\",";
            } else {
                jString = "{\"name\":\"" + jName + "\",";
            }

            if (!age.equals(jAge)) {
                jString = jString + "\"age\":\"" + age + "\"}";
            } else {
                jString = jString + "\"age\":\"" + jAge + "\"}";
            }
            String fName = httpServletRequest.getParameter("fileReName");
            String content = jString;
            File dir1 = new File("./src/main/java/resources/" + fName + ".json");
            FileWriter fileWriter = new FileWriter(dir1);
            fileWriter.write(content);
            fileWriter.flush();
            fileWriter.close();

        }
    }



}

