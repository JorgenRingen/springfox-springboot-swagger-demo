package org.example.swaggerdemo.resource.sakerdemo;

import org.springframework.stereotype.Component;

import javax.validation.Valid;
import java.util.List;

@Component
public class SakService {
    public List<Sak> finn(@Valid FinnSakerFilter finnSakerFilter) {
        return null;
    }
}
