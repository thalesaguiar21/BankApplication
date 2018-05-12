package domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Log {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	public Log(String msg, Account acc) {
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

	public String getMsg() {
		return msg;
	}

	public void setMsg(String logMsg) {
		this.msg = logMsg;
	}
}
