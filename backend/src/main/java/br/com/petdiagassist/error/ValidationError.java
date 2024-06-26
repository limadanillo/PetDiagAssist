package br.com.petdiagassist.error;

import br.com.petdiagassist.utils.StaticContextUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * Class Validation Error
 *
 * @author Danillo Lima
 * @since 13/05/2024
 */
@Log4j2
public class ValidationError {

    public static List<ParamErrorDetails> getParamErrorDetails(ConstraintViolationException ex) {
        Set<ConstraintViolation<?>> violation = ex.getConstraintViolations();
        List<String> field = new ArrayList<>();
        List<ParamErrorDetails> params = new ArrayList<>();
        violation.forEach(x ->{
            Iterator<Path.Node> nodes = x.getPropertyPath().iterator();
            Path.Node firstNode = nodes.next();
            Path.Node secondNode = nodes.next();
            params.add(ParamErrorDetails.builder()
                    .value(x.getInvalidValue() != null ? x.getInvalidValue().toString() :"")
                    .message(x.getMessage())
                    .property(secondNode.getName())
                    .build());
        });
        return params;
    }

    public static List<ParamErrorDetails> getParamErrorDetails(WebExchangeBindException ex) {
        List<FieldError> allErrors = ex.getFieldErrors();
        List<ParamErrorDetails> params = new ArrayList<>();
        allErrors.forEach(x ->{
            params.add(ParamErrorDetails.builder()
                    .value(getValue(x))
                    .message(messageSource(x))
                    .property(x.getField())
                    .build());
        });
        return params;
    }

    private static String getValue(FieldError x) {
        return x.getRejectedValue() != null ? x.getRejectedValue().toString() : null;
    }

    private static String messageSource(FieldError x) {
        String code = new StringBuffer("message.error.").
                append(x.getField().replaceAll("\\[(.*?)\\]", "").toLowerCase())
                .append(".")
                .append(x.getCode().toLowerCase())
                .toString();
        String msg = "";
        MessageSource messageSource = StaticContextUtils.getBean(MessageSource.class);
        try {
            msg = messageSource.getMessage(code, null, Locale.getDefault());
        } catch (NoSuchMessageException e){
            msg = getMessageDefaultOrCustomized(x, messageSource);
        }
        return msg;
    }

    private static String getMessageDefaultOrCustomized(FieldError x, MessageSource messageSource) {
        String msg;
        if(x.getDefaultMessage().contains("{") && x.getDefaultMessage().contains("}")) {
            msg = messageSource.getMessage(x.getDefaultMessage().replaceAll("\\{", "").replaceAll("\\}", ""), null, Locale.getDefault());
        } else {
            msg = x.getDefaultMessage();
        }
        return msg;
    }
}
