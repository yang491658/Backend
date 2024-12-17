package project.blobus.Backend;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import project.blobus.Backend.youth.job.service.JobServiceImpl;

@SpringBootTest
class BackendApplicationTests {
	@Autowired
	JobServiceImpl jobService;

	@Test
	void contextLoads() {
		String text = "2024-08-12~2024-08-23\n" +
				"2024. 8. 12.(월) ~ 8. 23.(금) 16:00";
//		String text = "\n" +
//				"2024-06-03 00:00 ~ 2024-12-31 00:00";
		String[] results = jobService.extractDates(text);
		for(String r : results) {
			System.out.println(r);
		}
	}

}
