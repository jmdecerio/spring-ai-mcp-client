package com.jmdecerio.spring_ai_mcp_client;

import com.jmdecerio.spring_ai_mcp_client.config.ToolsProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(ToolsProperties.class)
public class SpringAiMcpClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringAiMcpClientApplication.class, args);
	}

}
