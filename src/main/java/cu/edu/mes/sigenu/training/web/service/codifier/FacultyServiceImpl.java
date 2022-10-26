package cu.edu.mes.sigenu.training.web.service.codifier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;
import cu.edu.mes.sigenu.training.web.utils.ApiRestMapper;
import cu.edu.mes.sigenu.training.web.utils.RestService;
import cu.edu.mes.subsystem.university.vo.BasicFacultyVO;
import cu.edu.mes.vo.FacultyVO;

@Named
@RequestScoped
public class FacultyServiceImpl implements FacultyService{
	
	@Inject
	private RestService restService;

	@Override
	public List<BasicFacultyVO> getFacultiesByUser() {
		List<BasicFacultyVO> items = new ArrayList<BasicFacultyVO>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<BasicFacultyVO> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/codifier/v1/faculty/users/me", params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
		    items = apiRestMapper.mapList(response, BasicFacultyVO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	@Override
	public List<FacultyVO> getAllFaculty() {
		List<FacultyVO> items = new ArrayList<FacultyVO>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<FacultyVO> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/codifier/v1/faculty/all-faculty", params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
		    items = apiRestMapper.mapList(response, FacultyVO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return items;
	}
	
	@Override
	public List<FacultyVO> getAllActive() {
		List<FacultyVO> items = new ArrayList<FacultyVO>();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<FacultyVO> apiRestMapper = new ApiRestMapper<>();
		    String response = (String)restService.GET("/api/codifier/v1/faculty", params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
		    items = apiRestMapper.mapList(response, FacultyVO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return items;
	}

	@Override
	public FacultyVO getFacultyById(String facultyId) {
		FacultyVO item = new FacultyVO();
	    try {
	    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		    ApiRestMapper<FacultyVO> apiRestMapper = new ApiRestMapper<>();
		   
			UriTemplate template = new UriTemplate("/api/codifier/v1/faculty/{id}");
			String uri = template.expand(facultyId).toString();
		    
		    String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
		    item = apiRestMapper.mapOne(response, FacultyVO.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return item;
		
	}
	
}
