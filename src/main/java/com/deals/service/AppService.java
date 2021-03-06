package com.deals.service;

import com.deals.model.AdminDetail;
import com.deals.repository.PlanRepository;
import com.deals.repository.UserRepository;
import com.deals.util.App;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Service
public class AppService {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Value("${img.copy.path}")
	private String imgCopyPath;

	@Value("${img.server.path}")
	private String imgServerPath;
	
	public String copyFile(MultipartFile file){
		String fileName = null; 
		try {
			fileName = App.generateKey(6)+"_"+file.getOriginalFilename().replaceAll("\\s+","");
			File outFile = new File( imgCopyPath + fileName);
			
			FileCopyUtils.copy(file.getBytes(), outFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return imgServerPath+fileName;
	}

	public String copyFileInputstream(Part part, HttpServletResponse res){
		OutputStream out = null;
		InputStream fileContent = null;
		String fileName = App.generateKey(6)+"_"+getFileName(part);
		
		try {
			PrintWriter writer = res.getWriter();
			out = new FileOutputStream(new File(imgCopyPath+fileName));
			fileContent = part.getInputStream();
			int read = 0;
			final byte[] bytes = new byte[1024];
			
			while((read=fileContent.read(bytes)) != -1){
				out.write(bytes, 0, read);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return imgServerPath+fileName;
		
	}
	
	public String deleteFileFromServer(String pathName){
		String res = "FAILED";
		String deletePath = (imgCopyPath+getImageNameFromUrl(pathName)).trim();
		log.info("Delete path :::: "+deletePath);
		try {
			FileUtils.forceDeleteOnExit(new File(deletePath));
			log.info("File Deleted successfully");
			res = "SUCCEED";
		} catch (IOException e) {
			res = "Error in deleting. Please try again later";
			e.printStackTrace();
		}
		return res;
	}
	
	public String getFileName(final Part part){
		final String partHeader = part.getHeader("content-disposition");
		String fileName = partHeader.substring(partHeader.lastIndexOf("=")+1, partHeader.length());
		if(fileName != null )
			fileName = fileName.replaceAll("\"", "");
		
		return fileName;
	}
	
	private String getImageNameFromUrl(String imgUrl){
		return imgUrl.substring((imgUrl.lastIndexOf("/")+1));
	}
	
	// Admin dashboard details
	public List<AdminDetail> getAdminDetail(){
		List<AdminDetail> adminDetails = new ArrayList<>();
		AdminDetail adminDetail = new AdminDetail();
		adminDetail.setName("Users");
		adminDetail.setCount(userRepository.count());
		adminDetail.setIconName("fa fa-users");
		adminDetail.setColorName("bg-aqua");
		adminDetail.setLandingPath("user");
		adminDetails.add(adminDetail);
		
		adminDetail = new AdminDetail();
		adminDetail.setName("Plans");
		adminDetail.setCount(planRepository.count());
		adminDetail.setIconName("fa fa-archive");
		adminDetail.setColorName("bg-red");
		adminDetail.setLandingPath("plan");
		adminDetails.add(adminDetail);
		
		return adminDetails;
		
	}
	
}
