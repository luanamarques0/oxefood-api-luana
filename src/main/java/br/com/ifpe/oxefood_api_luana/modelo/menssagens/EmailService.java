package br.com.ifpe.oxefood_api_luana.modelo.menssagens;

import java.nio.charset.StandardCharsets;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import br.com.ifpe.oxefood_api_luana.modelo.cliente.Cliente;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Component
public class EmailService {

    @Value("${spring.mail.username}") //Pega das variaveis de ambiente
    String username;

    @Value("${spring.mail.password}")
    String password;

    @Value("${spring.mail.host}")
    String host;

    @Value("${spring.mail.port}")
    int port;

    @Value("${spring.mail.properties.mail.smtp.auth}")
    String smtpAuth;

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    String starttls;

    private JavaMailSender emailSender;

    public void enviarEmailConfirmacaoCadastroCliente(Cliente cliente) {

        String assuntoEmail = "Bem vindo ao nosso aplicativo";

        Context params = new Context();
        params.setVariable("cliente", cliente);

        this.sendMailTemplate(
                "bem_vindo_cliente", // arquivo html que criamos: nome do arquivo(precisa ser exatamente igual)
                cliente.getUsuario().getUsername(), // Quem vai receber o e-mail
                assuntoEmail,
                params); // É o que vai passar para o arquivo html no <span th:text="${cliente.nome} ?: ''">
                // Se precisar de outro params ex:. <span th:text="${cpf} ?: ''"> -> params.setVariable("cpf", cliente.getCpf());
                // O params não precisa colocar mais de um, esse já é suficiente.
    }

    @Async
    private void sendMailTemplate(String template, String to, String subject, Context params) {

        TemplateEngine templateEngine = new TemplateEngine();

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setPrefix("templates/");
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding("UTF-8");
        templateResolver.setOrder(0);

        templateEngine.setTemplateResolver(templateResolver);

        String content = templateEngine.process(template, params);
        this.sendMail(to, subject, content, Boolean.TRUE);
    }

    @Async
    private void sendMail(String to, String subject, String content, Boolean html) {

        emailSender = getJavaMailSender();

        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        try {

            helper.setFrom(new InternetAddress("not.reply@delifacil.com.br"));
            helper.setTo(to);
            helper.setSubject(subject);
            // helper.setText(new String(content.getBytes(), StandardCharsets.ISO_8859_1), html);
            helper.setText(content, html);
            helper.setEncodeFilenames(true);

        } catch (MessagingException e) {
            e.printStackTrace();
        }

        emailSender.send(message);
    }

    private JavaMailSender getJavaMailSender() {

        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", smtpAuth);
        props.put("mail.smtp.starttls.enable", starttls);
        props.put("mail.debug", "false");
        props.put("spring.mail.properties.mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}
