package cu.edu.mes.sigenu.training.web.config;

import javax.servlet.ServletContext;

import org.ocpsoft.rewrite.annotation.RewriteConfiguration;
import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.HttpConfigurationProvider;
import org.ocpsoft.rewrite.servlet.config.rule.Join;



@RewriteConfiguration
public class UrlRewriteConfigurationProvider extends HttpConfigurationProvider{

	@Override
	public Configuration getConfiguration(ServletContext context) {
		return ConfigurationBuilder.begin()
				//Generals
				.addRule(Join.path("/workspace").to("/pages/content.xhtml"))
				//login page
				.addRule(Join.path("/signin").to("/pages/security/login.xhtml"))
				//welcome page
				.addRule(Join.path("/welcome").to("/pages/welcome/welcome.xhtml"))
				
				//change-password
				.addRule(Join.path("/change-password").to("/pages/security/changePassword.xhtml"))
				//error-page
				.addRule(Join.path("/error-fatal").to("/pages/error/error.xhtml"))
				//view-expired-page
				.addRule(Join.path("/error-view-expired").to("/pages/error/viewExpired.xhtml"))
				//not-found-page
				.addRule(Join.path("/error-not-found").to("/pages/error/notFound.xhtml"))
				//access denied page
				.addRule(Join.path("/error-access-denied").to("/pages/error/accessDenied.xhtml"))
				
				//Codifiers
				//university
				.addRule(Join.path("/codifiers/university").to("/pages/secretary/codifier/academics/centerCareer/university/universityCodifier.xhtml"))
				//faculty
				.addRule(Join.path("/codifiers/faculty").to("/pages/secretary/codifier/academics/centerCareer/faculty/facultyCodifier.xhtml"))
				//town-university
				.addRule(Join.path("/codifiers/town-university").to("/pages/secretary/codifier/academics/centerCareer/townUniversity/townUniversityCodifier.xhtml"))
				//national-career
				.addRule(Join.path("/codifiers/national-career").to("/pages/secretary/codifier/academics/centerCareer/nationalCareer/nationalCareerCodifier.xhtml"))
				//career
				.addRule(Join.path("/codifiers/career").to("/pages/secretary/codifier/academics/centerCareer/career/careerCodifier.xhtml"))
				//academic-year
				.addRule(Join.path("/codifiers/academic-year").to("/pages/secretary/codifier/academics/centerCareer/academicYear/academicYearCodifier.xhtml"))
				//brigade
				.addRule(Join.path("/codifiers/brigade").to("/pages/secretary/codifier/academics/centerCareer/group/listGroups.xhtml"))
				
				//assign-department-to-subject
				.addRule(Join.path("/assign-department-to-subject").to("/pages/secretary/subjects/assignDepartmentToSubject/assignDepartmentToSubject.xhtml"))
				
				//Inscription
				//matriculate student
				.addRule(Join.path("/inscription/register-student").to("/pages/secretary/student/inscription/matriculateContent.xhtml"))
				//search student matriculated
				.addRule(Join.path("/inscription/search-student").to("/pages/secretary/student/inscription/search/search.xhtml"))
				//matriculate cloussure proccess
				.addRule(Join.path("/inscription/clossure-process").to("/pages/secretary/student/inscription/clossureProcess/clossureProcess.xhtml"))
				//students-until-today
				.addRule(Join.path("/inscription/students-until-today").to("/pages/secretary/student/inscription/reports/CES/matriculateStudentUntilToday.xhtml"))
				//students-date-by-range
				.addRule(Join.path("/inscription/students-date-by-range").to("/pages/secretary/student/inscription/reports/CES/matriculateStudentDateRage.xhtml"))
				//students-date-by-range
				.addRule(Join.path("/inscription/students-by-day").to("/pages/secretary/student/inscription/reports/CES/matriculateStudentDay.xhtml"))
				
				//Students/
				//search student
				.addRule(Join.path("/students-search").to("/pages/secretary/student/search/searchStudent.xhtml"))
				.addRule(Join.path("/students-search-safe-mode").to("/pages/secretary/student/search/searchStudentSafeMode.xhtml"))
				.addRule(Join.path("/students-search-safe-mode?ci={identification}").to("/pages/secretary/student/search/searchStudentSafeMode.xhtml"))
				.addRule(Join.path("/students-search?ci={identification}").to("/pages/secretary/student/search/searchStudent.xhtml"))
				//list Student
				.addRule(Join.path("/students-list").to("/pages/secretary/student/list/listStudent.xhtml"))
				//distribute Student
				.addRule(Join.path("/students-distribute").to("/pages/secretary/student/distribute/studentsDistribution.xhtml"))
				//promoteStudent
				.addRule(Join.path("/students-promote").to("/pages/secretary/student/promotion/promoteStudent.xhtml"))
				//general-evaluations
				.addRule(Join.path("/students-general-evaluations").to("/pages/secretary/evaluations/generalEvaluation/generalEvaluation.xhtml"))
				//list Student-safe-mode
				.addRule(Join.path("/students-list-safe-mode").to("/pages/secretary/evaluations/list/listStudent.xhtml"))
				
				
				//Study Program
				//search student
				.addRule(Join.path("/programs-list").to("/pages/secretary/studyProgram/list/studyProgramList.xhtml"))
				//list Student
				.addRule(Join.path("/programs-subjects-optative-elective").to("/pages/secretary/subjects/optativeElectiveSubject/optativeElectiveSubject.xhtml"))
				
				.addRule(Join.path("/programs-subjects").to("/pages/secretary/codifier/academics/studyPlan/subject/subjectCodifier.xhtml"))
				
				.addRule(Join.path("/programs-subjects-adjustment").to("/pages/secretary/codifier/academics/studyPlan/adjustmentSubject/adjustmentSubject.xhtml"))
				
				.addRule(Join.path("/programs-subjects-facultatives").to("/pages/secretary/codifier/academics/studyPlan/facultativeSubject/facultativeSubjects.xhtml"))
				
				.addRule(Join.path("/programs-discipline").to("/pages/secretary/codifier/academics/studyPlan/discipline/discipline.xhtml"))
				
				.addRule(Join.path("/programs-general-discipline").to("/pages/secretary/codifier/academics/studyPlan/disciplineName/disciplineName.xhtml"))
				
							
				//reports
				.addRule(Join.path("/students-assistants-report").to("/pages/secretary/student/reports/assistantsReport.xhtml"))
				
				.addRule(Join.path("/students-cadets-report").to("/pages/secretary/student/reports/cadetsReport.xhtml"))
				
				.addRule(Join.path("/students-year-report").to("/pages/secretary/student/reports/dragStudentReportsContent.xhtml"))
				
				.addRule(Join.path("/students-drag-report").to("/pages/secretary/student/reports/dragStudentReports.xhtml"))
				
				.addRule(Join.path("/students-last-examination-report").to("/pages/secretary/student/reports/studentWithWorldExaminationReport.xhtml"))
				
				.addRule(Join.path("/students-evaluation-report").to("/pages/secretary/student/reports/notesStudentForSummons.xhtml"))
				
				.addRule(Join.path("/students-drop-report").to("/pages/secretary/student/docentControl/dropsReport.xhtml"))
				
				//audit
                .addRule(Join.path("/audit-without-year").to("/pages/secretary/audit/studentsWithoutYear.xhtml"))
				
				.addRule(Join.path("/audit-without-study-program").to("/pages/secretary/audit/studentsWithoutStudyPlan.xhtml"))
				
				.addRule(Join.path("/audit-year-without-study-program").to("/pages/secretary/audit/yearsWithoutStudyPlan.xhtml"))
				
				.addRule(Join.path("/audit-foreigner-without-native-country").to("/pages/secretary/audit/foreignersWithoutNativeCountry.xhtml"))
				
				.addRule(Join.path("/audit-students-without-town").to("/pages/secretary/audit/studentsWithoutTown.xhtml"))
				
				.addRule(Join.path("/audit-students-without-source-entrance").to("/pages/secretary/audit/studentsWithoutSourceEntrance.xhtml"))
				
				.addRule(Join.path("/audit-students-whitout-not-registerd-subjet").to("/pages/secretary/audit/studentsWithoutNoRegisteredSubjectsOfCrawl.xhtml"))
				
				.addRule(Join.path("/audit-students-foreigners-with-errors").to("/pages/secretary/audit/studentsForeignersNotWellDefined.xhtml"))
				
				.addRule(Join.path("/audit-students-evaluate-twice").to("/pages/secretary/audit/studentsWithSubjectsEvaluatedTwice.xhtml"))

				
				
				
				;
				
				
				
				
				
				


		//ej pasando parametros            
		//.addRule(Join.path("/shop/{category}").to("/faces/viewCategory.xhtml"));
	}

	@Override
	public int priority() {
		return 0;
	}

}
