package br.edu.ifal.enology.exception;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {    

    @ExceptionHandler({ ClassNotFoundException.class, NoSuchElementException.class, NotFoundException.class, UsernameNotFoundException.class, BadCredentialsException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ModelAndView handleNoValuePresent(Exception exception) {
        ModelAndView model = new ModelAndView("error");

        model.setViewName("error/404");
        model.addObject("msgErro", "O Link ou Recurso que você está buscando não existe, " +
                        "ou pode estar expirado!");
        return model;
    }

    @ExceptionHandler({ MethodArgumentTypeMismatchException.class, NumberFormatException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ModelAndView handleFormatConversion(Exception exception) {
        ModelAndView model = new ModelAndView("error");
        HttpStatus status = HttpStatus.NOT_FOUND;

        model.addObject("message", exception.getMessage()).addObject("time", LocalDateTime.now())
                .addObject("status", status.value()).addObject("error", status.getReasonPhrase())
                .addObject("details", exception.fillInStackTrace());

        return model;
    }

}