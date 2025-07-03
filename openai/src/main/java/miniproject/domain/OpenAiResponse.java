package miniproject.domain;

import lombok.Data;
import java.util.List;

@Data
public class OpenAiResponse {
    private List<ImageData> data;
    
    @Data
    public static class ImageData {
        private String url;
    }
}