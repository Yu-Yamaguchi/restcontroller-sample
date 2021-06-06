package example.restcontroller_sample.aop;

import java.util.ArrayList;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * Spring AOPを利用したログ出力処理を担うクラス。
 * @author Yu Yamaguchi
 *
 */
@Aspect
@Component
public class LogAop {
	/** ログ */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	/**
	 * {@link example.restcontroller_sample.controller}パッケージ配下のメソッドが実行される前に<br>
	 * Callされるメソッドの情報をログ出力する。
	 * @param joinPoint {@link JoinPoint}
	 */
	@Before("execution(* example.restcontroller_sample.controller..*.*(..))")
	public void invokeBeforeController(JoinPoint joinPoint) {
		writeBeforeLog(joinPoint);
	}

	/**
	 * {@link example.restcontroller_sample.controller}パッケージ配下のメソッドが実行された後に<br>
	 * Callされたメソッド情報と、そのメソッドからのReturn情報をログ出力する。
	 * @param joinPoint {@link JoinPoint}
	 * @param returnValue 戻り値
	 */
	@AfterReturning(pointcut = "execution(* example.restcontroller_sample.controller..*.*(..))", returning = "returnValue")
	public void invokeAfterController(JoinPoint joinPoint, Object returnValue) {
		writeAfterLog(joinPoint, returnValue);
	}

	/**
	 * Callされるメソッドの情報と、そのメソッドの引数情報を成形してログ出力する。<br>
	 * ログにはSessionIDを出力し、同一セッションを識別可能にすることで、ログを追いやすくしている。
	 * @param joinPoint {@link JoinPoint}
	 */
	private void writeBeforeLog(JoinPoint joinPoint) {
		var logMessage = "[" + getSessionId() + "]:" + getClassName(joinPoint) + "." + getSignatureName(joinPoint) + ":START:parameter=[" + getArguments(joinPoint) + "]";
		logger.debug(logMessage);
	}

	/**
	 * Callされたメソッドの情報と、そのメソッドの実行結果である戻り値を成形してログ出力する。
	 * @param joinPoint {@link JoinPoint}
	 * @param returnValue 戻り値
	 */
	private void writeAfterLog(JoinPoint joinPoint, Object returnValue) {
		var logMessage = "[" + getSessionId() + "]:" + getClassName(joinPoint) + "." + getSignatureName(joinPoint) + ":END:returnValue=[" + getReturnValue(returnValue) + "]";
		logger.debug(logMessage);
	}

	/**
	 * セッションIDを取得する。
	 * @return セッションID
	 */
	private String getSessionId() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest().getSession().getId();
	}

	/**
	 * 対象のクラス名を取得する。
	 * @param joinPoint {@link JoinPoint}
	 * @return クラス名
	 */
	private String getClassName(JoinPoint joinPoint) {
		return joinPoint.getTarget().getClass().toString();
	}

	/**
	 * シグネチャ（メソッド名など）を取得する。
	 * @param joinPoint {@link JoinPoint}
	 * @return シグネチャ
	 */
	private String getSignatureName(JoinPoint joinPoint) {
		return joinPoint.getSignature().getName();
	}

	/**
	 * 引数の情報を文字列で取得する。
	 * @param joinPoint {@link JoinPoint}
	 * @return 引数情報
	 */
	private String getArguments(JoinPoint joinPoint) {
		if (joinPoint.getArgs() == null) {
			return "argument is null";
		}

		Object[] arguments = joinPoint.getArgs();
		ArrayList<String> argumentStrings = new ArrayList<>();

		for (Object argument : arguments) {
			if (argument != null) {
				argumentStrings.add(argument.toString());
			}
		}
		return String.join(",", argumentStrings);
	}

	/**
	 * 戻り値の情報を文字列で取得する。
	 * @param returnValue 戻り値オブジェクト
	 * @return 文字列の戻り値
	 */
	private String getReturnValue(Object returnValue) {
		return (returnValue != null) ? returnValue.toString() : "return value is null";
	}
}
