package org.example.swaggerdemo.resource.sakerdemo;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


/**
 * Benyttes som request-param input til spring-mvc. Må derfor ha default-constructor og settere
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class FinnSakerFilter {

    public static final String PARAM_KUID = "kuid";
    public static final String PARAM_KUNDEID = "kundeId";
    public static final String PARAM_SAKSSTATUS = "saksstatus";
    public static final String PARAM_SAKSTYPER = "sakstyper";
    public static final String PARAM_OPPRETTET_AV_ENHET = "opprettetAvEnhet";
    public static final String PARAM_DAGER_SIDEN_VEDTAK = "dagerSidenVedtakFattet";
    public static final String PARAM_DAGER_SIDEN_OPPRETTELSE = "dagerSidenOpprettelse";

    private Long kuid;
    private Long kundeId;
    private String saksstatus;
    private List<String> sakstyper;
    private Integer dagerSidenVedtakFattet;
    private Integer dagerSidenOpprettelse;
    private String opprettetAvEnhet;


    public FinnSakerFilter() {
        // rammeverk
    }

    private FinnSakerFilter(Long kuid,
                            Long kundeId,
                            String saksstatus,
                            List<String> sakstyper,
                            Integer dagerSidenVedtakFattet,
                            Integer dagerSidenOpprettelse,
                            String opprettetAvEnhet) {
        this.kuid = kuid;
        this.kundeId = kundeId;
        this.saksstatus = saksstatus;
        this.sakstyper = sakstyper;
        this.dagerSidenVedtakFattet = dagerSidenVedtakFattet;
        this.dagerSidenOpprettelse = dagerSidenOpprettelse;
        this.opprettetAvEnhet = opprettetAvEnhet;
    }

    // rammeverk
    public void setKuid(Long kuid) {
        this.kuid = kuid;
    }

    // rammeverk
    public void setKundeId(Long kundeId) {
        this.kundeId = kundeId;
    }

    // rammeverk
    public void setSaksstatus(String saksstatus) {
        this.saksstatus = saksstatus;
    }

    // rammeverk
    public void setSakstyper(List<String> sakstyper) {
        this.sakstyper = sakstyper;
    }

    // rammeverk
    public void setDagerSidenVedtakFattet(Integer dagerSidenVedtakFattet) {
        this.dagerSidenVedtakFattet = dagerSidenVedtakFattet;
    }

    // rammeverk
    public void setDagerSidenOpprettelse(Integer dagerSidenOpprettelse) {
        this.dagerSidenOpprettelse = dagerSidenOpprettelse;
    }

    // rammeverk
    public void setOpprettetAvEnhet(String opprettetAvEnhet) {
        this.opprettetAvEnhet = opprettetAvEnhet;
    }

    public Optional<Long> getKuid() {
        return Optional.ofNullable(kuid);
    }

    public Optional<Long> getKundeId() {
        return Optional.ofNullable(kundeId);
    }

    public Optional<String> getSaksstatus() {
        return Optional.ofNullable(saksstatus);
    }

    public Optional<List<String>> getSakstyper() {
        if (sakstyper == null || sakstyper.isEmpty()) {
            return Optional.empty();
        } else {
            return Optional.of(sakstyper);
        }
    }

    public Optional<Integer> getDagerSidenVedtakFattet() {
        return Optional.ofNullable(dagerSidenVedtakFattet);
    }

    public Optional<Integer> getDagerSidenOpprettelse() {
        return Optional.ofNullable(dagerSidenOpprettelse);
    }

    public Optional<String> getOpprettetAvEnhet() {
        return Optional.ofNullable(opprettetAvEnhet);
    }

    public boolean erTom() {
        return kuid == null &&
                kundeId == null &&
                saksstatus == null &&
                (sakstyper == null || sakstyper.isEmpty()) &&
                dagerSidenVedtakFattet == null &&
                dagerSidenOpprettelse == null;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        FinnSakerFilter other = (FinnSakerFilter) object;

        return Objects.equals(kuid, other.kuid)
                && Objects.equals(kundeId, other.kundeId)
                && saksstatus == other.saksstatus
                && Objects.equals(sakstyper, other.sakstyper)
                && Objects.equals(dagerSidenVedtakFattet, other.dagerSidenVedtakFattet)
                && Objects.equals(dagerSidenOpprettelse, other.dagerSidenOpprettelse)
                && Objects.equals(opprettetAvEnhet, other.opprettetAvEnhet);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kuid, kundeId, saksstatus, sakstyper, dagerSidenVedtakFattet, dagerSidenOpprettelse, opprettetAvEnhet);
    }

}

