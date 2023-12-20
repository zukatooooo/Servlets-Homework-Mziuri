package com.mziuri;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class MyServlet extends HttpServlet {

    private Map<String, String> subjectMap;

    @Override
    public void init() {
        subjectMap = new HashMap<>();
        subjectMap.put("Java", "Saba");
        subjectMap.put("History", "Nato");
        subjectMap.put("Russian", "Diana");
        subjectMap.put("Georgian", "Gela");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        StringBuilder builder = new StringBuilder();
        String getAll = req.getParameter("getAll");
        if (getAll != null && getAll.equalsIgnoreCase("TRUE")) {
            subjectMap.forEach((k, v) -> builder.append(k).append(": ").append(v).append("\n"));
        }
        else {
            String subject = req.getParameter("subject");
            if (subjectMap.containsKey(subject)) {
                builder.append(subjectMap.get(subject));
            }
        }
        resp.getWriter().write("<h1> " + builder.toString() + " </h1>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String subject = req.getParameter("subject");
        String teacher = req.getParameter("teacher");
        String result;
        if (!subjectMap.containsKey(subject)) {
            subjectMap.put(subject, teacher);
            result = "Added new subject: " + subject;
        }
        else {
            result = subject + " already exists";
        }
        resp.getWriter().write("<h2> " + result + " </h2>");
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String subject = req.getParameter("subject");
        String teacher = req.getParameter("teacher");
        String result;
        if (subjectMap.containsKey(subject)) {
            subjectMap.put(subject, teacher);
            result = "Replaced " + subject;
        }
        else {
            result = subject + " not found";
        }
        resp.getWriter().write("<h2> " + result + " </h2>");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        String subject = req.getParameter("subject");
        String result;
        if (subjectMap.remove(subject) == null) {
            result = "Could not remove " + subject;
        }
        else {
            result = "Deleted " + subject;
        }
        resp.getWriter().write("<h2> " + result + " </h2>");
    }

}
