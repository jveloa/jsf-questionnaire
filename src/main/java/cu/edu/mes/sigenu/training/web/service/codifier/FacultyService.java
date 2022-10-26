package cu.edu.mes.sigenu.training.web.service.codifier;

import java.util.List;

import cu.edu.mes.subsystem.university.vo.BasicFacultyVO;
import cu.edu.mes.vo.FacultyVO;


public interface FacultyService {
	List<BasicFacultyVO> getFacultiesByUser();
	FacultyVO getFacultyById(String facultyId);
	List<FacultyVO> getAllFaculty();
	List<FacultyVO> getAllActive();
}
