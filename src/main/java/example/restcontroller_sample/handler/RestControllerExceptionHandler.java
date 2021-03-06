package example.restcontroller_sample.handler;

import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import example.restcontroller_sample.mail.SpringBootMail;
import example.restcontroller_sample.vo.ErrorResponse;

/**
 * RestControllerで処理されるパラメータのValidationにより、エラーが発生した場合の<br>
 * エラーハンドリングを管理するクラス。
 * @author Yu Yamaguchi
 *
 */
@RestControllerAdvice
public class RestControllerExceptionHandler {
	/** ログ */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/** 開発・テスト用のメールサーバ */
	@Autowired
	private SpringBootMail mail;

	/**
	 * RestControllerで実行されたValidationの結果、エラーとなった状態をハンドルし、<br>
	 * http_status=400でエラーの原因メッセージと一緒に返却する。
	 * @param e
	 * @return {@link ErrorResponse}
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
		var response = new ErrorResponse(910, e.getBindingResult().getAllErrors()
				.stream()
				.map(DefaultMessageSourceResolvable::getDefaultMessage)
				.collect(Collectors.joining(",")));

		logger.error(response.toString());
		mail.send("パラメータの検証エラー", response.toString());
		return response;
	}

	/**
	 * その他、Exception全般をハンドルする。
	 * @param e
	 * @return {@link ErrorResponse}
	 */
	@ExceptionHandler(EntityNotFoundException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse handleEntityNotFoundException(EntityNotFoundException e) {
		var response = new ErrorResponse(930, e.getMessage());
		logger.error(e.toString());
		mail.send("EntityNotFoundエラー", e.toString());
		return response;
	}

	/**
	 * その他、Exception全般をハンドルする。
	 * @param e
	 * @return {@link ErrorResponse}
	 */
	@ExceptionHandler
	public ResponseEntity<ErrorResponse> handleException(Exception e) {
		var innerEx = e.getCause();
		if (innerEx instanceof InvalidFormatException) {
			var response = new ErrorResponse(920, innerEx.getMessage());
			logger.error(innerEx.toString());
			mail.send("フォーマット検証エラー", innerEx.toString());
			return ResponseEntity.badRequest().body(response);
		} else {
			var response = new ErrorResponse(999, e.getMessage());
			logger.error(e.toString());
			mail.send("予期せぬエラー", e.toString());
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
	}
}
