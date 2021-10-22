/*
 ///////////////////////// JAVA MAIL SENDER  //////////////////////////////////////////////
 */
package com.weblab.app.servicios;

import java.io.File;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.weblab.app.entidades.Usuario;
import com.weblab.app.repositorios.UsuarioRepositorio;

@Service
public class NotificacionService {

	@Autowired
	private JavaMailSender mailSender;

	@Autowired
	private UsuarioRepositorio usuarioRepositorio;

	@Value("${spring.mail.username}")
	private String mailFrom;

	@Value("${spring.mail.password}")
	private String mailPassword;

	
	
	@Async
	public void enviarMail(String cuerpo, String titulo, String mail) {

		SimpleMailMessage mensaje = new SimpleMailMessage();

		// DIRECCIÓN HACIA DONDE VA DIRIGIDO EL MAIL
		mensaje.setTo(mail);

		// DIRECCIÓN DESDE DONDE SE ENVIA EL MAIL (estaría bueno traer el usuario mediante session)
		mensaje.setFrom("dileandrog@gmail.com");
		mensaje.setSubject(titulo);
		mensaje.setText(cuerpo);

		mailSender.send(mensaje);
	}
	
	

	public boolean notificar(String id, String mailTo, String cuerpo, String titulo, String mail) {

		try {
			Usuario usuario = usuarioRepositorio.getOne(id);

			if (usuario != null) {
				enviarMail(cuerpo, titulo, mail);
//                enviarHTML(usuario, mailTo);

				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			System.out.println("Se ha producido un error de " + e.getMessage());
			return false;
		}
	}
	
	

	@Async
	private void enviarHTML(Usuario usuario, String mailTo) {

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", true);
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", 587);
		prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");

		Session session = Session.getInstance(prop, new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(mailFrom, mailPassword);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(mailFrom));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			message.setSubject("Mail Subject");

			String msg = "<h1>" + usuario.getNombre() + "</h1>" + "<h2>" + usuario.getApellido() + "</h2>" + "<h2>"
					+ usuario.getEmail() + "</h2>" + "<img src=" + usuario.getFoto() + ">";

			MimeBodyPart mimeBodyPart = new MimeBodyPart();
			mimeBodyPart.setContent(msg, "text/html");

			// ENVIAR ARCHIVOS DESDE EL SERVIDOR O BUCKET
			MimeBodyPart attachmentBodyPart = new MimeBodyPart();
			attachmentBodyPart.attachFile(new File("pom.xml"));
			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(mimeBodyPart);
			// multipart.addBodyPart(attachmentBodyPart);

			message.setContent(multipart);

			Transport.send(message);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	

	public void sendMailWithAttachment(Usuario usuario, String mail, String titulo, String cuerpo,
			String fileToAttach) {

		MimeMessagePreparator preparator = new MimeMessagePreparator() {

			public void prepare(MimeMessage mimeMessage) throws Exception {

				mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(mail));
				mimeMessage.setFrom(new InternetAddress("franciscoaguirreweb@gmail.com"));
				mimeMessage.setSubject(titulo);
				mimeMessage.setText(cuerpo);

//				ClassPathResource file = new ClassPathResource(usuario.getNombre() + ".pdf");
				FileSystemResource file = new FileSystemResource(new File(fileToAttach));
				MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
				helper.addAttachment(usuario.getNombre() + ".pdf", file);
				
		        //send off the email
		        Transport.send(mimeMessage);
			}
		};

		try {
			mailSender.send(preparator);
		} catch (MailException ex) {
			// simply log it and go on...
			System.err.println(ex.getMessage());
		}
	}


}
