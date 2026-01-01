package com.jmdecerio.spring_ai_mcp_client.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatClient chatClient;
    private final ToolCallback[] tavilyTools;

    public ChatController(ChatClient.Builder chatClientBuilder,
                          SyncMcpToolCallbackProvider mcpTools) {
        this.chatClient = chatClientBuilder.build();

        this.tavilyTools = Arrays.stream(mcpTools.getToolCallbacks())
                .filter(tc -> (tc.getToolDefinition().name().equals("tavily_search")
                        || tc.getToolDefinition().name().equals("tavily_extract")))
                .toArray(ToolCallback[]::new);
    }

    @PostMapping
    public String chat(@RequestBody String msg) {
        return chatClient
                .prompt()
                .system("""
                        Eres un asistente con acceso a internet a traves del tool Tavily.
                        Si para responder necesitas usar la tool, devuelve directamente el resultado de UNA busqueda (tavily_search).
                        Si no te es posible obtener la respuesta, responde que no fue posible obtener la información
                        """)
                .user(msg)
                .toolCallbacks(tavilyTools)
                .call()
                .content();
    }
}