package org.example.swaggerdemo.resource.sakerdemo;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@Api(value = SakerResource.SAKER_PATH, tags = {"Saker"})
@RestController
@RequestMapping(SakerResource.SAKER_PATH)
public class SakerResource {

    static final String SAKER_PATH = "saker";

    private final SakService sakService;

    public SakerResource(SakService sakService) {
        this.sakService = sakService;
    }

    @ApiOperation(value = "Endepunkt for å hente ut saker basert å filtere", response = Sak.class, responseContainer = "List")
    @ApiResponses({
            @ApiResponse(code = 400, message = "Feil i forespørselparametere")
    })
    @GetMapping
    public ResponseEntity finn(@Valid FinnSakerFilter finnSakerFilter) {
        if (finnSakerFilter.erTom()) {
            return ResponseEntity.badRequest().body("Ingen filtere angitt*");
        }

        return ResponseEntity.ok(sakService.finn(finnSakerFilter));
    }

}