package com.jmdecerio.spring_ai_mcp_client.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.SyncMcpToolCallbackProvider;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatClient chatClient;
    private final SyncMcpToolCallbackProvider mcpTools;
    private final Environment env;

    public ChatController(ChatClient.Builder chatClientBuilder,
                          SyncMcpToolCallbackProvider mcpTools,
                          Environment env) {
        this.chatClient = chatClientBuilder.build();
        this.mcpTools = mcpTools;
        this.env = env;
    }

    @PostMapping
    public String chat(@RequestBody String msg) {
        ToolCallback[] tools = mcpTools.getToolCallbacks();
        String key = env.getProperty("TAVILY_API_KEY");
        log.info("TAVILY_API_KEY present: " + (key != null && !key.isBlank()));
        return chatClient
                .prompt("""
    Eres un asistente. Si para responder necesitas información actual o verificar un dato:
    - Usa herramientas Tavily.
    - Para confirmar precios/disponibilidad: primero usa tavily_search (preferiblemente restringiendo a la tienda o dominio si el usuario lo menciona).
    - Si el resultado incluye una URL relevante con el dato (p.ej. ficha de producto), usa tavily_extract sobre esa URL.
    - Máximo 2 llamadas a herramientas por respuesta (search y, si aplica, extract). No hagas bucles.
    - Si tras extract no hay dato explícito, responde “no encontrado” y aporta las URLs consultadas.

    Responde en español y cita 2-3 URLs cuando uses Tavily.

    Usuario:
    %s
    """.formatted(msg))
                .toolCallbacks(tools)
                .call()
                .content();
    }
}
