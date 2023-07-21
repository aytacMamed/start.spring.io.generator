package  srcpath.controller;

import srcpath.service.CrudService;
import org.springframework.data.domain.Persistable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import srcpath.model.validator.ExampleValidator;
import srcpath.model.dto.ExampleDto;
import srcpath.model.entity.Example;

@RestController
@Validated(ExampleValidator.class)
@RequestMapping("/api/Example")
public class ExampleController extends CrudController4LongId<ExampleDto, Example> {
    public ExampleController(CrudService<ExampleDto, Example, Long> service) {
        super(service);
    }
}
