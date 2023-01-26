package com.ansc.model.request;

import jakarta.annotation.Nonnull;

@Nonnull
public class AnagraficaRequest {

	private String nome;
	private String cognome;
	private String cf;
	private String celluare;
	private String mail;
	private Boolean mailCertificata;
	private String bancaId;
	private String bt;
	private String pin;
	
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
	public String getBt() {
		return bt;
	}
	public void setBt(String bt) {
		this.bt = bt;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	
	
}
