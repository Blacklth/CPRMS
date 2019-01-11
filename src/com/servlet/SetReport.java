package com.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.service.MemberService;
import com.vo.Report;

/**
 * Servlet implementation class SetReport
 */

public class SetReport extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetReport() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
	    response.setCharacterEncoding("utf-8");
	    int paper_number = Integer.parseInt(request.getParameter("paper_number"));
	    String pc_code = request.getParameter("pc_code");
	    String relevant = request.getParameter("relevant");
    	String technically = request.getParameter("technically");
    	String length = request.getParameter("length");
    	double  originality = Double.parseDouble(request.getParameter("originality"));
    	double  impact = Double.parseDouble(request.getParameter("impact"));
    	double  presentation = Double.parseDouble(request.getParameter("presentation"));
    	double  depth = Double.parseDouble(request.getParameter("depth"));
    	double  overall_rating = Double.parseDouble(request.getParameter("overall_rating"));
    	double  confidence = Double.parseDouble(request.getParameter("confidence"));
    	String best_paper = request.getParameter("best_paper");
    	String main_contribution = request.getParameter("main_contribution");
    	String strong_points = request.getParameter("strong_points");
    	String weak_points = request.getParameter("weak_points");
    	String overall_summary = request.getParameter("overall_summary");
    	String detailed_comments = request.getParameter("detailed_comments");
    	String confidential_comments = request.getParameter("confidential_comments");
    	
    	
	    Report report = new Report();
	    
	    report.setPaper_number(paper_number);
    	report.setPc_code(pc_code);
    	report.setRelevant(relevant);
    	report.setTechnically(technically);
    	report.setLength(length);
    	report.setOriginality(originality);
    	report.setImpact(impact);
    	report.setPresentation(presentation);
    	report.setDepth(depth);
    	report.setOverall_rating(overall_rating);
    	report.setConfidence(confidence);
    	report.setBest_paper(best_paper);
    	report.setMain_contribution(main_contribution);
    	report.setStrong_points(strong_points);
    	report.setWeak_points(weak_points);
    	report.setOverall_summary(overall_summary);
    	report.setDetailed_comments(detailed_comments);
    	report.setConfidential_comments(confidential_comments);
    	
    	MemberService.setReport(report);
	    response.sendRedirect("reviewPaper.jsp");
	}

}
