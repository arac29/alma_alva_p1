package TransactionRefundMS.DAO;

import java.util.List;

import TransactionRefundMS.pojos.*;

public interface ReimbursementDAO {

	public void createReimbursement(Reimbursement reimbursement);
	
	public Reimbursement readReimbursement(int reimbursementId);
	
	public List<Reimbursement> readAllReimbursements();
	
	public List<Reimbursement> readReimbursementById(int employee_id);
	
	public List<Reimbursement> readAllReimbsByReportTo(int reportsTo);
	
	public int updateReimbursementDirSupDate(int reimbursementId, Reimbursement reimbursement);
	
	public int updateReimbursement(int reimbursementid, Reimbursement reimbursement);
	
	public int deleteReimbursement(int reimbursementId);
}
