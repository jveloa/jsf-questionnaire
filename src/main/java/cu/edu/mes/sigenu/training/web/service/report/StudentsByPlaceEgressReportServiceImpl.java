package cu.edu.mes.sigenu.training.web.service.report;


import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.EntrySourceAuxDto;
import cu.edu.mes.sigenu.training.web.security.CurrentUserUtils;
import cu.edu.mes.sigenu.training.web.utils.ApiRestMapper;
import cu.edu.mes.sigenu.training.web.utils.RestService;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriTemplate;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class StudentsByPlaceEgressReportServiceImpl implements StudentsByPlaceEgressReportService {

    @Inject
    private RestService restService;


    @Override
    public List<String> getStudentsByPlaceEgress (Integer year, String idPlaceEgress, Integer id){


        List<String> items = new ArrayList<String>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<String> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/reportCareerChief/studentsByPlaceEgress/{year}/{idPlaceEgress}/{id}");

            String uri = template.expand(year,idPlaceEgress,id).toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response, String.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<EntrySourceAuxDto> getAllPlaceEgress() {
        List<EntrySourceAuxDto> items = new ArrayList<EntrySourceAuxDto>();
        try {
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            ApiRestMapper<EntrySourceAuxDto> apiRestMapper = new ApiRestMapper<>();

            UriTemplate template = new UriTemplate("/api/v1/training/reportCareerChief/allPlaceEgress/");
            String uri = template.expand().toString();

            String response = (String)restService.GET(uri, params, String.class, CurrentUserUtils.getTokenBearer()).getBody();
            items = apiRestMapper.mapList(response, EntrySourceAuxDto.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}

