package domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class User {
	
	@Id @GeneratedValue
	@Column(name="user_id")
	private Long id;

	@Column
	private String name;
	
	@Column(unique=true)
	private String cpf;
	
	@OneToMany(mappedBy="user", targetEntity=Account.class, fetch=FetchType.LAZY)
	private Set<Account> accounts;
	
	public User() {}
	
	public User(String name, String cpf) {
		this.name = name;
		this.cpf = cpf;
	}
	
	public User(String name, String cpf, Set<Account> accounts) {
		this.name = name;
		this.cpf = cpf;
		this.accounts = accounts;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Set<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(Set<Account> accounts) {
		this.accounts = accounts;
	}
}
