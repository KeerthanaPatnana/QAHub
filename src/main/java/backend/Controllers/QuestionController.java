package backend.Controllers;


import backend.DTOs.QuestionDTO;
import backend.Entity.Answer;
import backend.Entity.Question;
import backend.Repositories.AnswerRepository;
import backend.Services.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/questions")
public class QuestionController {
    private QuestionService questionService;
    private AnswerRepository answerRepository;

    @Autowired
    QuestionController(QuestionService questionService, AnswerRepository answerRepository) {
        this.questionService = questionService;
        this.answerRepository = answerRepository;
    }

    @GetMapping
    public List<QuestionDTO> getQuestions() {
        return questionService.getQuestions();
    }

    @PostMapping
    public String postAQuestion(@RequestBody Question question) {
        if (question.getTitle() != null) {
            return questionService.createQuestion(question);
        }
        return "";
    }

    @GetMapping("/{questionId}/answers")
    public List<Answer> getAnswersById(@PathVariable("questionId") Long id) {
        return answerRepository.findByQuestionId(id);
    }
}
