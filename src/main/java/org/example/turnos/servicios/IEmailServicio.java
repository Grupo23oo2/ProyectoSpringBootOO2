package org.example.turnos.servicios;

import java.util.Map;

public interface IEmailServicio {
    void enviarCorreo(String destinatario, String asunto, String contenido);

    public void enviarCorreoHtml(String para, String asunto, String htmlBody);
    
    void enviarCorreoConPlantilla(String para, String asunto, Map<String, Object> variables);

}
