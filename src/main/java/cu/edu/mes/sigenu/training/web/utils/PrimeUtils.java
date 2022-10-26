package cu.edu.mes.sigenu.training.web.utils;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileSystems;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;



public class PrimeUtils {
	
	public static String destination = "C:\\tmp\\";
	public static String OS = System.getProperty("os.name").toLowerCase();
    public static boolean IS_WINDOWS = (OS.indexOf("win") >= 0);
    
    private static String getDestination() {
		return IS_WINDOWS ? destination : "/opt/sigenu/files/students";
	}
    
    /*public static List<UniversityVOExt> getUniversitiesWithFacultiesByUser(){
		List<UniversityVOExt> universityVOExts = new ArrayList<UniversityVOExt>();
		try {
			Collection<BasicFacultyVO> faculties = Client.getUniversitySubsystem()
					.getActiveFacultiesByUser();
			for (BasicFacultyVO lightFacultyVO : faculties) {
				FacultyVO fac = (FacultyVO) Client.getFacultyCatalog().getFaculty(lightFacultyVO.getFacultyId());
				cu.edu.mes.vo.BasicUniversityVO universityOfFaculty = Client.getUniversityCatalog().getBasicUniversity(fac.getIdUniversity());
				boolean foundUniversityGrouped = false;
				for (UniversityVOExt item : universityVOExts) {
					if(item.getBasicUniversityVO().getInitial().equals(universityOfFaculty.getInitial())) {
						BasicFacultyVO basicFacultyVO = new BasicFacultyVO(fac.getName(), fac.getIdFaculty());
						item.getFaculties().add(basicFacultyVO);
						foundUniversityGrouped= true;
						break;
					}
				}
				if(!foundUniversityGrouped) {
					Collection<BasicFacultyVO> facultiesToAdd = new ArrayList<BasicFacultyVO>();
					facultiesToAdd.add(new BasicFacultyVO(fac.getName(), fac.getIdFaculty()));
					UniversityVOExt universityVOExt = new UniversityVOExt(universityOfFaculty, facultiesToAdd);
					universityVOExts.add(universityVOExt);
				}
				
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return universityVOExts;
	}*/
    
    /*public static List<UniversityVOCumExt> getUniversitiesWithTownsByUser(){
		List<UniversityVOCumExt> universityVOExts = new ArrayList<UniversityVOCumExt>();
		try {
			Collection<BasicTownUniversityVO> townUniversities = Client.getUniversitySubsystem().getActiveTownUniversitiesByUser();
			
			for (BasicTownUniversityVO itemVO : townUniversities) {
				TownUniversityVO townUniversityVO = (TownUniversityVO) Client.getTownUniversityCatalog().getTownUniversity(itemVO.getTownUniversityId());
				cu.edu.mes.vo.BasicUniversityVO universityOfFaculty = Client.getUniversityCatalog().getBasicUniversity(townUniversityVO.getIdUniversity());
				boolean foundUniversityGrouped = false;
				for (UniversityVOCumExt item : universityVOExts) {
					if(item.getBasicUniversityVO().getInitial().equals(universityOfFaculty.getInitial())) {
						BasicTownUniversityVO basicVO = new BasicTownUniversityVO(itemVO.getName(), itemVO.getTownUniversityId());
						item.getTownUniversities().add(basicVO);
						foundUniversityGrouped= true;
						break;
					}
				}
				if(!foundUniversityGrouped) {
					Collection<BasicTownUniversityVO> townsToAdd = new ArrayList<BasicTownUniversityVO>();
					townsToAdd.add(new BasicTownUniversityVO(itemVO.getName(), itemVO.getTownUniversityId()));
					UniversityVOCumExt universityVOExt = new UniversityVOCumExt(universityOfFaculty, townsToAdd);
					universityVOExts.add(universityVOExt);
				}
				
			}
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return universityVOExts;
	}*/

	/**
	 * Metodo para borrar el segundo tab activo de Primefaces
	 * @param tab
	 */
	public static void deleteSecondTab(TabView tabView){
		if(tabView.getChildren().size() == 2){
			tabView.getChildren().remove(1);
			tabView.setActiveIndex(0);
		}
	}
	
	public static int addSecondTab(TabView tabView, Tab tab){
		tabView.getChildren().add(tab);  
		return tabView.getChildren().size() - 1;
	}
	
	/**
	 * Permite validar un email.
	 * @param email
	 * @return
	 */
	public static boolean isValidEmail(String email){
		boolean isValid = false;
		Pattern p = Pattern
				.compile("[a-zA-Z0-9][a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)*@[a-zA-Z0-9_]+(\\.[a-zA-Z]+)+");
		Matcher m = p.matcher(email);
		isValid = m.matches();
		return isValid; 
	}
	
	
	public static void copyFile(String fileName, InputStream in, String identification) {
		try {
			BufferedImage imBuff = ImageIO.read(in);
			BufferedImage imageResized = resizeImage(imBuff, 200, 200);
			
			String directoryPath = getDestination() + FileSystems.getDefault().getSeparator() + identification;
			System.out.println("directory: " + directoryPath);
			File directory = new File(directoryPath);
		    if (!directory.exists()){
		        directory.mkdirs();
		    } else {
		    	String[]entries = directory.list();
		    	for(String s: entries){
		    	    File currentFile = new File(directory.getPath(),s);
		    	    currentFile.delete();
		    	}
		    }
			OutputStream out = new FileOutputStream(new File(directoryPath + FileSystems.getDefault().getSeparator() + identification + ".jpg"));
			byte[] bytes = toByteArray(imageResized, "jpg");
	
			out.write(bytes);

			in.close();
			out.flush();
			out.close();
	
			System.out.println("New file created!");
			} catch (IOException e) {
			System.out.println(e.getMessage());
			}
		}
	
	public static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
	    BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
	    Graphics2D graphics2D = resizedImage.createGraphics();
	    graphics2D.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    graphics2D.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
	    graphics2D.dispose();
	    return resizedImage;
	}
	  
	public static byte[] toByteArray(BufferedImage bi, String format)
	        throws IOException {

	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(bi, format, baos);
	        byte[] bytes = baos.toByteArray();
	        return bytes;

	    }
	
	public static String getStudentPhotoPathByIdentification(String identification) {
		return getDestination() + File.separator + identification + File.separator + identification + ".jpg";
	}
	
}
