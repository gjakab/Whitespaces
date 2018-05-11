package hu.elte.whitespaces.tester.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.annotation.SessionScope;

import hu.elte.whitespaces.tester.model.Answer;
import hu.elte.whitespaces.tester.repository.AnswerRepository;

@Service
@SessionScope
public class AnswerService {
    private AnswerRepository answerRepository;
    private QuestionService questionService;

    @Autowired
    public AnswerService(AnswerRepository answerRepository, QuestionService questionService) {
        this.answerRepository = answerRepository;
        this.questionService = questionService;
    }

    public Answer getAnswerByQuestionId(Integer anId) {
        Optional<Answer> result = answerRepository.findById(anId);

        if (result.isPresent()) {
            return result.get();
        }

        return null;
    }

    public List<Answer> getAllAnswersByQuestion(Integer qId) {
        Iterable<Answer> answers = answerRepository.findAllByQuestionId(qId);
        List<Answer> results = new ArrayList<>();

        answers.forEach(answer -> results.add(answer));
        return results;
    }

    @Transactional
    public Answer create(Answer answer, Integer qId) {
        answer.setQuestion(questionService.getQuestionById(qId));
        return answerRepository.save(answer);
    }

    @Transactional
    public Answer update(Integer anId, Answer answer) {
        Optional<Answer> result = answerRepository.findById(anId);

        if (result.isPresent()) {
            Answer currentAnswer = result.get();
            currentAnswer.setAnswer(answer.getAnswer());
            currentAnswer.setRightAnswer(answer.isRightAnswer());

            return answerRepository.save(currentAnswer);
        }

        return null;
    }

    @Transactional
    public boolean delete(Integer anId) {
        Optional<Answer> result = answerRepository.findById(anId);

        if (result.isPresent()) {
            answerRepository.delete(result.get());
            return true;
        }

        return false;
    }
}
