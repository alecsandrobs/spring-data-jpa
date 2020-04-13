package br.com.alecsandro.estudos.handler;

import org.apache.commons.io.IOUtils;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;

@ControllerAdvice
public class RestResponseExceptionHandler extends DefaultResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        System.out.println(String.format("Request status: %s", response.getStatusCode()));
        return super.hasError(response);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        System.out.println(String.format("Corpo: %s", IOUtils.toString(response.getBody(), "UTF-8")));
//        super.handleError(response);
    }
}
