package cu.edu.mes.sigenu.training.web.service.report;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsNotesDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.StudentsWithNotesDto;
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
public class EntryDataByCourseReportServiceImpl implements EntryDataByCourseReportService {

    @Inject
    private RestService restService;


    @Override
    public List<StudentsWithNotesDto> getEntryDataByCourse(Integer year, Integer id) {

        List<StudentsWithNotesDto> items = new ArrayList<StudentsWithNotesDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<StudentsWithNotesDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/reportCareerChief/entryDataByCourse/{year}/{id}");
            String uri = template.expand(year, id).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response, StudentsWithNotesDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}