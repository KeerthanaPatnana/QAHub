package backend.Services;


import backend.DTOs.AnswerDTO;
import backend.DTOs.QuestionDTO;
import backend.Entity.Question;
import backend.Repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {
    private QuestionRepository questionRepository;

    @Autowired
    QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public List<QuestionDTO> getQuestions() {
        List<Question> questions = questionRepository.findAll();
        List<QuestionDTO> questionDTOs = convertToDto(questions);
        return questionDTOs;
    }

    public String createQuestion(Question question) {
        questionRepository.save(question);
        return "Question saved successfully";
    }

    public List<QuestionDTO> convertToDto(List<Question> questions) {
        return questions.stream().map(question -> {
            QuestionDTO dto = new QuestionDTO();
            dto.setqId(question.getId());
            dto.setTitle(question.getTitle());
            dto.setContent(question.getContent());
            dto.setAskedBy(question.getAskedBy().getName());
            dto.setAskedAt(question.getCreatedAt());

            // Convert answers to AnswerDTO
            List<AnswerDTO> answerDTOs = question.getAnswers().stream().map(answer -> {
                AnswerDTO answerDTO = new AnswerDTO();
                answerDTO.setContent(answer.getContent());
                answerDTO.setAnsweredBy(answer.getAnsweredBy().getName());
                answerDTO.setAnsweredAt(answer.getCreatedAt());
                return answerDTO;
            }).collect(Collectors.toList());

            dto.setAnswers(answerDTOs);

            return dto;
        }).collect(Collectors.toList());
    }

}
