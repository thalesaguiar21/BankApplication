package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Log {

	@Id @GeneratedValue
	@Column(name="log_id")
	private Long id;
	
	@Column
	private String msg;
	
	@ManyToOne
	@JoinColumn(name="account_id")
	private Account account;

	public Log() {
		id = null;
		account = null;
		msg = null;
	}
	
	public Log(Long id, String msg, Account acc) {
		this.id = id;
		this.msg = msg;
		account = acc;
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
		return msg;
	}

	public void setLogMsg(String logMsg) {
		this.msg = logMsg;
	}
}
