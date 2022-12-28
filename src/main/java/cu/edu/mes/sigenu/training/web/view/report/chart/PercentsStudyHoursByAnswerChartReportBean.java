package cu.edu.mes.sigenu.training.web.view.report.chart;

import cu.edu.mes.sigenu.training.web.dto.questionnaire.QuestionnaireDto;
import cu.edu.mes.sigenu.training.web.dto.questionnaire.report.PercentsStudyHoursByAnswerDto;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionCareerService;
import cu.edu.mes.sigenu.training.web.service.questionnaire.QuestionnaireService;

import cu.edu.mes.sigenu.training.web.service.report.PercentsStudyHoursByAnswerReportService;
import cu.edu.mes.sigenu.training.web.service.report.chart.PercentsStudyHoursByAnswerChartReportService;
import cu.edu.mes.sigenu.training.web.utils.JsfUtils;
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
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Named
@RequestScoped
@Data
public class PercentsStudyHoursByAnswerChartReportBean implements Serializable {

    @Inject
    private PercentsStudyHoursByAnswerReportService percentsStudyHoursByAnswerReportService;

    @Inject
    private PercentsStudyHoursByAnswerChartReportService percentsStudyHoursByAnswerChartReportService;

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


        List<String> yearList = percentsStudyHoursByAnswerChartReportService.getAllYears(this.questionnarieId);
        if (!(yearList.isEmpty())){

            stackedBarModel = new BarChartModel();
            ChartData data = new ChartData();

            BarChartDataSet barDataSet = new BarChartDataSet();
            barDataSet.setLabel("20 o más");
            barDataSet.setBackgroundColor("rgb(255, 99, 132)");
            List<Number> dataVal = new ArrayList<>();

            BarChartDataSet barDataSet2 = new BarChartDataSet();
            barDataSet2.setLabel("Entre 15 y 20");
            barDataSet2.setBackgroundColor("rgb(54, 162, 235)");
            List<Number> dataVal2 = new ArrayList<>();

            BarChartDataSet barDataSet3 = new BarChartDataSet();
            barDataSet3.setLabel("Entre 10 y 15");
            barDataSet3.setBackgroundColor("rgb(75, 192, 192)");
            List<Number> dataVal3 = new ArrayList<>();

            BarChartDataSet barDataSet4 = new BarChartDataSet();
            barDataSet4.setLabel("Entre 5 y 10");
            barDataSet4.setBackgroundColor("rgb(100, 102, 255)");
            List<Number> dataVal4 = new ArrayList<>();

            BarChartDataSet barDataSet5 = new BarChartDataSet();
            barDataSet5.setLabel("Entre 2 y 5");
            barDataSet5.setBackgroundColor("rgb(201, 203, 207)");
            List<Number> dataVal5 = new ArrayList<>();

            BarChartDataSet barDataSet6 = new BarChartDataSet();
            barDataSet6.setLabel("Menos de 2");
            barDataSet6.setBackgroundColor("rgb(255, 205, 86)");
            List<Number> dataVal6 = new ArrayList<>();

            List<String> labels = new ArrayList<>();

            for (int i = 0; i < yearList.size(); i++) {

                String year = yearList.get(i).substring(0, 4);
                labels.add(year);

                try {
                    valueYear = Integer.valueOf(year);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                List<PercentsStudyHoursByAnswerDto> list = percentsStudyHoursByAnswerReportService.getPercentsStudyHoursByAnswer(valueYear, this.questionnarieId);
                for (int j = 0; j < list.size(); j++) {

                    if (list.get(j).getQuestion().contains("20 o más"))
                        dataVal.add(list.get(j).getValue());

                    else if (list.get(j).getQuestion().contains("Entre 15 y 20"))
                        dataVal2.add(list.get(j).getValue());

                    else if (list.get(j).getQuestion().contains("Entre 10 y 15"))
                        dataVal3.add(list.get(j).getValue());

                    else if (list.get(j).getQuestion().contains("Entre 5 y 10"))
                        dataVal4.add(list.get(j).getValue());

                    else if (list.get(j).getQuestion().contains("Entre 2 y 5"))
                        dataVal5.add(list.get(j).getValue());

                    else if (list.get(j).getQuestion().contains("Menos de 2"))
                        dataVal6.add(list.get(j).getValue());
                }


            }

            barDataSet.setData(dataVal);
            barDataSet2.setData(dataVal2);
            barDataSet3.setData(dataVal3);
            barDataSet4.setData(dataVal4);
            barDataSet5.setData(dataVal5);
            barDataSet6.setData(dataVal6);

            data.addChartDataSet(barDataSet);
            data.addChartDataSet(barDataSet2);
            data.addChartDataSet(barDataSet3);
            data.addChartDataSet(barDataSet4);
            data.addChartDataSet(barDataSet5);
            data.addChartDataSet(barDataSet6);


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
            title.setText("Rangos de horas dedicadas al estudio");
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
