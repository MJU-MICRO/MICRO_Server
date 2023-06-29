package mju.sw.micro.global.error.exception.external.forbidden;


import mju.sw.micro.global.error.exception.AbstractApiException;
import mju.sw.micro.global.error.exception.MicroErrorCode;

public class ForbiddenException extends AbstractApiException {
    public ForbiddenException(String msg) {
        super(MicroErrorCode.FORBIDDEN, msg);
    }
}
