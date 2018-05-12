package domain;

import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Account {

	public static final Double MAX_BALANCE = 10000000.0;
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "account_id")
	private Long id;

	@ManyToOne
	@JoinColumn(name="user_id")
	private User user;

	@Column(nullable=false)
	private Double balance;
	
	@Column(name="acc_number", nullable=false)
	private Long accNumber;
	
	@OneToMany(mappedBy="account", targetEntity=Log.class, fetch=FetchType.LAZY,
			cascade = CascadeType.ALL)
	private Set<Log> logs;
	
	public Account() {}
	
	public Account(double balance, User usr) {
		Random accNumGenerator = new Random(); 
		this.id = Long.valueOf(0);
		this.balance = balance;
		accNumber = accNumGenerator.nextLong() % Long.valueOf(1000000000);
		this.user = usr;
		this.logs = new TreeSet<>();
	}
	
	public Account(Long accNum, double balance, User usr, Set<Log> logs) {
		this.id = Long.valueOf(0);
		this.balance = balance;
		accNumber = accNum;
		this.user = usr;
		this.logs = logs;
	}
	
	public boolean withdraw(Double value) {
		if(value != null) {
			if(value > 0 && value <= balance) {
				balance -= value;
				return true;
			}
		}
		return false;
	}
	
	public boolean deposit(Double value) {
		if(value != null) {
			if(value > 0 && (value + balance < MAX_BALANCE)) {
				balance += value;
				return true;
			}
		}
		return false;
	}
	
	public Set<Log> getLogs() {
		return logs;
	}

	public void setLogs(Set<Log> logs) {
		this.logs = logs;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public Long getAccNumber() {
		return accNumber;
	}

	public void setAccNumber(Long accNumber) {
		this.accNumber = accNumber;
	}
	
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
