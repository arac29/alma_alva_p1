package TransactionRefundMS.Postgres;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import TransactionRefundMS.DAO.ReimbursementDAO;
import TransactionRefundMS.pojos.Reimbursement;
import TransactionRefundMS.util.ConnectionUtil;

public class ReimbursementDAOPostgres implements ReimbursementDAO {
	
	private static Logger log = Logger.getRootLogger();
	
	LocalDate date = LocalDate.now();
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private PreparedStatement prepSt;
	
	private PreparedStatement stmt;
	private ConnectionUtil connUtil = new ConnectionUtil();
	
	public void setConnUtil(ConnectionUtil connUtil) {
		this.connUtil = connUtil;
	}

	@Override
	public void createReimbursement(Reimbursement reimbursement) {

		String sql = "insert into reimbursement (employee_id, event_id, date_submition, amount_requested)"
				+ "values(?, ?, ?, ?);";

		try (Connection conn = connUtil.createConnection()) {

			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, reimbursement.getEmployeeId());
			stmt.setInt(2, reimbursement.getEventId());
			
			stmt.setDate(3,  Date.valueOf(date.format(formatter)));
			stmt.setDouble(4, reimbursement.getAmountRequested());
			stmt.executeUpdate();

			log.info("Dao creating reimbursement");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public Reimbursement readReimbursement(int reimbursementId) {

		String sql = "select * from reimbursement where reimbursement_id = " + reimbursementId;

		Reimbursement reimbursement = new Reimbursement();

		try (Connection conn = connUtil.createConnection()) {

			stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			log.info("Dao read reimbursement by id = " + reimbursementId);

			while (rs.next()) {

				reimbursementId = rs.getInt("reimbursement_id");
				int employeeId = rs.getInt("employee_id");
				int eventId = rs.getInt("event_id");
				String dateSubmition = rs.getString("date_submition");
				Boolean employeeCancelation = rs.getBoolean("employee_cancel");
				String justification = rs.getString("justification");
				double amountRequested = rs.getDouble("amount_requested");
				String directorSupervisorApprovalDate = rs.getString("dirsup_approval_date");
				String departmentHeadApprovalDate = rs.getString("dephead_approval_date");
				String benCoApprovalDate = rs.getString("benco_approval_date");
				int reimbursementStatusId = rs.getInt("reimbursement_status_id");
				String notes = rs.getString("notes");
				int updateFileId = rs.getInt("upload_file_id");

				reimbursement.setReimbursementId(reimbursementId);
				reimbursement.setEmployeeId(employeeId);
				reimbursement.setEventId(eventId);
				reimbursement.setDateSubmition(dateSubmition);
				reimbursement.setEmployeeCancelation(employeeCancelation);
				reimbursement.setJustification(justification);
				reimbursement.setAmountRequested(amountRequested);
				reimbursement.setDirectorSupervisorApprovalDate(directorSupervisorApprovalDate);
				reimbursement.setDepartmentHeadApprovalDate(departmentHeadApprovalDate);
				reimbursement.setBenCoApprovalDate(benCoApprovalDate);
				reimbursement.setReimbursementStatusId(reimbursementStatusId);
				reimbursement.setNotes(notes);
				reimbursement.setUpdateFileId(updateFileId);

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reimbursement;
	}

	@Override
	public List<Reimbursement> readAllReimbursements() {

		List<Reimbursement> reimbursementList = new ArrayList();

		String sql = "select * from reimbursement";

		try (Connection conn = connUtil.createConnection()) {

			stmt = conn.prepareStatement(sql);

			ResultSet rs = stmt.executeQuery();

			log.info("Dao read all event reimbursement");

			while (rs.next()) {

				Reimbursement reimbursement = new Reimbursement();

				int reimbursementId = rs.getInt("reimbursement_id");
				int employeeId = rs.getInt("employee_id");
				int eventId = rs.getInt("event_id");
				String dateSubmition = rs.getString("date_submition");
				Boolean employeeCancelation = rs.getBoolean("employee_cancel");
				String justification = rs.getString("justification");
				double amountRequested = rs.getDouble("amount_requested");
				String directorSupervisorApprovalDate = rs.getString("dirsup_approval_date");
				String departmentHeadApprovalDate = rs.getString("dephead_approval_date");
				String benCoApprovalDate = rs.getString("benco_approval_date");
				int reimbursementStatusId = rs.getInt("reimbursement_status_id");
				String notes = rs.getString("notes");
				int updateFileId = rs.getInt("upload_file_id");

				reimbursement.setReimbursementId(reimbursementId);
				reimbursement.setEmployeeId(employeeId);
				reimbursement.setEventId(eventId);
				reimbursement.setDateSubmition(dateSubmition);
				reimbursement.setEmployeeCancelation(employeeCancelation);
				reimbursement.setJustification(justification);
				reimbursement.setAmountRequested(amountRequested);
				reimbursement.setDirectorSupervisorApprovalDate(directorSupervisorApprovalDate);
				reimbursement.setDepartmentHeadApprovalDate(departmentHeadApprovalDate);
				reimbursement.setBenCoApprovalDate(benCoApprovalDate);
				reimbursement.setReimbursementStatusId(reimbursementStatusId);
				reimbursement.setNotes(notes);
				reimbursement.setUpdateFileId(updateFileId);

				reimbursementList.add(reimbursement);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reimbursementList;
	}

	@Override
	public int updateReimbursement(int reimbursementid, Reimbursement reimbursement) {

		String sql = "update reimbursement "
				+ "set employee_id = ?, event_id = ?, date_submition = ?, employee_cancel = ?, justification = ?, amount_requested =?,"
				+ "dirsup_approval_date = ?, dephead_approval_date = ?, benco_approval_date = ?, reimbursement_status_id = ?, notes = ?, upload_file_id = ?"
				+ "where reimbursement_id = ?";

		int rows = 0;

		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, reimbursement.getEmployeeId());
			stmt.setInt(2, reimbursement.getEventId());
			stmt.setDate(3, Date.valueOf(reimbursement.getDateSubmition()));
			stmt.setBoolean(4, reimbursement.isEmployeeCancelation());
			stmt.setString(5, reimbursement.getJustification());
			stmt.setDouble(6, reimbursement.getAmountRequested());
			stmt.setDate(7, Date.valueOf(reimbursement.getDirectorSupervisorApprovalDate()));
			stmt.setDate(8, Date.valueOf(reimbursement.getDepartmentHeadApprovalDate()));
			stmt.setDate(9, Date.valueOf(reimbursement.getBenCoApprovalDate()));
			stmt.setInt(10, reimbursement.getReimbursementStatusId());
			stmt.setString(11, reimbursement.getNotes());
			stmt.setInt(12, reimbursement.getUpdateFileId());
			stmt.setInt(13, reimbursement.getReimbursementId());

			rows = stmt.executeUpdate();

			log.info("Dao updating reimbursementid by reimbursementid = " + reimbursementid);

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rows;
	}

	@Override
	public int deleteReimbursement(int reimbursementId) {

		String sql = "delete from reimbursement where reimbursement_id = ?";

		int rowsToDelete = 0;

		try (Connection conn = connUtil.createConnection()) {
			stmt = conn.prepareStatement(sql);

			stmt.setInt(1, reimbursementId);

			rowsToDelete = stmt.executeUpdate();

			log.info("Dao deleting reimbursement by reimbursementId = " + reimbursementId);

			if (rowsToDelete == 0) {
				System.out.println("No rows to delete.");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return rowsToDelete;

	}

	@Override
	public List<Reimbursement> readReimbursementById(int employee_id) {
		
		String sql = "select  * from reimbursement where employee_id = ?;";
		
		List<Reimbursement> reimbursementList = new ArrayList();
		
		try (Connection conn = connUtil.createConnection()) {

			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, employee_id);

			ResultSet rs = stmt.executeQuery();

			log.info("Dao read all event reimbursement");

			while (rs.next()) {

				Reimbursement reimbursement = new Reimbursement();
				
				int reimbursementId = rs.getInt("reimbursement_id");
				int employeeId = rs.getInt("employee_id");
				int eventId = rs.getInt("event_id");
				String dateSubmition = rs.getString("date_submition");
				Boolean employeeCancelation = rs.getBoolean("employee_cancel");
				String justification = rs.getString("justification");
				double amountRequested = rs.getDouble("amount_requested");
				String directorSupervisorApprovalDate = rs.getString("dirsup_approval_date");
				String departmentHeadApprovalDate = rs.getString("dephead_approval_date");
				String benCoApprovalDate = rs.getString("benco_approval_date");
				int reimbursementStatusId = rs.getInt("reimbursement_status_id");
				String notes = rs.getString("notes");
				//int updateFileId = rs.getInt("upload_file_id");

				reimbursement.setReimbursementId(reimbursementId);
				reimbursement.setEmployeeId(employeeId);
				reimbursement.setEventId(eventId);
				reimbursement.setDateSubmition(dateSubmition);
				reimbursement.setEmployeeCancelation(employeeCancelation);
				reimbursement.setJustification(justification);
				reimbursement.setAmountRequested(amountRequested);
				reimbursement.setDirectorSupervisorApprovalDate(directorSupervisorApprovalDate);
				reimbursement.setDepartmentHeadApprovalDate(departmentHeadApprovalDate);
				reimbursement.setBenCoApprovalDate(benCoApprovalDate);
				reimbursement.setReimbursementStatusId(reimbursementStatusId);
				reimbursement.setNotes(notes);
				//reimbursement.setUpdateFileId(updateFileId);
				

				reimbursementList.add(reimbursement);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reimbursementList;

	
	}
}
