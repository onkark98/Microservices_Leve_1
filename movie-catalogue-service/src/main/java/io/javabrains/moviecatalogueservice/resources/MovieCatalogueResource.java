package io.javabrains.moviecatalogueservice.resources;

import io.javabrains.moviecatalogueservice.models.CatalogueItem;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static java.util.Collections.singletonList;
import java.util.List;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogueResource {

    @RequestMapping("/{userId}")
    public List<CatalogueItem> getCatalogue(@PathVariable("userId") String userId)
    {
        return singletonList(
                new CatalogueItem("Transformers", "Test", 4)
        );
    }
}
