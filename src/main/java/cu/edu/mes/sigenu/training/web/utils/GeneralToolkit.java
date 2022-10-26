package cu.edu.mes.sigenu.training.web.utils;

public class GeneralToolkit {

	private static String ID_STUDY_CONTINUITY = "02";
	private static String ID_DISTANCE_LEARNING = "05";
	
	public static boolean studyContinuityBehavior(String idCourseType){
		
		return (idCourseType.equals(ID_STUDY_CONTINUITY) 
				|| idCourseType.equals(ID_DISTANCE_LEARNING));
	}
}
