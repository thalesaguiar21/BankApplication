package domain;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

public class Log {

	private Long id;
	private String logMsg;
	
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	public Log() {
		id = null;
		account = null;
		logMsg = null;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public String getLogMsg() {
		return logMsg;
	}

	public void setLogMsg(String logMsg) {
		this.logMsg = logMsg;
	}
}
