package demo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import feign.Logger;
import feign.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author Spencer Gibb
 */
@SpringBootApplication
@EnableDiscoveryClient
@RestController
@EnableFeignClients
public class HelloClientApplication {
	@Autowired
	HelloClient client;

	@RequestMapping("/")
	public String hello() {

		System.out.println(client.hello());
		System.out.println(client.t1("nome teste"));
		System.out.println(client.t2("nome teste 2"));

		HashMap<String, Object> teste2 = new HashMap<String, Object>();
		teste2.put("firstName", "fabio");
		teste2.put("lastName", "balancin");
		System.out.println(client.t4(teste2));

		return "teste";
	}

	public static void main(String[] args) {

		SpringApplication.run(HelloClientApplication.class, args);

	}

	@FeignClient("HelloServer")
	public interface HelloClient {

		@RequestMapping(value = "/", method = GET)
		String hello();
		@RequestMapping(value = "/teste1", method = GET, consumes = "application/json")
		String t1(@RequestParam(value = "name") String name);
		@RequestMapping(value = "/teste2/{name}", method = GET, consumes = "application/json")
		String t2(@PathVariable(value ="name") String name);
		@RequestMapping(value = "/teste4", method = POST, consumes = "application/json")
		String t4(@RequestBody HashMap<String, Object> stats);

	}

}
