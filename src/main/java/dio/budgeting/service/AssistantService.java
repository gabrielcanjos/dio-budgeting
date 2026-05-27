package dio.budgeting.service;

import dio.budgeting.model.Transaction;
import dio.budgeting.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssistantService {

    private final ChatClient chatClient;
    private final TransactionRepository transactionRepository;

    public String chat(String userMessage) {
        BigDecimal income  = transactionRepository.sumIncome();
        BigDecimal expense = transactionRepository.sumExpense();
        BigDecimal balance = income.subtract(expense);
        List<Transaction> recent = transactionRepository.findAll()
                .stream().limit(10).toList();

        String context = """
                VocÃƒÂª ÃƒÂ© um assistente financeiro inteligente chamado BudgetBot.
                Responda sempre em portuguÃƒÂªs, de forma clara e objetiva.
                
                === SITUAÃƒâ€¡ÃƒÆ’O FINANCEIRA ATUAL ===
                Total de receitas:  R$ %s
                Total de despesas:  R$ %s
                Saldo atual:        R$ %s
                
                === ÃƒÅ¡LTIMAS TRANSAÃƒâ€¡Ãƒâ€¢ES ===
                %s
                
                === PERGUNTA DO USUÃƒÂRIO ===
                %s
                """.formatted(income, expense, balance, formatTransactions(recent), userMessage);

        return chatClient.prompt()
                .user(context)
                .call()
                .content();
    }

    private String formatTransactions(List<Transaction> transactions) {
        if (transactions.isEmpty()) return "Nenhuma transaÃƒÂ§ÃƒÂ£o registrada ainda.";
        StringBuilder sb = new StringBuilder();
        for (Transaction t : transactions) {
            sb.append("- [%s] %s: R$ %s (%s) em %s%n"
                    .formatted(t.getType(), t.getDescription(),
                               t.getAmount(), t.getCategory(),
                               t.getCreatedAt().toLocalDate()));
        }
        return sb.toString();
    }
}
