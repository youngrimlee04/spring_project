package edu.spring.sample.view;

import java.io.FileInputStream;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class FileDownloadView extends AbstractView {

	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
				
		response.setHeader("Content-Disposition",
				 "attachment; filename=\"screen.jpg\";charset=\"UTF-8\"");
		
		FileInputStream in = new FileInputStream("/dev/screen.jpg");
		ServletOutputStream out = response.getOutputStream();
		FileCopyUtils.copy(in, out); // �Ʒ� �ݺ��� ��ü �ڵ�
		
		/*
		 * while(true) { int data = in.read(); if(data == -1 ) break; out.write(data); }
		 */
	}

}
