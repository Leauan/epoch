package [(${package})].api;

[# th:each="item:${import}"]
import [(${item})];
[/]
import [(${package})].entity.[(${dtoName})];
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/[(${dtoName})]")
public interface [(${apiName})]{

    @RequestMapping(value = "/query")
    ResponseEntity query(@RequestParam int page,
                         @RequestParam int pageSize,
                         [(${dtoName})] dto);

    @RequestMapping(value = "/submit")
    ResponseEntity submit(@RequestBody [(${dtoName})] dto);

    @RequestMapping(value = "/remove")
    ResponseEntity remove(@RequestBody List<[(${dtoName})]> list);
}