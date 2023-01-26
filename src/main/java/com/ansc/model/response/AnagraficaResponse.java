package com.ansc.model.response;

public class AnagraficaResponse extends BaseAnscResponse{

	private String nome;
	private String cognome;
	private String cf;
	private String celluare;
	private String mail;
	private Boolean mailCertificata;
	private String bancaId;
	
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCognome() {
		return cognome;
	}
	public void setCognome(String cognome) {
		this.cognome = cognome;
	}
	public String getCf() {
		return cf;
	}
	public void setCf(String cf) {
		this.cf = cf;
	}
	public String getCelluare() {
		return celluare;
	}
	public void setCelluare(String celluare) {
		this.celluare = celluare;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public Boolean getMailCertificata() {
		return mailCertificata;
	}
	public void setMailCertificata(Boolean mailCertificata) {
		this.mailCertificata = mailCertificata;
	}
	public String getBancaId() {
		return bancaId;
	}
	public void setBancaId(String bancaId) {
		this.bancaId = bancaId;
	}
	
	
}
