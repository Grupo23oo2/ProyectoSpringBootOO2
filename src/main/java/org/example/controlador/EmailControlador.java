package org.example.controlador;

import org.example.servicios.IEmailServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/email")
public class EmailControlador {

    @Autowired
    private IEmailServicio emailServicio;

    @GetMapping("/enviar")
    public String enviarEmailDePrueba() {
        emailServicio.enviarCorreo(
                "matiasgiudiceunla@gmail.com",
                "Correo de prueba desde Spring Boot",
                "Hola! Este es un correo de prueba enviado con Spring Boot."
        );
        return "Correo enviado!";
    }

    @GetMapping("/enviarhtml")
    public String enviarEmailHtml() {
        String destinatario = "matiasgiudiceunla@gmail.com";
        String asunto = "Prueba email HTML desde Spring Boot";
        String contenidoHtml = """
            <html>
            <body>
                <h1 style='color: blue;'>Hola desde Spring Boot!</h1>
                <p>Este es un correo <b>con formato HTML</b>.</p>
            </body>
            </html>
            """;

        emailServicio.enviarCorreoHtml(destinatario, asunto, contenidoHtml);

        return "Email enviado (revisá tu bandeja de entrada)";
    }


}
