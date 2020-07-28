package br.edu.ifal.enology.service;

import java.util.Properties;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.edu.ifal.enology.model.Usuario;

@Service
public class EmailService{

    @Value("${spring.mail.username}")
    private String meuEmail;
    @Value("${spring.mail.password}")
    private String senha;

    public void enviarEmail(String assunto, String emailDestino, String mensagem) throws Exception {
        Session session = Session.getInstance(getProperties(), new Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(meuEmail, senha);
            }
        });

        Address[] adressDestination = InternetAddress.parse(emailDestino);

        InternetAddress adressSender = new InternetAddress();
        adressSender.setPersonal("Equipe E-nology");
        adressSender.setAddress(meuEmail);

        Message message = new MimeMessage(session);
        message.setFrom(adressSender);
        message.addRecipients(Message.RecipientType.TO, adressDestination);
        message.setSubject(assunto);
         message.setText(mensagem);

        Transport.send(message);
    }

    public void enviarEmailConfirmacao(Usuario usuario){
        try {
        enviarEmail("E-nology - Verificação de Email", usuario.getEmail(), "Bem vindo ao E-nology, " + usuario.getNome() + "! " +
                    "\nPara ativar sua conta, use o código: \n" + usuario.getCodigoVerificacao() + 
                    ".\nOu, clique no link: https://e-nology.herokuapp.com/ativar-conta/?codigoVerificacao=" + 
                    usuario.getCodigoVerificacao() + "\n\nSe você não se cadastrou em nosso sistema, pedimos que desconsidere este email.");
        }catch(Exception e){
            System.err.println(e);
        }
    }

    private Properties getProperties() {

        Properties properties = new Properties();

        properties.put("mail.smtp.ssl.trust", "*");
        properties.put("mail.smtp.auth", true);
        properties.put("mail.smtp.starttls", true);
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.socketFactory.port", "465");
        properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");

        return properties;
    }

}