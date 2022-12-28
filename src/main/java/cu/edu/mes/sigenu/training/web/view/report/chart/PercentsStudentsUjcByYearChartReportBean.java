package cu.edu.mes.sigenu.training.web.view.report.chart;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.CareerOptionsDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.chart.PercentsStudentsUjcByYearChartReportService;
import cu.edu.mes.sigenu.training.web.service.report.chart.StudentCareerOptionsChartReportService;
import cu.edu.mes.vo.NationalCareerVO;
import lombok.Data;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.line.LineChartOptions;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.optionconfig.tooltip.Tooltip;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named
@RequestScoped
@Data
public class PercentsStudentsUjcByYearChartReportBean implements Serializable {


    @Inject
    private PercentsStudentsUjcByYearChartReportService percentsStudentsUjcByYearChartReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    @Inject
    private QuestionCareerService questionCareerService;

    private List<NationalCareerVO> careerList;

    private Integer questionnarieId;

    private LineChartModel lineModel;

    private boolean hidden;



    @PostConstruct
    public void init() {

        questionnarieId = 0;
        hidden = false;
        lineModel = new  LineChartModel();





    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public boolean setHidden(){
        createLineModel();
        /*if (this.hidden == false)
            JsfUtils.addMessageFromBundle("casa", FacesMessage.SEVERITY_WARN, "search_not_found");

        */return this.hidden;

    }

    public void createLineModel() {

        lineModel = new LineChartModel();
        ChartData data = new ChartData();
        LineChartDataSet dataSet = new LineChartDataSet();
        List<Object> values = new ArrayList<>();
        Integer valueYear = 0;


        List<String> yearList = percentsStudentsUjcByYearChartReportService.getAllYears(this.questionnarieId);
        if (!(yearList.isEmpty())){
            List<String> labels = new ArrayList<>();

            for (int i = 0; i < yearList.size(); i++) {

                String year = yearList.get(i).substring(0, 4);
                labels.add(year);

                try {
                    valueYear = Integer.valueOf(year);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }


                values.add(percentsStudentsUjcByYearChartReportService.percentsStudentsUjcByYear(valueYear,this.questionnarieId));


            }

            dataSet.setData(values);
            dataSet.setFill(false);
            dataSet.setLabel("% de estudiantes ");
            dataSet.setBorderColor("rgb(75, 192, 192)");
            dataSet.setTension(0.1);
            data.addChartDataSet(dataSet);
            data.setLabels(labels);

            //Options
            LineChartOptions options = new LineChartOptions();
            Title title = new Title();
            title.setDisplay(true);
            title.setText("% de estudiantes que pertenecen a la UJC por año");
            options.setTitle(title);

            lineModel.setOptions(options);
            lineModel.setData(data);

            this.hidden = true;

        }
        else
            this.hidden = false;


    }

    /*public BarChartModel getStackedBarModel() {
        createStackedBarModel();
        return stackedBarModel;
    }*/

    /*public void setStackedBarModel(BarChartModel stackedBarModel) {
        this.stackedBarModel = stackedBarModel;
    }*/

   /*public boolean getHidden() {
        return this.hidden;
    }*/

    public String getCarrerById(String IdCareer){
        if(careerList == null){
            careerList = questionCareerService.getCareersSigenu();
        }
        return careerList.stream().filter(career -> career.getIdNationalCareer().equals(IdCareer)).findFirst().get().getName();
    }




}
