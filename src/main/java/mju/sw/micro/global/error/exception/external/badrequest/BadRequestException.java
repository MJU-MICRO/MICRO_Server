package mju.sw.micro.global.error.exception.external.badrequest;

import mju.sw.micro.global.error.exception.AbstractApiException;
import mju.sw.micro.global.error.exception.MicroErrorCode;

public class BadRequestException extends AbstractApiException {
    public BadRequestException(String msg) {
        super(MicroErrorCode.BAD_REQUEST, msg);
    }
}
