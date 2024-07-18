package checkboot.springbootAc;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionEvaluationReport;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringbootAcApplication {
	
	// 어플리케이션의 초기화가 완료된 이후에 실행
	@Bean
	ApplicationRunner runner(ConditionEvaluationReport report) {
		return args -> {
			// getConditionAndOutcomesBySource() : 모든 Condition을 체크한 기록들이 Map형태로 return
			System.out.println(report.getConditionAndOutcomesBySource().entrySet().stream() // 필터링을 하기 위해 스트림화
					.filter(conAndOut -> conAndOut.getValue().isFullMatch()) // isFullMatch() : 모든 조건에 통과한 빈들의 정보를 return
					.filter(conAndOut -> conAndOut.getKey().indexOf("Sql") < 0)
					.map(matchCo -> {
						// key : 등록된 클래스와 빈 메소드 
						System.out.println(matchCo.getKey());
						
						// value : 어떤 Condition을 통과했는지도 확인 가능
						matchCo.getValue().forEach(co -> {
							System.out.println(co.getOutcome());
						});
						
						System.out.println( " count :  ");
						
						return matchCo;
					}).count());
		};
	}
	
	
	public static void main(String[] args) {
		/*
		 * 아무런 Dependency도 선택하지 않고 프로젝트를 만들었기 때문에, main메소드를 실행해도 Web(Tomcat)은 띄어지지 않는다.
		 * 따라서, SpringContainer만 띄었다가 standalone 한 작업들을 하고 바로 종료됨.
		 */
		SpringApplication.run(SpringbootAcApplication.class, args);
	}

}
