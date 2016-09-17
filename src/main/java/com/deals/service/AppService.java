package com.deals.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import com.deals.model.AdminDetail;
import com.deals.repository.PlanRepository;
import com.deals.repository.SalesManagerRepository;
import com.deals.repository.SalesmanRepository;
import com.deals.repository.UserRepository;
import com.deals.util.App;

@Service
public class AppService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private SalesmanRepository salesmanRepository;
	
	@Autowired
	private SalesManagerRepository salesManagerRepository;
	
	@Autowired
	private PlanRepository planRepository;
	
	@Value("${img.copy.path}")
	private String imgCopyPath;
	
	@Value("${img.server.path}")
	private String imgServerPath;
	
	public String copyFile(MultipartFile file){
		String fileName = null; 
		try {
			fileName = App.generateKey(6)+"_"+file.getOriginalFilename();
			File outFile = new File(imgCopyPath+fileName);
			
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
		System.out.println("Delete path :::: "+deletePath);
		try {
			FileUtils.forceDeleteOnExit(new File(deletePath));
			System.out.println("File Deleted successfully");
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
		adminDetail.setName("Sales Mans");
		adminDetail.setCount(salesmanRepository.count());
		adminDetail.setIconName("fa fa-users");
		adminDetail.setColorName("bg-green");
		adminDetail.setLandingPath("salesman");
		adminDetails.add(adminDetail);
		
		
		adminDetail = new AdminDetail();
		adminDetail.setName("Sales Managers");
		adminDetail.setCount(salesManagerRepository.count());
		adminDetail.setIconName("fa fa-users");
		adminDetail.setColorName("bg-yellow");
		adminDetail.setLandingPath("salesManager");
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
