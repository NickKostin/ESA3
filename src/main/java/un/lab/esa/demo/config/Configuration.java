package un.lab.esa.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.xslt.XsltView;
import org.springframework.web.servlet.view.xslt.XsltViewResolver;

@org.springframework.context.annotation.Configuration
@EnableWebMvc
public class Configuration extends WebMvcConfigurerAdapter {

    @Bean
    public ViewResolver getXSLTViewResolver(){

        XsltViewResolver xsltResolver = new XsltViewResolver();
        xsltResolver.setOrder(1);
        xsltResolver.setViewClass(XsltView.class);
        xsltResolver.setViewNames("movie-list", "director-list");
        xsltResolver.setPrefix("classpath:/xsl/");
        xsltResolver.setSuffix(".xsl");

        return xsltResolver;
    }
}