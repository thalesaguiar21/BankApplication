package domain;

import java.util.Random;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Account {

	public static final Double MAX_BALANCE = 10000000.0;
	
	@Id @GeneratedValue
	@Column(name = "id_account")
	private Long id;
	
	@Column
	private Double balance;
	
	@Column(nullable=false)
	private Long accNumber;
	
	@Column(nullable=false)
	private String ownerCpf;
	
	@Column
	private String ownerName;
	
	public Account() {}
	
	public Account(double balance, String cpf, String ownerName) {
		Random accNumGenerator = new Random(); 
		this.id = Long.valueOf(0);
		this.balance = balance;
		accNumber = accNumGenerator.nextLong();
		ownerCpf = cpf;
		this.ownerName = ownerName;
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

	public String getOwnerCpf() {
		return ownerCpf;
	}

	public void setOwnerCpf(String ownerCpf) {
		this.ownerCpf = ownerCpf;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}
}
