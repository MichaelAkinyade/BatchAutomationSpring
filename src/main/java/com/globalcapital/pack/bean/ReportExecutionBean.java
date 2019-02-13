package com.globalcapital.pack.bean;

public class ReportExecutionBean {

	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getJndiServer() {
		return jndiServer;
	}
	public void setJndiServer(String jndiServer) {
		this.jndiServer = jndiServer;
	}
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public String getBusinessError() {
		return businessError;
	}
	public void setBusinessError(String businessError) {
		this.businessError = businessError;
	}
	public String getPath() {
		return Path;
	}
	public void setPath(String path) {
		Path = path;
	}
	private String username;
	private String password;
	private String jndiServer;
	private String serverUrl;
	private String businessError;
	private String Path;
	private String reportName;
	
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	
	
}
