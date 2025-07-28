package backend.Controllers;

import backend.Entity.Answer;
import backend.Services.AnswerService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequestMapping("/answers")
public class AnswerController {

    private AnswerService answerService;

    AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @PostMapping
    public String postAnswer(@RequestBody Answer answer) {
        return answerService.postAnswer(answer);
    }

}
