package mju.sw.micro.global.error.exception.external.notfound;


import mju.sw.micro.global.error.exception.AbstractApiException;
import mju.sw.micro.global.error.exception.MicroErrorCode;

public class NotFoundException extends AbstractApiException {
    public NotFoundException(String msg) {
        super(MicroErrorCode.NOT_FOUND, msg);
    }
}
