abstract class SupportHandler {
    protected SupportHandler next;

    public SupportHandler setNext(SupportHandler handler) {
        this.next = handler;
        return handler;
    }

    public abstract void handle(String issue);
}

class FAQBotHandler extends SupportHandler {
    public void handle(String issue) {
        if (issue.equals("password_reset")) {
            System.out.println("[FAQBot] Handled " + issue);
        } else if (next != null) {
            next.handle(issue);
        }
    }
}

class JuniorSupportHandler extends SupportHandler {
    public void handle(String issue) {
        if (issue.equals("refund_request") || issue.equals("billing_issue")) {
            System.out.println("[JuniorSupport] Handled " + issue);
        } else if (next != null) {
            next.handle(issue);
        }
    }
}

class SeniorSupportHandler extends SupportHandler {
    public void handle(String issue) {
        if (issue.equals("account_ban") || issue.equals("data_loss")) {
            System.out.println("[SeniorSupport] Handled " + issue);
        } else {
            System.out.println("[SeniorSupport] Cannot handle " + issue + " — escalate manually");
        }
    }
}

public class ChainOfResponsibilityDemo {
    public static void main(String[] args) {
        SupportHandler faq = new FAQBotHandler();
        SupportHandler junior = new JuniorSupportHandler();
        SupportHandler senior = new SeniorSupportHandler();

        faq.setNext(junior).setNext(senior);

        String[] issues = {"password_reset", "refund_request", "account_ban", "unknown_bug"};
        for (String issue : issues) {
            faq.handle(issue);
        }
    }
}

