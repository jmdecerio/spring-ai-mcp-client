package com.jmdecerio.spring_ai_mcp_client.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "tools")
public record ToolsProperties(List<String> toload) {}