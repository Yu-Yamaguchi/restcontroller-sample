package example.restcontroller_sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.ServerSetup;

/**
 * SpringBootApplicationクラス。
 * @author Yu Yamaguchi
 *
 */
@SpringBootApplication
public class Application {

	/** ログ */
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private GreenMail smtp = null;
	
	@Autowired
    private Environment env;
	
	/**
	 * SpringBootApplicationを起動する。
	 * @param args パラメータ
	 */
	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
		Application app = ctx.getBean(Application.class);
		app.execStartup();
	}

	public void execStartup() {
		if (env != null && "local".equals(env.getActiveProfiles()[0])) {
			logger.debug("SMTPサーバーをGreenMailで起動します。（ポート：3025, ホスト：localhost）");
			this.smtp = new GreenMail(new ServerSetup(3025,"localhost",ServerSetup.PROTOCOL_SMTP));
			this.smtp.start();
		}
	}
}
