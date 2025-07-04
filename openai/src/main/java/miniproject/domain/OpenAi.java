package miniproject.domain;

import miniproject.OpenaiApplication;
import org.springframework.beans.factory.annotation.Autowired; // <-- [수정] Autowired import 추가
import org.springframework.core.env.Environment;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import java.util.HashMap; // <-- [수정] HashMap import 추가
import java.util.Map;     
import org.springframework.web.reactive.function.client.WebClient;

@Entity
@Table(name = "OpenAi_table")
@Data

//<<< DDD / Aggregate Root
public class OpenAi {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long requestId;
    private Long bookId;
    private String prompt;
    private String coverUrl;

    private static Environment env;

    @Autowired
    public void setEnvironment(Environment env) {
        OpenAi.env = env;
    }

    public static OpenAiRepository repository() {
        OpenAiRepository openAiRepository = OpenaiApplication.applicationContext.getBean(OpenAiRepository.class);
        return openAiRepository;
    }

    //<<< Clean Arch / Port Method
    public void bookCoverCreate(BookCoverCreateCommand command) { // <-- 이 부분의 변수명을 'command'로 수정했습니다.
    // [1] 외부 API 호출을 위한 WebClient 생성
    WebClient webClient = WebClient.create("https://api.openai.com");

    // [2] application.yml을 통해 Gitpod 환경 변수에 저장된 API 키를 안전하게 가져옴
    String apiKey = env.getProperty("openai.api-key");
    if (apiKey == null || apiKey.trim().isEmpty()) {
        System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        System.err.println("!!! OPENAI_API_KEY가 설정되지 않았습니다. !!!");
        System.err.println("!!! Gitpod 환경 변수를 다시 확인해주세요. !!!");
        System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
        return;
    }

    // [3] DALL-E API에 보낼 요청 본문(Body) 생성
    Map<String, Object> requestBody = new HashMap<>();
    requestBody.put("model", "dall-e-3");
    
    // 책 제목과 내용으로 AI에게 전달할 프롬프트를 만듭니다.
    String generatedPrompt = String.format(
        "A professional and artistic book cover for a novel titled '%s'. The main theme of the story is about: %s",
        command.getTitle(),
        command.getContent() != null ? command.getContent().substring(0, Math.min(command.getContent().length(), 200)) : "" // 내용이 너무 길 경우 일부만 요약해서 사용
    );
    this.setPrompt(generatedPrompt);
    this.setBookId(command.getBookId()); // 나중에 어떤 책에 대한 요청이었는지 추적하기 위해 저장

    requestBody.put("prompt", this.getPrompt());
    requestBody.put("n", 1);
    requestBody.put("size", "1024x1024");
    requestBody.put("quality", "standard");

    System.out.println("##### OpenAI API에 프롬프트 전송: " + this.getPrompt());

    // [4] API를 비동기 방식으로 호출하고, 성공/실패 시의 동작을 정의합니다.
    webClient
        .post()
        .uri("/v1/images/generations")
        .header("Authorization", "Bearer " + apiKey)
        .bodyValue(requestBody)
        .retrieve()
        .bodyToMono(OpenAiResponse.class) // 응답 결과를 OpenAiResponse DTO로 변환
        .doOnSuccess(response -> {
            // [성공 시] API 호출이 성공하면 이 부분이 실행됩니다.
            if (response != null && response.getData() != null && !response.getData().isEmpty()) {
                String imageUrl = response.getData().get(0).getUrl();
                this.setCoverUrl(imageUrl);
                System.out.println("##### OpenAI API 응답 성공 - 이미지 URL: " + imageUrl);

                // 'CoverCreated' 이벤트를 발행하여 book 서비스에 결과를 알려줍니다.
                CoverCreated coverCreated = new CoverCreated(this);
                coverCreated.publishAfterCommit();
            }
        })
        .doOnError(error -> {
            // [실패 시] 에러 로그를 남깁니다.
            System.err.println("##### OpenAI API 호출 에러: " + error.getMessage());
            // (실제 서비스에서는 실패 이벤트를 발행하여 후속 처리를 해야 합니다.)
        })
        .subscribe(); // 비동기 호출을 시작합니다.
    }
    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void coverGenerationRequested(CoverGenerationRequested coverGenerationRequested) {

        // implement business logic here:

        /** Example 1:  new item 
        OpenAi openAi = new OpenAi();
        repository().save(openAi);
        */

        /** Example 2:  finding and process
        repository().findById(coverGenerationRequested.get???()).ifPresent(openAi -> {
            openAi // do something
            repository().save(openAi);
        });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root