package mju.sw.micro.global.error.exception.external.internalservererror;

import mju.sw.micro.global.error.exception.AbstractApiException;
import mju.sw.micro.global.error.exception.MicroErrorCode;

public class InternalServerErrorException extends AbstractApiException {

	public InternalServerErrorException(MicroErrorCode errorCode, String msg) {
		super(errorCode, msg);
	}
}
