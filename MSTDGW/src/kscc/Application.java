package kscc;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.Charset;

import javax.net.ssl.SSLContext;
import javax.servlet.Filter;

import kscc.com.gateway.interceptor.GwInterceptor;

import org.apache.http.Consts;
import org.apache.http.Header;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.DnsResolver;
import org.apache.http.conn.HttpConnectionFactory;
import org.apache.http.conn.ManagedHttpClientConnection;
import org.apache.http.conn.routing.HttpRoute;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.impl.DefaultHttpResponseFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.DefaultHttpResponseParser;
import org.apache.http.impl.conn.DefaultHttpResponseParserFactory;
import org.apache.http.impl.conn.ManagedHttpClientConnectionFactory;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.SystemDefaultDnsResolver;
import org.apache.http.impl.io.DefaultHttpRequestWriterFactory;
import org.apache.http.io.HttpMessageParser;
import org.apache.http.io.HttpMessageParserFactory;
import org.apache.http.io.HttpMessageWriterFactory;
import org.apache.http.io.SessionInputBuffer;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicLineParser;
import org.apache.http.message.LineParser;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.CharArrayBuffer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@PropertySource("config.properties")
public class Application {
	
	@Value("${common.httpClient.maxSize}")
	String httpMaxSize;

	@Value("${common.httpClient.minSize}")
	String httpMinSize;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SpringApplication.run(Application.class, args);
	}
	
	@Bean
	public Docket api() {
//		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().pathMapping("/")
//				.directModelSubstitute(LocalDate.class, String.class).genericModelSubstitutes(ResponseEntity.class)
//				.alternateTypeRules(newRule(typeResolver.resolve(DeferredResult.class, typeResolver.resolve(ResponseEntity.class, WildcardType.class)), typeResolver.resolve(WildcardType.class)))
//				.useDefaultResponseMessages(false)
//				.globalResponseMessage(RequestMethod.GET, newArrayList(new ResponseMessageBuilder().code(500).message("500 message").responseModel(new ModelRef("Error")).build()))
//				.securitySchemes(newArrayList(apiKey())).securityContexts(newArrayList(securityContext()));
		
		ApiInfo apiInfo = new ApiInfo("Kscc Rest Api", "This documents describes about sample API", "usr", "j.j@daum.net", "Mobile Team", "API License URL","" );
		return new Docket(DocumentationType.SWAGGER_2).select().apis(RequestHandlerSelectors.any()).paths(PathSelectors.any()).build().apiInfo(apiInfo);
	}

 
	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration("validatorUrl");
	}
	
	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter(){
		return new WebMvcConfigurerAdapter(){
			@Override
			public void addInterceptors(InterceptorRegistry registry){
				registry.addInterceptor(new GwInterceptor()).addPathPatterns("/api/gw/**");
			}
		};
	}
	
	  @Bean
	  public HttpClient httpClient() {
		  SSLContext sslcontext = SSLContexts.createSystemDefault();
		  HttpMessageWriterFactory<HttpRequest> requestWriterFactory = new DefaultHttpRequestWriterFactory();
		  HttpMessageParserFactory<HttpResponse> responseParserFactory = new DefaultHttpResponseParserFactory() {

	            @Override
	            public HttpMessageParser<HttpResponse> create(
	                SessionInputBuffer buffer, MessageConstraints constraints) {
	                LineParser lineParser = new BasicLineParser() {

	                    @Override
	                    public Header parseHeader(final CharArrayBuffer buffer) {
	                        try {
	                            return super.parseHeader(buffer);
	                        } catch (ParseException ex) {
	                            return new BasicHeader(buffer.toString(), null);
	                        }
	                    }

	                };
	                return new DefaultHttpResponseParser(
	                    buffer, lineParser, DefaultHttpResponseFactory.INSTANCE, constraints) {

	                    @Override
	                    protected boolean reject(final CharArrayBuffer line, int count) {
	                        // try to ignore all garbage preceding a status line infinitely
	                        return false;
	                    }

	                };
	            }

	        };
	        
			DnsResolver dnsResolver = new SystemDefaultDnsResolver() {
				@Override
				public InetAddress[] resolve(final String host) throws UnknownHostException {
					if (host.equalsIgnoreCase("myhost")) {
						return new InetAddress[] { InetAddress.getByAddress(new byte[] {127, 0, 0, 1}) };
					} else {
						return super.resolve(host);
					}
				}
			};
		  HttpConnectionFactory<HttpRoute, ManagedHttpClientConnection> connFactory = new ManagedHttpClientConnectionFactory(requestWriterFactory, responseParserFactory);
		  Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create().register("http", PlainConnectionSocketFactory.INSTANCE)/*.register("https", new SSLConnectionSocketFactory(sslcontext))*/.build();
		  PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry, connFactory, dnsResolver);
		  ConnectionConfig connectionConfig = ConnectionConfig.custom().setCharset(Consts.UTF_8).build();
		  connManager.setDefaultConnectionConfig(connectionConfig);
		  connManager.setMaxTotal(100);
		  RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000).setConnectTimeout(3000).setConnectionRequestTimeout(3000).build();
		  CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connManager).setDefaultRequestConfig(requestConfig).build();
	    return httpClient;
	  }
	

	    @Bean
	    public HttpMessageConverter<String> responseBodyConverter() {
	        return new StringHttpMessageConverter(Charset.forName("UTF-8"));
	    }
	 
	    @Bean
	    public Filter characterEncodingFilter() {
	        CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
	        characterEncodingFilter.setEncoding("UTF-8");
	        characterEncodingFilter.setForceEncoding(true);
	        return characterEncodingFilter;
	    }
	  
	  
}
