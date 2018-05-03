package operation;

import domain.Account;
import domain.AOperation;
import utils.CpfUtils;
import utils.NameUtils;

public class CreateAccOperation extends AOperation {

	private static final long serialVersionUID = -2185791947823561349L;
	private String ownerName;
	private String cpf;

	public CreateAccOperation() {
		originAccount = null;
		value = null;
		type = OperationType.CREATE;
		ownerName = null;
		cpf = null;
	}
	
	@Override
	public boolean isValid() {
		boolean valid = true;
		valid = valid && (NameUtils.isValidName(ownerName));
		valid = valid && (CpfUtils.isValid(cpf));
		valid = valid && (value != null);
		valid = valid && (value > 0 && value < Account.MAX_BALANCE);
		return valid;
	}
	
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
}
