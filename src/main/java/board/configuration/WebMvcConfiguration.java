package board.configuration;

import java.nio.charset.Charset;

import javax.servlet.Filter; //Filter

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {
// 충돌이 일어나서 사용안하는걸로 했음.	
//	@Bean
//	public Filter characterEncodingFilter() {
//		/*
//		스프링부트 2.1 이후로는 기본적으로 UTF-8이 적용되기 때문에
//		따로 설정할 필요는 없지만, 한글이 정상적으로 안나올 경우를 대비해 설정해둔다.
//		CharacterEncodingFilter 클래스는 스프링에서 제공하는 클래스로,
//		웹에서 주고받는 데이터의 헤더값을 UTF-8(지정한 문자)로 인코딩함
//		*/
//		CharacterEncodingFilter charEncodFilter = new CharacterEncodingFilter();
//		charEncodFilter.setEncoding("UTF-8");
//		charEncodFilter.setForceEncoding(true);
//		//HttpServletRequest, HttpServletResponse 모두 강제적으로 지정한 인코딩으로 변경
//		
//		return charEncodFilter;
//	}
	
//	/*
//	@ResponseBody를 이용하여 결과 출력시,
//	그 결과를 UTF-8로 인코딩함
//	*/
//	@Bean
//	public HttpMessageConverter<String> responseBodyConverter(){
//		return new StringHttpMessageConverter(Charset.forName("UTF-8"));
//	}
	
	/*
	@SpringBootApplication(exclude={MultipartAutoConfiguration.class})를
	사용하여 중지된 자동 구성 대신 사용하는 설정
	 */
	@Bean
	public CommonsMultipartResolver multipartResolver() {
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver();
		
		//파일의 인코딩을 UTF-8로 설정
		commonsMultipartResolver.setDefaultEncoding("UTF-8");
		//최대 업로드 파일 크기 설정
		//파일의 크기를 byte 단위로 설정 가능함(5 * 1024 * 1024 = 5mb)
		//컴퓨터는 2진수 계산을 하기 때문에 1000을 2의 10승으로 계산함
		commonsMultipartResolver.setMaxUploadSizePerFile(5*1024*1024);
		
		return commonsMultipartResolver;
	}
}
