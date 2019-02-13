/*
 * //package com.globalcapital.pack.database.entity;
 * 
 * import java.util.Date;
 * 
 * import javax.persistence.Column; import javax.persistence.Entity; import
 * javax.persistence.GeneratedValue; import javax.persistence.GenerationType;
 * import javax.persistence.Id; import javax.persistence.Table;
 * 
 * @Entity
 * 
 * @Table (name= "POLICY_TYPE") public class PolicyType {
 * 
 * public PolicyType() {
 * 
 * }
 * 
 * public PolicyType(Long codeid, String externalID, int wordingNumber, int
 * version, String creationActor, Date creationDateTime, String creationTask,
 * String creationAction, String creationChannel, String modificationActor, Date
 * modificationDateTime, String modificationTask, String modificationAction,
 * String modificationChannel) { super(); this.codeid = codeid; this.externalID
 * = externalID; this.wordingNumber = wordingNumber; this.version = version;
 * this.creationActor = creationActor; this.creationDateTime = creationDateTime;
 * this.creationTask = creationTask; this.creationAction = creationAction;
 * this.creationChannel = creationChannel; this.modificationActor =
 * modificationActor; this.modificationDateTime = modificationDateTime;
 * this.modificationTask = modificationTask; this.modificationAction =
 * modificationAction; this.modificationChannel = modificationChannel; }
 * 
 * //@GeneratedValue(strategy = GenerationType.AUTO, generator = "CUST_SEQ")
 * //@SequenceGenerator(sequenceName = "", allocationSize = 1, name =
 * "CUST_SEQ")
 * 
 * @Id
 * 
 * @GeneratedValue(strategy=GenerationType.AUTO)
 * 
 * @Column(name="CODEID") private Long codeid;
 * 
 * @Column(name = "EXTERNAL_ID") String externalID;
 * 
 * @Column(name = "WORDING_NUMBER") int wordingNumber;
 * 
 * @Column(name = "VERSION") int version;
 * 
 * @Column(name = "CREATION_ACTOR") String creationActor;
 * 
 * @Column(name = "CREATION_DATE_TIME") Date creationDateTime;
 * 
 * @Column(name = "CREATION_TASK") String creationTask;
 * 
 * @Column(name = "CREATION_ACTION") String creationAction;
 * 
 * @Column(name = "CREATION_CHANNEL") String creationChannel;
 * 
 * @Column(name = "MODIFICATION_ACTOR") String modificationActor;
 * 
 * @Column(name = "MODIFICATION_DATE_TIME") Date modificationDateTime;
 * 
 * @Column(name = "MODIFICATION_TASK") String modificationTask;
 * 
 * @Column(name = "MODIFICATION_ACTION") String modificationAction;
 * 
 * @Column(name = "MODIFICATION_CHANNEL") String modificationChannel;
 * 
 * 
 * public Long getCodeid() { return codeid; }
 * 
 * public void setCodeid(Long codeid) { this.codeid = codeid; }
 * 
 * public String getExternalID() { return externalID; }
 * 
 * public void setExternalID(String externalID) { this.externalID = externalID;
 * }
 * 
 * public int getWordingNumber() { return wordingNumber; }
 * 
 * public void setWordingNumber(int wordingNumber) { this.wordingNumber =
 * wordingNumber; }
 * 
 * public int getVersion() { return version; }
 * 
 * public void setVersion(int version) { this.version = version; }
 * 
 * public String getCreationActor() { return creationActor; }
 * 
 * public void setCreationActor(String creationActor) { this.creationActor =
 * creationActor; }
 * 
 * public Date getCreationDateTime() { return creationDateTime; }
 * 
 * public void setCreationDateTime(Date creationDateTime) {
 * this.creationDateTime = creationDateTime; }
 * 
 * public String getCreationTask() { return creationTask; }
 * 
 * public void setCreationTask(String creationTask) { this.creationTask =
 * creationTask; }
 * 
 * public String getCreationAction() { return creationAction; }
 * 
 * public void setCreationAction(String creationAction) { this.creationAction =
 * creationAction; }
 * 
 * public String getCreationChannel() { return creationChannel; }
 * 
 * public void setCreationChannel(String creationChannel) { this.creationChannel
 * = creationChannel; }
 * 
 * public String getModificationActor() { return modificationActor; }
 * 
 * public void setModificationActor(String modificationActor) {
 * this.modificationActor = modificationActor; }
 * 
 * public Date getModificationDateTime() { return modificationDateTime; }
 * 
 * public void setModificationDateTime(Date modificationDateTime) {
 * this.modificationDateTime = modificationDateTime; }
 * 
 * public String getModificationTask() { return modificationTask; }
 * 
 * public void setModificationTask(String modificationTask) {
 * this.modificationTask = modificationTask; }
 * 
 * public String getModificationAction() { return modificationAction; }
 * 
 * public void setModificationAction(String modificationAction) {
 * this.modificationAction = modificationAction; }
 * 
 * public String getModificationChannel() { return modificationChannel; }
 * 
 * public void setModificationChannel(String modificationChannel) {
 * this.modificationChannel = modificationChannel; }
 * 
 * 
 * }
 */