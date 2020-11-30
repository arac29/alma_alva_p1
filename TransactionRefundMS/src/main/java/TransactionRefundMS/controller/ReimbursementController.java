package TransactionRefundMS.controller;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import org.apache.log4j.Logger;

import TransactionRefundMS.pojos.Event;
import TransactionRefundMS.pojos.Reimbursement;
import TransactionRefundMS.service.AuthService;
import TransactionRefundMS.service.AuthServiceImpl;
import TransactionRefundMS.service.ReimbursementService;
import TransactionRefundMS.service.ReimbursementServiceImpl;
import io.javalin.http.Context;

public class ReimbursementController {
	
	private static Logger log = Logger.getRootLogger();
	
	ReimbursementService reimbursementService = new ReimbursementServiceImpl();
	
	AuthService auth= new AuthServiceImpl();

	public void  checkStatus(Context ctx) {
		int employeeId=ctx.cookieStore("id");
		log.info("Check Reimbursement Status of " + employeeId);
		
		//ctx.result(reimbursementService.re)
	
	}
	public void submitReimbursementForm(Context ctx) throws ServletException, IOException {
		
		
		int employeeId=ctx.cookieStore("id");
		//int employeeId=100;
		//if (!employeeId)
		
		log.info("Submitting a new request" + employeeId);
				/* CREATE AN EVENT FIRST */

		String name = ctx.formParam("name"); // 1
		String startDate = ctx.formParam("startDate"); //2
		String endDate = ctx.formParam("endDate"); //3
		String location = ctx.formParam("location"); //4
		int eventTypeId = Integer.parseInt(ctx.formParam("eventTypeId")); //6
		String description = ctx.formParam("description"); //7
		int gradingFormatId =Integer.parseInt( ctx.formParam("format")); //8

		Event event = new Event(eventTypeId, name, description, startDate, endDate, location,
				gradingFormatId);
		
		int eventId=reimbursementService.creatEvent(event);
		log.info("Controller creating eventid- "+ eventId);

		/* CREATE A REIMBURSEMENT */
		
		double amountRequested = Double.parseDouble(ctx.formParam("amountRequested")); //5

		Reimbursement reimbursement = new Reimbursement(employeeId, eventId, amountRequested);
		
		log.info("Submitting a new request" + eventId + amountRequested );
		
		Reimbursement newa= new Reimbursement();
		newa=reimbursementService.createReimbursement(reimbursement);
		//create event, create reimbursement
		ctx.cookieStore("submitted" ,true);
		ctx.req.getRequestDispatcher("/forward").forward(ctx.req, ctx.res);
	}
	
	public void getReimbursements(Context ctx) {
		int id=ctx.cookieStore("id");
		List<Reimbursement> reimList = reimbursementService.readReimbursementById(id);
		
		
		ctx.json(reimList);
	}
	public void getReimbursementById(Context ctx) {
		
		int reimbursementId=Integer.parseInt(ctx.pathParam("reimbursementId"));
		Reimbursement reimbursement=reimbursementService.readReimbursement(reimbursementId);
		ctx.json(reimbursement);
	}
	
	public void getEvents(Context ctx) {
		int id=ctx.cookieStore("id");
		List<Event> reimList = reimbursementService.readEventById(id);
		ctx.json(reimList);
	}
	public void getEventById(Context ctx) {
		int eventId=Integer.parseInt(ctx.pathParam("eventId"));
		Event event=reimbursementService.readEvent(eventId);
		ctx.json(event);
	}
	
	public void updateCost(Context ctx) throws ServletException, IOException {
		int reimbursementId=Integer.parseInt(ctx.pathParam("reimbursementId"));
		double cost=Double.parseDouble(ctx.formParam("cost"));
		reimbursementService.updateCost(reimbursementId,cost);
		//ctx.redirect("employeeDashboard.html");
		//ctx.req.getRequestDispatcher("../forward").forward(ctx.req, ctx.res);
		//ctx.req.getRequestDispatcher("/forward").forward(ctx.req, ctx.res);
	}
	public void cancelReimbursement(Context ctx) {
		// regular employee and benco can do this?
	}
	public void updateStatus(Context ctx) {
		//get id dept head, direct supervisor, benco
		//get status
		//get notes?
		
	}
	public void additionalInfo(Context ctx) {
		
	}
	public boolean checkBalance(Context ctx) {
		
		return false;
	}
	
	public void readAllReimbsByReportTo(Context ctx) {
		int id=ctx.cookieStore("id");
		List<Reimbursement> reimList = reimbursementService.readReimbursementByReportTo(id);
		ctx.json(reimList);
	}
	
	public void updateReimbursementDirSupDate(Context ctx) {
		
		log.info("Form Controller --> updateReimbursementDirSupDate ");
		
		int reimbursementId = Integer.parseInt(ctx.formParam("reimbursementId"));
		
		String directorSupervisorApprovalDate = ctx.formParam("directorSupervisorApprovalDate");
		
		String notes = ctx.formParam("notes");
		
		Reimbursement reimbursement = new Reimbursement(directorSupervisorApprovalDate, null, notes);
		
		reimbursementService.updateReimbursementDirSupDate(reimbursementId, reimbursement);
		
		ctx.html(Integer.toString(reimbursement.getReimbursementId()));
	}
	
	
}
