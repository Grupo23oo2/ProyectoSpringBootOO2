package org.example.turnos.servicios.implementacion;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import org.example.turnos.excepciones.MiExcepcionPersonalizada;
import org.example.turnos.servicios.IEmailServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.util.Map;

@Service
public class EmailServicio implements IEmailServicio {
    @Autowired
    private JavaMailSender mailSender;
    
    @Autowired
    private TemplateEngine templateEngine;



    public EmailServicio(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarCorreo(String destinatario, String asunto, String contenido) {
    	try {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(contenido);
        mailSender.send(mensaje);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo enviar el correo" + e.getMessage());
        }
    }
    
 // Este método ahora recibe también las variables para la plantilla Thymeleaf
    public void enviarCorreoConPlantilla(String para, String asunto, Map<String, Object> variables) {
    	try {
        Context context = new Context();
        context.setVariables(variables);

        // Procesamos la plantilla
        String htmlBody = templateEngine.process("bienvenida", context);

        enviarCorreoHtml(para, asunto, htmlBody);
    	} catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo enviar el correo con la plantilla" + e.getMessage());
        }
    }
/*
    @Override
    public void enviarCorreoHtml(String para, String asunto, String htmlBody) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(para);
            helper.setSubject(asunto);
            helper.setText(htmlBody, true); // true = contenido HTML

            mailSender.send(mensaje);
        } catch (MessagingException e) {
            e.printStackTrace(); // o usar un logger
        }
    }*/
    
    @Override
    public void enviarCorreoHtml(String para, String asunto, String htmlBody) {
        try {
            MimeMessage mensaje = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensaje, true, "UTF-8");

            helper.setTo(para);
            helper.setSubject(asunto);
            helper.setText(htmlBody, true); // true = contenido HTML

            mailSender.send(mensaje);
        } catch (Exception e){
            throw new MiExcepcionPersonalizada("No se pudo enviar el correo" + e.getMessage());
        }
        }
    

}
