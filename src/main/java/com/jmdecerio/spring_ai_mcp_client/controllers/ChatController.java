package com.jmdecerio.spring_ai_mcp_client.controllers;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatClient chatClient;
    private final SyncMcpToolCallbackProvider mcpTools;

    public ChatController(ChatClient.Builder chatClientBuilder,
                          SyncMcpToolCallbackProvider mcpTools) {
        this.chatClient = chatClientBuilder.build();
        this.mcpTools = mcpTools;
    }

    @PostMapping
    public String chat(@RequestBody String msg) {
        ToolCallback[] tools = mcpTools.getToolCallbacks();

        return chatClient
                .prompt(msg)
                .toolCallbacks(tools)
                .call()
                .content();
    }
}
