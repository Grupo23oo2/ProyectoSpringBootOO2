package org.example.servicios;

import java.util.Map;

public interface IEmailServicio {
    void enviarCorreo(String destinatario, String asunto, String contenido);

    public void enviarCorreoHtml(String para, String asunto, String htmlBody);

}
