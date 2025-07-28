package backend.Services;

import backend.Entity.Answer;
import backend.Repositories.AnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerService {
    private AnswerRepository answerRepository;

    @Autowired
    AnswerService(AnswerRepository answerRepository) {
        this.answerRepository = answerRepository;
    }

    public String postAnswer(Answer answer) {
        answerRepository.save(answer);
        return "Answer saved successfully";
    }
}
