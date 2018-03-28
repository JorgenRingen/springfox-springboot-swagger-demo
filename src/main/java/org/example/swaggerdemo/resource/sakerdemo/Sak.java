package org.example.swaggerdemo.resource.sakerdemo;

import java.time.ZonedDateTime;

public class Sak {

    private String sakId;
    private String saksType;
    private Long kuid;
    private String opprettetAv;
    private ZonedDateTime sistEndret;
    private String saksstatus;
    private ZonedDateTime opprettetDato;
    private String opprettetAvEnhet;
    private Kanal opprettetIKanal;
    private boolean internSaksbehandlingPakrevet;

    public String getSakId() {
        return sakId;
    }

    public void setSakId(String sakId) {
        this.sakId = sakId;
    }

    public String getSaksType() {
        return saksType;
    }

    public void setSaksType(String saksType) {
        this.saksType = saksType;
    }

    public Long getKuid() {
        return kuid;
    }

    public void setKuid(Long kuid) {
        this.kuid = kuid;
    }

    public String getOpprettetAv() {
        return opprettetAv;
    }

    public void setOpprettetAv(String opprettetAv) {
        this.opprettetAv = opprettetAv;
    }

    public ZonedDateTime getSistEndret() {
        return sistEndret;
    }

    public void setSistEndret(ZonedDateTime sistEndret) {
        this.sistEndret = sistEndret;
    }

    public String getSaksstatus() {
        return saksstatus;
    }

    public void setSaksstatus(String saksstatus) {
        this.saksstatus = saksstatus;
    }

    public ZonedDateTime getOpprettetDato() {
        return opprettetDato;
    }

    public void setOpprettetDato(ZonedDateTime opprettetDato) {
        this.opprettetDato = opprettetDato;
    }

    public String getOpprettetAvEnhet() {
        return opprettetAvEnhet;
    }

    public void setOpprettetAvEnhet(String opprettetAvEnhet) {
        this.opprettetAvEnhet = opprettetAvEnhet;
    }

    public Kanal getOpprettetIKanal() {
        return opprettetIKanal;
    }

    public void setOpprettetIKanal(Kanal opprettetIKanal) {
        this.opprettetIKanal = opprettetIKanal;
    }

    public boolean isInternSaksbehandlingPakrevet() {
        return internSaksbehandlingPakrevet;
    }

    public void setInternSaksbehandlingPakrevet(boolean internSaksbehandlingPakrevet) {
        this.internSaksbehandlingPakrevet = internSaksbehandlingPakrevet;
    }
}
