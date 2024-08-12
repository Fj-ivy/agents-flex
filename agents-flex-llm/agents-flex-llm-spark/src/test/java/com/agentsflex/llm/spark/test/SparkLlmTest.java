package com.agentsflex.llm.spark.test;

import com.agentsflex.core.document.Document;
import com.agentsflex.core.llm.Llm;
import com.agentsflex.core.llm.response.FunctionMessageResponse;
import com.agentsflex.core.message.HumanMessage;
import com.agentsflex.core.prompt.FunctionPrompt;
import com.agentsflex.core.prompt.HistoriesPrompt;
import com.agentsflex.core.store.VectorData;
import com.agentsflex.llm.spark.SparkLlm;
import com.agentsflex.llm.spark.SparkLlmConfig;
import org.junit.Test;

import java.util.Scanner;

public class SparkLlmTest {

    @Test()
    public void testSimple() {
        SparkLlmConfig config = new SparkLlmConfig();
        config.setAppId("****");
        config.setApiKey("****");
        config.setApiSecret("****");

        Llm llm = new SparkLlm(config);
        String result = llm.chat("你好，请问你是谁？");
        System.out.println(result);
    }

    @Test
    public void testEmbedding() {
        SparkLlmConfig config = new SparkLlmConfig();
        config.setAppId("****");
        config.setApiKey("****");
        config.setApiSecret("****");

        Llm llm = new SparkLlm(config);
        VectorData vectorData = llm.embed(Document.of("你好，请问你是谁？"));
        System.out.println(vectorData);
    }


    @Test
    public void testFunctionCalling() throws InterruptedException {
        SparkLlmConfig config = new SparkLlmConfig();
        config.setAppId("****");
        config.setApiKey("****");
        config.setApiSecret("****");
        config.setDebug(true);


        Llm llm = new SparkLlm(config);

        FunctionPrompt prompt = new FunctionPrompt("今天北京的天气怎么样", WeatherFunctions.class);
        FunctionMessageResponse response = llm.chat(prompt);

        Object result = response == null ? null : response.getFunctionResult();

        System.out.println(result);
    }


    public static void main(String[] args) {
        SparkLlmConfig config = new SparkLlmConfig();
        config.setAppId("****");
        config.setApiKey("****");
        config.setApiSecret("****");

        Llm llm = new SparkLlm(config);

        HistoriesPrompt prompt = new HistoriesPrompt();

        System.out.println("您想问什么？");
        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        while (userInput != null) {

            prompt.addMessage(new HumanMessage(userInput));

            llm.chatStream(prompt, (context, response) -> {
                System.out.println(">>>> " + response.getMessage().getContent());
            });

            userInput = scanner.nextLine();
        }
    }
}
