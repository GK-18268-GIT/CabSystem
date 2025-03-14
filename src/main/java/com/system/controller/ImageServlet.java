package com.system.controller;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private String uploadPath;

    @Override
    public void init() {
        
        uploadPath = getServletConfig().getInitParameter("uploadPath");
        new File(uploadPath).mkdirs(); 
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        String filename = request.getPathInfo().substring(1); 
        File file = new File(uploadPath, filename);
        
        if (file.exists() && !file.isDirectory()) {
            response.setContentType(getServletContext().getMimeType(filename));
            response.setContentLengthLong(file.length());
            Files.copy(file.toPath(), response.getOutputStream());
        } else {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
