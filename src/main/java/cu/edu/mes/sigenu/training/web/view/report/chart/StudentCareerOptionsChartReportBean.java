package cu.edu.mes.sigenu.training.web.view.report.chart;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.CareerOptionsDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.PercentsStudyHoursByAnswerDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;
import cu.edu.mes.sigenu.training.web.service.report.PercentsStudyHoursByAnswerReportService;
import cu.edu.mes.sigenu.training.web.service.report.chart.PercentsStudyHoursByAnswerChartReportService;
import cu.edu.mes.sigenu.training.web.service.report.chart.StudentCareerOptionsChartReportService;
import cu.edu.mes.vo.NationalCareerVO;
import lombok.Data;
import org.primefaces.model.charts.ChartData;
import org.primefaces.model.charts.axes.cartesian.CartesianScales;
import org.primefaces.model.charts.axes.cartesian.linear.CartesianLinearAxes;
import org.primefaces.model.charts.bar.BarChartDataSet;
import org.primefaces.model.charts.bar.BarChartModel;
import org.primefaces.model.charts.bar.BarChartOptions;
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
public class StudentCareerOptionsChartReportBean implements Serializable {


    @Inject
    private StudentCareerOptionsChartReportService studentCareerOptionsChartReportService;

    @Inject
    private QuestionnaireService questionnaireService;

    @Inject
    private QuestionCareerService questionCareerService;

    private List<NationalCareerVO> careerList;

    private Integer questionnarieId;

    private BarChartModel stackedBarModel;

    private boolean hidden;



    @PostConstruct
    public void init() {

        questionnarieId = 0;
        hidden = false;
        stackedBarModel = new  BarChartModel();





    }

    public List<QuestionnaireDto> getAllQuestionnaire() {
        return questionnaireService.getAllQuestionnaire();
    }

    public boolean setHidden(){
        createStackedBarModel();
        /*if (this.hidden == false)
            JsfUtils.addMessageFromBundle("casa", FacesMessage.SEVERITY_WARN, "search_not_found");

        */return this.hidden;

    }

    public void createStackedBarModel() {



        Integer valueYear = 0;


        List<String> yearList = studentCareerOptionsChartReportService.getAllYears(this.questionnarieId);
        if (!(yearList.isEmpty())){

            stackedBarModel = new BarChartModel();
            ChartData data = new ChartData();

            BarChartDataSet barDataSetOne = new BarChartDataSet();
            barDataSetOne.setLabel("Opción 1");
            barDataSetOne.setBackgroundColor("rgb(255, 99, 132)");
            List<Number> dataVal = new ArrayList<>();

            BarChartDataSet barDataSetTwo = new BarChartDataSet();
            barDataSetTwo.setLabel("Opción 2");
            barDataSetTwo.setBackgroundColor("rgb(54, 162, 235)");
            List<Number> dataVal2 = new ArrayList<>();

            BarChartDataSet barDataSetThree = new BarChartDataSet();
            barDataSetThree.setLabel("Opción 3");
            barDataSetThree.setBackgroundColor("rgb(255, 205, 86)");
            List<Number> dataVal3 = new ArrayList<>();

            BarChartDataSet barDataSetPlusThree= new BarChartDataSet();
            barDataSetPlusThree.setLabel("Más de 3");
            barDataSetPlusThree.setBackgroundColor("rgb(201, 203, 207)");
            List<Number> dataVal4 = new ArrayList<>();

            List<String> labels = new ArrayList<>();

            for (int i = 0; i < yearList.size(); i++) {

                String year = yearList.get(i).substring(0, 4);
                labels.add(year);

                try {
                    valueYear = Integer.valueOf(year);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                CareerOptionsDto list = studentCareerOptionsChartReportService.studentCareerOptions(valueYear, this.questionnarieId);

                dataVal.add(list.getQuantityOptionOne());
                dataVal2.add(list.getQuantityOptionTwo());
                dataVal3.add(list.getQuantityOptionThree());
                dataVal4.add(list.getQuantityOptionPlusThree());


            }

            barDataSetOne.setData(dataVal);
            barDataSetTwo.setData(dataVal2);
            barDataSetThree.setData(dataVal3);
            barDataSetPlusThree.setData(dataVal4);


            data.addChartDataSet(barDataSetOne);
            data.addChartDataSet(barDataSetTwo);
            data.addChartDataSet(barDataSetThree);
            data.addChartDataSet(barDataSetPlusThree);



            data.setLabels(labels);
            stackedBarModel.setData(data);

            //Options
            BarChartOptions options = new BarChartOptions();
            CartesianScales cScales = new CartesianScales();
            CartesianLinearAxes linearAxes = new CartesianLinearAxes();
            linearAxes.setStacked(true);
            linearAxes.setOffset(true);
            cScales.addXAxesData(linearAxes);
            cScales.addYAxesData(linearAxes);
            options.setScales(cScales);


            Title title = new Title();
            title.setDisplay(true);
            title.setText("Opción de selección de carrera");
            options.setTitle(title);

            Tooltip tooltip = new Tooltip();
            tooltip.setMode("index");
            tooltip.setIntersect(false);
            options.setTooltip(tooltip);

            stackedBarModel.setOptions(options);

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

    public boolean getHidden() {
        return this.hidden;
    }

    public String getCarrerById(String IdCareer){
        if(careerList == null){
            careerList = questionCareerService.getCareersSigenu();
        }
        return careerList.stream().filter(career -> career.getIdNationalCareer().equals(IdCareer)).findFirst().get().getName();
    }




}
