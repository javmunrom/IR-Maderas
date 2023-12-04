package org.springframework.samples.maderas;

import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

public class MaderasInitializer extends SpringBootServletInitializer{
    
    @Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MaderasApplication.class);
	}
    
}
