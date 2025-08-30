package web.planorama.demo.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


//DENTRO DESSE CONTROLLER ADVICE TRATAMOS AS EXCEPTIONS
//QUE SÃO MUITO ESPECÍFICAS QUE NEM PASSARAM PELO CONTROLLER

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public String handlerMaxSizeException(MaxUploadSizeExceededException exception, RedirectAttributes redirectAttributes){ 
            redirectAttributes.addFlashAttribute("error", "O arquivo selecionado é muito grande.");

            return "redirect:/minha-conta";
    }
}
