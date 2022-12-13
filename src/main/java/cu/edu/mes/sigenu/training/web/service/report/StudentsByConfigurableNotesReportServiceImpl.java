package cu.edu.mes.sigenu.training.web.service.report;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsNotesDto;
import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;
import cu.edu.mes.sigenu.training.web.utils.ApiRestMapper;
import cu.edu.mes.sigenu.training.web.utils.RestService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriTemplate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class StudentsByConfigurableNotesReportServiceImpl implements StudentsByConfigurableNotesReportService {

    @Inject
    private RestService restService;


    @Override
    public List<StudentsNotesDto> getStudentsByConfigurableNotes(Integer year, float academicIndex
            , float noteSpanish, float noteMat, float noteHistory,Integer questionnarieId) {

        List<StudentsNotesDto> items = new ArrayList<StudentsNotesDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<StudentsNotesDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/reportCareerChief/studentsByConfigurableNotes/{year}/{academicIndex}/{noteSpanish}/{noteMat}/{noteHistory}/{questionnarieId}");
            String uri = template.expand(year,academicIndex,noteSpanish,noteMat,noteHistory,questionnarieId).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response, StudentsNotesDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
