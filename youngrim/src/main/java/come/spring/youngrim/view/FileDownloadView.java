package come.spring.youngrim.view;

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
				 "attachment; filename=\"exerd.jpg\";charset=\"UTF-8\"");
		
		FileInputStream in = new FileInputStream("/dev/exerd.jpg");
		ServletOutputStream out = response.getOutputStream();
		FileCopyUtils.copy(in, out); // 아래 반복문 대체 코드
		
	}

}
