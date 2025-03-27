package br.edu.ifal.enology.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import br.edu.ifal.enology.model.Usuario;
import freemarker.template.Configuration;
import freemarker.template.Template;

@Service
public class EmailService {

    @Value("${spring.mail.username}")
    private String meuEmail;
    @Value("${spring.mail.password}")
    private String senha;
    @Autowired
    private Configuration config;

    public void enviarEmail(String assunto, String emailDestino, String html, Map<String, Object> templateData)
            throws Exception {
        Session session = Session.getInstance(getProperties(), new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(meuEmail, senha);
            }
        });

        Message message = construirEmailEnvio(assunto, emailDestino, html, templateData, session);
        Transport.send(message);
    }

    private Message construirEmailEnvio(
        String assunto, String emailDestino, String html, Map<String, Object> templateData, Session session
    ) throws Exception {
        Address[] adressDestination = InternetAddress.parse(emailDestino);

        InternetAddress adressSender = new InternetAddress();
        adressSender.setPersonal("Equipe E-nology");
        adressSender.setAddress(meuEmail);

        Message message = new MimeMessage(session);
        String pageContent = convertTemplatePageIntoString(html, templateData);
        message.setFrom(adressSender);
        message.addRecipients(Message.RecipientType.TO, adressDestination);
        message.setSubject(assunto);
        message.setContent(pageContent, "text/html; charset=utf-8");
        // message.setText(mensagem);
        return message;
    }

    private String convertTemplatePageIntoString(String html, Map<String, Object> templateData) throws Exception {
        Template pageTemplate = config.getTemplate(html);
        return FreeMarkerTemplateUtils.processTemplateIntoString(pageTemplate, templateData);
    }

    public void enviarEmailRedefinirEmail(String token, Usuario usuario) {
        try {
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("title", "Hello, " + usuario.getNome() + "! ");
            templateData.put("email", usuario.getEmail());
            templateData.put("link",
                    "https://e-nology.herokuapp.com/redefinir-email?tk=" + token + "&email=" + usuario.getEmail());

            enviarEmail("E-nology - Redefinição de endereço de e-mail", usuario.getEmail(),
                    "mails/redefinir-email.html", templateData);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void enviarEmailConfirmacao(Usuario usuario) {

        try {
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("bv", "Welcome to E-nology, " + usuario.getNome() + "! ");
            templateData.put("cod", "" + usuario.getCodigoVerificacao());
            templateData.put("link",
                    "https://e-nology.herokuapp.com/ativar-conta/?codigoVerificacao=" + usuario.getCodigoVerificacao());

            enviarEmail("E-nology - Verificação de Email", usuario.getEmail(), "mails/confirmacao.html", templateData);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public boolean enviarEmailRedefinirSenha(String token, Usuario usuario) {

        try {
            Map<String, Object> templateData = new HashMap<>();
            templateData.put("title", "Hello, " + usuario.getNome());
            templateData.put("link", "https://e-nology.herokuapp.com/redefinir-senha?tk=" + token);

            enviarEmail("E-nology - Tudo Pronto para Você Redefinir Sua Senha!", usuario.getEmail(),
                    "mails/redefinir-senha.html", templateData);

            return true;
        } catch (Exception e) {
            System.err.println(e);
            return false; 
        }
    }

    private Properties getProperties() {

        Properties properties = new Properties();

        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.debug", true);
        properties.put("mail.smtp.starttls", true);
        properties.put("mail.smtp.socketFactory.fallback", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return properties;
    }

}