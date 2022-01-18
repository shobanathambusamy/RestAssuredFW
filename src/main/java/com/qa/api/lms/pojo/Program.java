package com.qa.api.lms.pojo;

public class Program {
	
	
	private int programId;
	private String programName;
	private String programDescription;
	private boolean online;
	
	
	
	public Program() {
		super();
	}

	public Program(int programId, String programName, String programDescription, boolean online) {
		super();
		this.programId = programId;
		this.programName = programName;
		this.programDescription = programDescription;
		this.online = online;
	}

	public int getProgramId() {
		return programId;
	}

	public void setProgramId(int programId) {
		this.programId = programId;
	}

	public String getProgramName() {
		return programName;
	}

	public void setProgramName(String programName) {
		this.programName = programName;
	}

	public String getProgramDescription() {
		return programDescription;
	}

	public void setProgramDescription(String programDescription) {
		this.programDescription = programDescription;
	}

	public boolean isOnline() {
		return online;
	}

	public void setOnline(boolean online) {
		this.online = online;
	}
	
	
	
	
	
	

}
