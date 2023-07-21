package net.memmedli.web.generator.controller;

import lombok.RequiredArgsConstructor;
import net.memmedli.web.generator.model.dto.ProjectConfigDto;
import net.memmedli.web.generator.service.GeneratorService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/generator")
public class GeneratorController {

    private final GeneratorService service;

    @PostMapping
    public void generate(@RequestBody ProjectConfigDto dto) {

        service.generate(dto);
    }
}
