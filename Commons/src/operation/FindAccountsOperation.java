package operation;

import domain.AOperation;
import utils.CpfUtils;

public class FindAccountsOperation extends AOperation {

	private static final long serialVersionUID = 936421529393892799L;
	private String ownerCpf;
	
	public FindAccountsOperation() {
		originAccount = null;
		value = null;
		ownerCpf = null;
		type = OperationType.FIND_ACCOUNTS;
	}

	@Override
	public boolean isValid() {
		return ownerCpf != null && CpfUtils.isValid(ownerCpf);
	}
	
	public String getOwnerCpf() {
		return ownerCpf;
	}

	public void setOwnerCpf(String ownerCpf) {
		this.ownerCpf = ownerCpf;
	}

}
