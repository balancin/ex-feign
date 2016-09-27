package demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
public class HelloServerApplication {
	@Autowired
	DiscoveryClient client;

	@RequestMapping("/")
	public String hello() {
		ServiceInstance localInstance = client.getLocalServiceInstance();
		return "Hello World: "+ localInstance.getServiceId()+":"+localInstance.getHost()+":"+localInstance.getPort();
	}

	@RequestMapping(value = "/teste1", produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String t1(@RequestParam("name") String name) {

		ServiceInstance localInstance = client.getLocalServiceInstance();
		return name+" "+ localInstance.getServiceId()+":"+localInstance.getHost()+":"+localInstance.getPort();
	}

	@RequestMapping(value = "/teste2/{name}", produces = {MediaType.APPLICATION_JSON_VALUE})
	public String t2(@PathVariable("name") String name) {

		ServiceInstance localInstance = client.getLocalServiceInstance();
		return name+" - "+ localInstance.getServiceId()+":"+localInstance.getHost()+":"+localInstance.getPort();
	}

	@RequestMapping(value = "/teste3", method = POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String t3(@RequestBody UserStats stats) {

		ServiceInstance localInstance = client.getLocalServiceInstance();
		return stats.getFirstName()+" - "+ localInstance.getServiceId()+":"+localInstance.getHost()+":"+localInstance.getPort();
	}

	@RequestMapping(value = "/teste4", method = POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody String t4(@RequestBody UserStats stats) {

		ServiceInstance localInstance = client.getLocalServiceInstance();
		return stats.getFirstName()+" - "+ localInstance.getServiceId()+":"+localInstance.getHost()+":"+localInstance.getPort();
	}

	public static void main(String[] args) {
		SpringApplication.run(HelloServerApplication.class, args);
	}

	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class UserStats {

		private String firstName;
		private String lastName;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		@JsonCreator
		public UserStats(@JsonProperty("firstName") String firstName,
						 @JsonProperty("lastName") String lastName) {
			this.firstName = firstName;
			this.lastName = lastName;
		}

		public UserStats() {
		}
	}

}
