package ltts.ems.com.model;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class MVCConfigure implements WebMvcConfigurer{

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		Path employeeUploadDir=Paths.get("./Employee-Photos");
		String employeeUploadPath=employeeUploadDir.toFile().getAbsolutePath();
		registry.addResourceHandler("/Employee-Photos/**").addResourceLocations("file:/"+employeeUploadPath+"/");

	}

}
