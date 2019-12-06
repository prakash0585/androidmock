package com.sabakuch.epaper.data;

public class DashboardHeaderData {

	private String examName, examDate,levelId,lastPaperId,score;


	public DashboardHeaderData(String examName, String examDate, String levelId, String lastPaperId, String score) {
		super();
		this.examName = examName;
		this.examDate = examDate;
		this.levelId = levelId;
		this.lastPaperId = lastPaperId;
		this.score = score;
	}

	public String getLastPaperId() {
		return lastPaperId;
	}
	public String getExamName() { return examName; }
	public String getExamDate() {
		return examDate;
	}
	public String getLevelId() {
		return levelId;
	}
	public String getScore() { return score; }


}
