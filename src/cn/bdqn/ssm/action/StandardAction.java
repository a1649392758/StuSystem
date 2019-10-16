package cn.bdqn.ssm.action;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import cn.bdqn.ssm.common.DateUtils;
import cn.bdqn.ssm.common.ExcelUtils;
import cn.bdqn.ssm.common.Page;
import cn.bdqn.ssm.entity.Standard;
import cn.bdqn.ssm.service.StandardService;

/**
 * @description 标准信息控制层
 * @author 李洋
 * @address 北大青鸟沈阳三好中心
 * @created 2017年7月2日 下午1:43:51
 * @version 1.0.0
 */
@Controller
@RequestMapping("/standard")
public class StandardAction {

	@Resource
	private StandardService standardService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) throws Exception {
		binder.registerCustomEditor(
				Date.class, 
				new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"),true));
	}
	
	@RequestMapping("/select")
	@ResponseBody
	public Map<String, Object> standardAllSelect(String pageNo,String stdNum){
		Map<String, Object> map = new LinkedHashMap<>();
		//利用commons-lang3中自带的工具类
		if(StringUtils.isBlank(pageNo)){
			pageNo = "1";
		}
		int currPageNO = Integer.parseInt(pageNo);
		Page<Standard> page = standardService.standardAllSelect(currPageNO, stdNum);
		int totalCount = page.getAllRecordNO();
		map.put("totalCount", totalCount);
		map.put("standardList", page.getList());
		map.put("page", pageNo);
		map.put("maxPageNo", page.getAllPageNO());
		return map;
	}
	
	@RequestMapping("/add")
	public String standardInsert(Standard standard,@RequestParam MultipartFile[] imgs,
			HttpServletRequest request) {
		for(MultipartFile file : imgs){
			if(file.isEmpty()){
				System.out.println("文件未上传...");
			}else {
				try {
					//得到服务器项目发布运行所在地址  
					String uploadPath = request.getSession().getServletContext()
							.getRealPath("/upload");
					//得到上传的文件名
					String fileName = file.getOriginalFilename();
					String fileEx = fileName.substring(fileName.indexOf("."),fileName.length());
					String filePath = System.currentTimeMillis() + fileEx;
					String pathName = uploadPath + File.separator + filePath;
					standard.setPackagePath(filePath);
					//把文件上传至path的路径  
					File localFile = new File(pathName);  
					FileUtils.copyInputStreamToFile(file.getInputStream(),localFile);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		standardService.standardInsert(standard);
		return "redirect:/list.jsp";
	}
	
	@RequestMapping("/check")
	@ResponseBody
	public Map<String, Object> standardCheck(String stdNum) {
		Map<String, Object> map = new LinkedHashMap<>();
		boolean isExist = standardService.standardCheck(stdNum);
		map.put("result", isExist);
		return map;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public Map<String, Object> standardDelete(String params) {
		Map<String, Object> map = new LinkedHashMap<>();
		String[] strs = params.split(",");
		List<Integer> ids = new ArrayList<>();
		for(int i=0;i<strs.length;i++){
			ids.add(Integer.parseInt(strs[i]));
		}
		boolean isDone = standardService.standardDelete(ids);
		map.put("result", isDone);
		return map;
	}
	
	@RequestMapping("/updateInit/{id}")
	public String standardByIdSelect(@PathVariable String id,Model model) {
		Standard standard = standardService.standardByIdSelect(Integer.parseInt(id));
		model.addAttribute("standard", standard);
		return "update";
	}
	
	@RequestMapping("/update")
	public String standardUpdate(Standard standard,@RequestParam MultipartFile[] imgs,HttpServletRequest request) throws Exception{
		for(MultipartFile file : imgs){
			if(file.isEmpty()){
				System.out.println("无文件上传...");
			}else {
				try {
					//得到服务器项目发布运行所在地址  
					String uploadPath = request.getSession().getServletContext()
							.getRealPath("/upload");
					//得到上传的文件名
					String fileName = file.getOriginalFilename();
					String fileEx = fileName.substring(fileName.indexOf("."),fileName.length());
					String filePath = System.currentTimeMillis() + fileEx;
					String pathName = uploadPath + File.separator + filePath;
					standard.setPackagePath(filePath);
					//把文件上传至path的路径  
					File localFile = new File(pathName);  
					FileUtils.copyInputStreamToFile(file.getInputStream(),localFile);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
		standardService.standardUpdate(standard);
		return "redirect:/list.jsp";
	}
	
	/**
     * 文件下载
     * @Description: 
     * @param fileName
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/download")
    public String downloadFile(String fileName,
            HttpServletRequest request, HttpServletResponse response) {
    	System.out.println("fileName-->" + fileName);
    	if (fileName != null) {
    		String realPath = request.getServletContext().getRealPath(
                  "/upload");
    		File file = new File(realPath, fileName);
            if (file.exists()) {
            	try {
					FileInputStream fis = new FileInputStream(file);
					//得到可用字节数
					byte[] b = new byte[fis.available()];
					response.setIntHeader("Content-Length", b.length);
			        response.setHeader("Content-Disposition", "attachment;fileName=" + fileName);  
			        OutputStream os = response.getOutputStream();
			        //初始化缓冲区
			        byte[] bys = new byte[1024];
			        //写文件  
			        int len = 0;
			        while((len=fis.read(bys))!=-1){  
			            os.write(bys, 0, len); 
			        }    
			        fis.close();
			        os.close(); 
            	} catch (Exception e) {
            		e.printStackTrace();
            	}
           }
       }
       return null;
    }
    
    // 导出excel start
 	@RequestMapping(value = "/exportExcel")
 	public ModelAndView exportAction(HttpServletRequest request, HttpServletResponse response) {
 		List<Standard> standardList = standardService.standardExcelSelect();
 		HSSFWorkbook workbook = exportExcel(standardList);
 		if (workbook != null) {
 			try {
 				String fileName = "标准信息" + String.valueOf(System.currentTimeMillis()).substring(4, 13) + ".xls";
 				String fileName11 = URLEncoder.encode(fileName, "UTF-8");
 				String headStr = "attachment; filename=\"" + fileName11 + "\"";
 				response.setContentType("APPLICATION/OCTET-STREAM");
 				response.setHeader("Content-Disposition", headStr);
 				OutputStream out = response.getOutputStream();
 				workbook.write(out);
 				out.flush();
 				out.close();
 			} catch (IOException e) {
 				e.printStackTrace();
 			}

 		}
 		return null;
 	}

 	public HSSFWorkbook exportExcel(List<Standard> list) {
 		HSSFWorkbook workbook = null;
 		// 创建工作簿实例
 		workbook = new HSSFWorkbook();
 		// 创建工作表实例
 		HSSFSheet sheet = workbook.createSheet("信息标准");
 		// 获取样式
 		HSSFCellStyle styleTitle = this.createTitleStyle(workbook);
 		HSSFCellStyle styleContent = this.createContentStyle(workbook);
 		HSSFCellStyle style = this.createStyle(workbook);

 		/**
 		 * 信息标准Excle导出表头
 		 */
 		final String[][] SCAN_LESS_QUERY_EXPORT_TITLE = { { "标准号", "8000" }, { "中文名称", "2000" }, { "版本", "2000" },
 				{ "发布日期", "8000" }, { "实施日期", "8000" }

 		};

 		if (list != null && list.size() > 0) {
 			ExcelUtils.createExcelTitle(sheet, styleTitle, SCAN_LESS_QUERY_EXPORT_TITLE);
 			// 给excel填充数据
 			for (int i = 0; i < list.size(); i++) {
 				Standard standard = list.get(i);
 				HSSFRow rowTemp = sheet.createRow((short) (i + 1));// 建立新行
 				if (null != standard.getStdNum() && !"".equals(standard.getStdNum())) {
 					ExcelUtils.createCell(rowTemp, 0, style, HSSFCell.CELL_TYPE_STRING, standard.getStdNum());
 				} else {
 					ExcelUtils.createCell(rowTemp, 0, style, HSSFCell.CELL_TYPE_STRING, "");
 				}
 				if (null != standard.getZhname() && !"".equals(standard.getZhname())) {
 					ExcelUtils.createCell(rowTemp, 1, styleContent, HSSFCell.CELL_TYPE_STRING, standard.getZhname());
 				} else {
 					ExcelUtils.createCell(rowTemp, 1, styleContent, HSSFCell.CELL_TYPE_STRING, "");
 				}
 				if (null != standard.getVersion() && !"".equals(standard.getVersion())) {
 					ExcelUtils.createCell(rowTemp, 2, styleContent, HSSFCell.CELL_TYPE_STRING, standard.getVersion());
 				} else {
 					ExcelUtils.createCell(rowTemp, 2, styleContent, HSSFCell.CELL_TYPE_STRING, "");
 				}
 				if (standard.getReleaseDate() != null) {
 					ExcelUtils.createCell(rowTemp, 3, styleContent, HSSFCell.CELL_TYPE_STRING, DateUtils.formatDate(standard.getReleaseDate(), "yyyy-MM-dd"));
 				} else {
 					ExcelUtils.createCell(rowTemp, 3, styleContent, HSSFCell.CELL_TYPE_STRING, "");
 				}
 				if (standard.getImplDate() != null) {
 					ExcelUtils.createCell(rowTemp, 4, styleContent, HSSFCell.CELL_TYPE_STRING, DateUtils.formatDate(standard.getImplDate(), "yyyy-MM-dd"));
 				} else {
 					ExcelUtils.createCell(rowTemp, 4, styleContent, HSSFCell.CELL_TYPE_STRING, "");
 				}
 			}
 		}
 		return workbook;
 	}

 	// 设置excel的title样式
 	private HSSFCellStyle createTitleStyle(HSSFWorkbook wb) {
 		HSSFCellStyle style = wb.createCellStyle();

 		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
 		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
 		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
 		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

 		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
 		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
 		HSSFFont font = wb.createFont();
 		font.setFontName("宋体");
 		font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
 		font.setFontHeightInPoints((short) 13);

 		style.setFont(font);// 选择需要用到的字体格式
 		style.setWrapText(true);// 设置自动换行
 		return style;
 	}

 	// 设置excel的内容样式
 	private HSSFCellStyle createContentStyle(HSSFWorkbook wb) {
 		HSSFCellStyle style = wb.createCellStyle();

 		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
 		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
 		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
 		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

 		style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 居中
 		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
 		HSSFFont font = wb.createFont();
 		font.setFontName("宋体");
 		font.setFontHeightInPoints((short) 12);

 		style.setFont(font);// 选择需要用到的字体格式
 		style.setWrapText(true);// 设置自动换行
 		return style;
 	}

 	private HSSFCellStyle createStyle(HSSFWorkbook wb) {
 		HSSFCellStyle style = wb.createCellStyle();

 		style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
 		style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
 		style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
 		style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框

 		style.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 居中
 		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
 		HSSFFont font = wb.createFont();
 		font.setFontName("宋体");
 		font.setFontHeightInPoints((short) 12);

 		style.setFont(font);// 选择需要用到的字体格式
 		style.setWrapText(true);// 设置自动换行
 		return style;
 	}
 	// export excel end
}
