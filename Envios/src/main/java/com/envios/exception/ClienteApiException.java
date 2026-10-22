package com.envios.exception;

import feign.FeignException;

public class ClienteApiException extends RuntimeException {


    private final int status;

    public ClienteApiException(FeignException cause) {
        super("La API Cliente respondió con un error.", cause);
        this.status = cause.status();
    }

    public int getStatus() {
        return status;
    }

    //    public ClienteApiException(FeignException cause) {
    //        super("La API Cliente respondió con un error HTTP " + cause.status() +
    //                " (" + cause.getClass().getSimpleName() + ").",
    //                cause);
    //    }


}
