package dio.budgeting.controller;

import dio.budgeting.service.AssistantService;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.openai.OpenAiAudioTranscriptionModel;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionPrompt;
import org.springframework.ai.openai.audio.transcription.AudioTranscriptionResponse;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/assistant")
@RequiredArgsConstructor
public class TranscriptionController {

    private final OpenAiAudioTranscriptionModel transcriptionModel;
    private final AssistantService assistantService;

    @PostMapping("/transcribe")
    public ResponseEntity<Map<String, String>> transcribe(
            @RequestParam("audio") MultipartFile audio) throws IOException {

        // Converte o áudio recebido para um recurso Spring
        ByteArrayResource audioResource = new ByteArrayResource(audio.getBytes()) {
            @Override
            public String getFilename() {
                return audio.getOriginalFilename();
            }
        };

        // Transcreve o áudio via Whisper
        AudioTranscriptionPrompt prompt = new AudioTranscriptionPrompt(audioResource);
        AudioTranscriptionResponse transcriptionResponse = transcriptionModel.call(prompt);
        String transcribedText = transcriptionResponse.getResult().getOutput();

        // Passa o texto transcrito direto para o assistente
        String assistantResponse = assistantService.chat(transcribedText);

        return ResponseEntity.ok(Map.of(
                "transcription", transcribedText,
                "response", assistantResponse
        ));
    }
}